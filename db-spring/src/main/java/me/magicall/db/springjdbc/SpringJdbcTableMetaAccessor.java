package me.magicall.db.springjdbc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.magicall.consts.StrConst.EncodingConst;
import me.magicall.db.dbms.CommonDBMS;
import me.magicall.db.dbms.DBMS;
import me.magicall.db.meta.DbColumn;
import me.magicall.db.meta.ForeignKey;
import me.magicall.db.meta.Key;
import me.magicall.db.meta.Key.KeyType;
import me.magicall.db.meta.TableMeta;
import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.util.DbUtil;
import me.magicall.db.util.FieldType;
import me.magicall.mark.Cached;
import me.magicall.util.kit.Kit;
import me.magicall.util.kit.Kits;
import me.magicall.util.time.TimeFormatter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpringJdbcTableMetaAccessor implements TableMetaAccessor, Cached, InitializingBean {

	private final DBMS dbms = CommonDBMS.MYSQL;

	private final DriverManagerDataSource dataSource;
	private final String dbName;

	private Map<String, TableMeta> tableMetaMap;

	public SpringJdbcTableMetaAccessor(final DriverManagerDataSource dataSource, final String dbName) {
		super();
		this.dataSource = dataSource;
		this.dbName = dbName;
	}

	//==============================================
	public TableMeta getTableMetaUsingModelName(final String modelName) {
		return getTableMetaUsingTableName(DbUtil.javaNameToDbName(modelName));
	}

	public TableMeta getTableMetaUsingTableName(final String tableName) {
		if (tableMetaMap == null) {
			tableMetaMap = Maps.newConcurrentMap();
			return null;
		} else {
			return tableMetaMap.get(tableName.toLowerCase());
		}
	}

	@Override
	public TableMeta getTableMetaIgnoreCase(final String tableNameOrModelName) {
		return getTableMetaUsingTableName(DbUtil.javaNameToDbName(tableNameOrModelName));
	}

	public void build() {
		final JdbcOperations metaJdbc = newJdbcOptions();

		final Map<String, TableMeta> tableMap = Maps.newHashMap();

		final Collection<TableMeta> tableMetas = parseTableMetas(metaJdbc);
		for (final TableMeta tableMeta : tableMetas) {
			tableMap.put(tableMeta.getName(), tableMeta);
		}

		parseColumnMetas(metaJdbc, tableMap);
		parseKeys(metaJdbc, tableMap);

		buildView(metaJdbc, tableMap);

		final Map<String, TableMeta> tmp = Maps.newConcurrentMap();
		for (final Entry<String, TableMeta> e : tableMap.entrySet()) {
			tmp.put(e.getKey().toLowerCase(), e.getValue());
		}

		tableMetaMap = tmp;
	}

	protected void parseKeys(final JdbcOperations metaJdbc, final Map<String, TableMeta> tableMap) {
		//键
		final Map<String, Key> keyMap = Maps.newHashMap();

		final List<Map<String, Object>> keys = metaJdbc
				.queryForList("select * from KEY_COLUMN_USAGE where `TABLE_SCHEMA`='" + dbName + '\'');
		for (final Map<String, Object> keyInfo : keys) {
			final String name = String.valueOf(keyInfo.get("CONSTRAINT_NAME"));
			//所属表
			final String tableName = String.valueOf(keyInfo.get("TABLE_NAME"));
			final TableMeta tableMeta = tableMap.get(tableName);

			final String columnName = String.valueOf(keyInfo.get("COLUMN_NAME"));
			final DbColumn column = tableMeta.getColumn(columnName);
			if (name.equalsIgnoreCase("PRIMARY")) {//主键
				Key key = tableMeta.getPrimaryKey();
				if (key == null) {
					key = new Key();
					key.setName(name);
					key.setType(KeyType.PRIMARY);
					tableMeta.setPrimaryKey(key);
				}
				key.add(column);
			} else {//非主键索引
				Key key = keyMap.get(name);
				if (key == null) {
					key = new Key();
					key.setName(name);
					final Object positionInUniqueConstraint = keyInfo.get("POSITION_IN_UNIQUE_CONSTRAINT");
					key.setType(positionInUniqueConstraint == null ? KeyType.UNIQUE : KeyType.COMMON);

					tableMeta.getKeys().add(key);
					keyMap.put(name, key);
				}
				key.add(column);
			}
			//外键
			final Object referencedTableSchema = keyInfo.get("REFERENCED_TABLE_SCHEMA");
			if (referencedTableSchema != null) {
				final String refedTableName = String.valueOf(keyInfo.get("REFERENCED_TABLE_NAME"));
				final TableMeta refedTable = tableMap.get(refedTableName);
				final String refedColumnName = String.valueOf(keyInfo.get("REFERENCED_COLUMN_NAME"));

				final ForeignKey foreignKey = new ForeignKey();
				foreignKey.setName(name);
				foreignKey.setReferencingColumn(column);
				foreignKey.setReferencingTable(tableMeta);
				foreignKey.setReferencedTable(refedTable);
				foreignKey.setReferencedColumn(refedTable.getColumn(refedColumnName));
			}
		}//for keys
	}

	protected void parseColumnMetas(final JdbcOperations metaJdbc, final Map<String, TableMeta> tableMap) {
		//列的基本信息
		final List<Map<String, Object>> columns = metaJdbc.queryForList("select * from COLUMNS where `TABLE_SCHEMA`='"
				+ dbName + '\'');
		for (final Map<String, Object> columnInfo : columns) {
			final TableMeta tableMeta = tableMap.get(columnInfo.get("TABLE_NAME"));
			final DbColumn column = new DbColumn();
			final String extra = String.valueOf(columnInfo.get("Extra"));
			final boolean autoInc = extra.contains("auto_increment");
			column.setAutoInc(autoInc);

			column.setComment(String.valueOf(columnInfo.get("COLUMN_COMMENT")));
			column.setName(String.valueOf(columnInfo.get("COLUMN_NAME")));

			column.setNullable(Kits.BOOL.fromString(String.valueOf(columnInfo.get("IS_NULLABLE"))));

			final String typeString = String.valueOf(columnInfo.get("COLUMN_TYPE"));
			final boolean unsigned = typeString.contains("unsigned");
			column.setUnsigned(unsigned);
			final boolean zeroFill = typeString.contains("zerofill");
			column.setZeroFill(zeroFill);

			final String typeName = String.valueOf(columnInfo.get("DATA_TYPE"));
			final FieldType fieldType = dbms.getType(typeName);
			column.setType(fieldType);

			if (FieldType.VARCHAR.equals(fieldType)) {
				final String lenStr = String.valueOf(columnInfo.get("CHARACTER_MAXIMUM_LENGTH"));
				if (!Kits.STR.isEmpty(lenStr)) {
					column.setLength(Kits.INT.fromString(lenStr));
				}
			}

			final Object defaultValue = columnInfo.get("COLUMN_DEFAULT");
			if (defaultValue == null) {
				if (autoInc) {
					column.setHasDefaultValue(true);
				}
			} else {
				final String defaultValueStr = String.valueOf(defaultValue);
				if (fieldType == FieldType.TIMESTAMP) {
					if ("CURRENT_TIMESTAMP".equalsIgnoreCase(defaultValueStr)) {
						column.setDefaultValue(null);
						column.setHasDefaultValue(true);
					} else {
						column.setDefaultValue(TimeFormatter.Y2_M2_D2_H2_MIN2_S2.parse(defaultValueStr));
					}
				} else {
					final Kit<?> kit = fieldType.kit;
					if (kit != null) {
						column.setDefaultValue(kit.fromString(defaultValueStr));
					}
				}
			}

			tableMeta.add(column);
		}//for columns
	}

	protected Collection<TableMeta> parseTableMetas(final JdbcOperations metaJdbc) {
		//表的基本信息
		final List<Map<String, Object>> tables = metaJdbc.queryForList("select * from TABLES where `TABLE_SCHEMA`='"
				+ dbName + '\'');

		final Collection<TableMeta> tableMetas = Lists.newArrayListWithExpectedSize(tables.size());
		for (final Map<String, Object> tableInfo : tables) {
			final String name = String.valueOf(tableInfo.get("TABLE_NAME"));
			final TableMeta tableMeta = new TableMeta();
			tableMeta.setComment(String.valueOf(tableInfo.get("TABLE_COMMENT")));
			tableMeta.setDbName(String.valueOf(tableInfo.get("TABLE_SCHEMA")));
//			tableMeta.setDefaultCharsetName(defaultCharsetName);
			tableMeta.setName(name);

			tableMetas.add(tableMeta);
		}//for tables
		return tableMetas;
	}

	protected JdbcOperations newJdbcOptions() {
		final DriverManagerDataSource ds = new DriverManagerDataSource();
		{//连接数据库
			ds.setDriverClassName(dbms.getDriverClassName());
			ds.setUsername(dataSource.getUsername());
			ds.setPassword(dataSource.getPassword());
			final String url = dataSource.getUrl();
			final int slashIndex = url.indexOf("//");
			final int colonIndex = url.indexOf(":", slashIndex);
			final int slash2Index = url.indexOf("/", colonIndex);
			int portEndIndex;
			if (slash2Index < 0) {
				portEndIndex = url.indexOf("?", colonIndex);
				if (portEndIndex < 0) {
					portEndIndex = url.length();
				}
			} else {
				portEndIndex = slash2Index;
			}
			ds.setUrl(dbms.formatUrl(url.substring(slashIndex + 2, colonIndex),//
					Kits.INT.fromString(url.substring(colonIndex + 1, portEndIndex)),//port
					"information_schema",//database name
					Collections.singletonMap("characterEncoding", EncodingConst.GBK)));
		}
		final JdbcOperations metaJdbc = new JdbcTemplate(ds);
		return metaJdbc;
	}

	private static final Pattern JOIN_PATTERN = Pattern
			.compile("\\s*(?:join\\s+)?`[a-zA-Z0-9_]+`\\.`([a-zA-Z0-9_]+)`\\s+`([a-zA-Z0-9_]+)`\\s*");
	private static final String SELECT = "select ",//
			FROM = " from ",//
			AS = " AS ";

	public void buildView(final JdbcOperations metaJdbc, final Map<String, TableMeta> tableMap) {
		final List<Map<String, Object>> tables = metaJdbc.queryForList("select * from VIEWS where `TABLE_SCHEMA`='"
				+ dbName + '\'');

		for (final Map<String, Object> tableInfo : tables) {
			final String name = String.valueOf(tableInfo.get("TABLE_NAME"));
			final String viewDefinition = String.valueOf(tableInfo.get("VIEW_DEFINITION"));
			//(select 
			//	`p`.`id` AS `id`,
			//	`t`.`name` AS `device_type_name`
			//from
			//		`anosi_asis`.`device_product` `p` 
			//	join `anosi_asis`.`device_brand` `b` 
			//	join `anosi_asis`.`device_type` `t`
			//where
			//(
			//		(`p`.`device_type_id` = `t`.`id`)
			//	and (`p`.`device_brand_id` = `b`.`id`)
			//))
			//(select 
			//	`anosi_asis`.`staff`.`id` AS `id`,
			//	`anosi_asis`.`staff`.`name` AS `name`,
			//from `anosi_asis`.`staff`
			//)
			final TableMeta viewMeta = new TableMeta();
			viewMeta.setComment(String.valueOf(tableInfo.get("TABLE_COMMENT")));
			viewMeta.setDbName(String.valueOf(tableInfo.get("TABLE_SCHEMA")));
			viewMeta.setName(name);

			final Map<String, String> tableShortNameMap = new HashMap<>();
			{//from part
				final String from = Kits.STR.subStringAfter(viewDefinition, FROM).trim();
				final Matcher fromMatcher = JOIN_PATTERN.matcher(from);
				while (fromMatcher.find()) {
					tableShortNameMap.put(fromMatcher.group(2), fromMatcher.group(1));
				}
			}

			{//select part
				final String select = Kits.STR.middle(viewDefinition, SELECT, false, FROM, false).trim();
				final String[] fields = select.split(",");
				for (String field : fields) {
					field = field.trim();
					final int asIndex = Kits.STR.indexOfIgnoreCase(field, AS);

					final String fieldNameWithTableName = field.substring(0, asIndex).trim();
					final int lastDotIndex = fieldNameWithTableName.lastIndexOf('.');
					final int secondLastDotIndex = fieldNameWithTableName.lastIndexOf('.', lastDotIndex - 1);

					final String tableShortName = DbUtil.unquote(fieldNameWithTableName.substring(
							secondLastDotIndex + 1, lastDotIndex));
					final String tableName = Kits.STR.checkToDefaultValue(tableShortNameMap.get(tableShortName),
							tableShortName);
					final String tableColumnName = DbUtil.unquote(fieldNameWithTableName.substring(lastDotIndex + 1));
					final TableMeta tableMeta = tableMap.get(tableName);
					final DbColumn tableColumn = tableMeta.getColumn(tableColumnName);
					final DbColumn viewColumn = new DbColumn(tableColumn);
					viewColumn.setName(DbUtil.unquote(Kits.STR.subStringAfter(field, AS)));
					viewColumn.setComment(tableMeta.getComment() + tableColumn.getComment());
					viewMeta.add(viewColumn);
				}// for field
			}

			tableMap.put(name, viewMeta);
		}//for views
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		final long start = System.currentTimeMillis();
		build();
		final long end = System.currentTimeMillis();
		System.out.println("@@@@@@SpringJdbcTableMetaAccessor.build() cost:" + (end - start));
	}

	@Override
	public void dropCache() {
		if (tableMetaMap != null) {
			tableMetaMap.clear();
		}
	}

	public String getDbName() {
		return dbName;
	}

	@Override
	public Collection<TableMeta> tableMetas() {
		return new ArrayList<>(tableMetaMap.values());
	}
}
