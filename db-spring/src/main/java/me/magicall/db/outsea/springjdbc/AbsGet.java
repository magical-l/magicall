package me.magicall.db.outsea.springjdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.magicall.db.Condition;
import me.magicall.db.SqlValueHandler;
import me.magicall.db.meta.DbColumn;
import me.magicall.db.meta.ForeignKey;
import me.magicall.db.meta.TableMeta;
import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.outsea.GetOneSqlConfig;
import me.magicall.db.util.DbUtil;
import me.magicall.util.LanguageLabelConverter;
import me.magicall.util.comparator.ComparatorUtil;
import me.magicall.util.kit.Kits;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class AbsGet<T, R, C extends GetOneSqlConfig<T>> extends AbsDataAccessor<T, R, C> {

	public AbsGet() {
		super();
	}

	public AbsGet(final NamedParameterJdbcOperations namedJdbc, final TableMetaAccessor tableMetaAccessor) {
		super(namedJdbc, tableMetaAccessor);
	}

	protected Map<String, String> buildSelectFromWhere(final StringBuilder sb, final Map<String, Object> namedParams,
			final C sqlConfig) {
		final SqlBuilderForGet builder = new SqlBuilderForGet(tableMetaAccessor, sqlConfig, namedParams);
		return builder.buildSql(sb);
	}

	/**
	 * 确保名字是以mainModelName.开头的
	 * 
	 * @param name
	 * @param sqlConfig
	 * @return
	 */
	protected String ensureStartsWithMainModelName(final String name, final GetOneSqlConfig<T> sqlConfig) {
		final String mainModelName = sqlConfig.getMainModelName();
		final String mainModelNameDot = mainModelName + '.';
		if (name.startsWith(mainModelNameDot)) {
			return name;
		} else {
			return mainModelNameDot + name;
		}
	}

	protected static class Mapping {
		String fullName;
		String shortName;
		String label;
		TableMeta tableMeta;
		String tableName;

		String containerModelName;
	}

	protected class SqlBuilderForGet {

		private final TableMetaAccessor tableMetaAccessor;
		private final GetOneSqlConfig<T> sqlConfig;

		private final Map<String, Mapping> modelFullNameMappings = Maps.newHashMap();

		private Collection<String> modelFullNames;

		private final Map<String, Object> namedParamValueMap;

		public SqlBuilderForGet(final TableMetaAccessor tableMetaAccessor,//
				final GetOneSqlConfig<T> sqlConfig,//
				final Map<String, Object> namedParamValueMap) {
			super();
			this.tableMetaAccessor = tableMetaAccessor;
			this.sqlConfig = sqlConfig;
			this.namedParamValueMap = namedParamValueMap;
		}

		public Map<String, String> buildSql(final StringBuilder sb) {
			sortModelNames();
			buildMappings();
			final Map<String, String> rt = buildSelect(sb);
			buildFrom(sb);
			buildWhere(sb);
			return rt;
		}

		void sortModelNames() {
			//按照名字从短到长排序,则父model肯定在子model之前
			final Collection<String> otherModelNames = sqlConfig.getOtherModelsNames();
			final List<String> list = new ArrayList<>(otherModelNames.size() + 1);
			final String mainModelName = sqlConfig.getMainModelName();
			list.add(mainModelName);
			for (final String otherModelName : otherModelNames) {
				final String fullName = ensureStartsWithMainModelName(otherModelName, sqlConfig);
				list.add(fullName);
			}
			Collections.sort(list, ComparatorUtil.STR_LEN_COMPARATOR);
			modelFullNames = list;
		}

		private void addMapping(final String fullName) {
			final Mapping mapping = new Mapping();
			mapping.fullName = fullName;
			final int lastDotIndex = fullName.lastIndexOf('.');
			if (lastDotIndex < 0) {
				mapping.shortName = fullName;
			} else {
				mapping.shortName = fullName.substring(lastDotIndex + 1);
			}
			mapping.label = convertToLabel(fullName);

			final String containerModelName = getContainerName(fullName);
			if (containerModelName == null) {
				mapping.tableMeta = tableMetaAccessor.getTableMetaIgnoreCase(fullName);
			} else {
				mapping.containerModelName = containerModelName;
				final Mapping container = modelFullNameMappings.get(containerModelName);
				mapping.tableMeta = findRefedTableMeta(container.tableMeta, mapping.shortName);
			}

			mapping.tableName = DbUtil.javaNameToDbName(mapping.shortName);

			modelFullNameMappings.put(fullName, mapping);
		}

		void buildMappings() {
			for (final String modelName : modelFullNames) {
				addMapping(modelName);
			}
		}

		private String convertToLabel(final String fullName) {
			return fullName.replace('.', DbUtil.TABLE_NAME_COLUMN_NAME_SEPERATOR_CHAR);
		}

		Map<String, String> buildSelect(final StringBuilder sb) {
			final List<As> as = Lists.newLinkedList();
			//main model
			final String mainModelName = sqlConfig.getMainModelName();
			final Mapping mainModelMapping = modelFullNameMappings.get(mainModelName);
			final TableMeta mainModelTableMeta = mainModelMapping.tableMeta;
			final List<DbColumn> mainModelColumns = mainModelTableMeta.getColumns();
			for (final DbColumn column : mainModelColumns) {
				final String columnName = column.getName();
				final String fieldName = DbUtil.dbNameToJavaName(columnName);
				as.add(new As(mainModelMapping.fullName + '.' + columnName, fieldName));
			}

			//other models
			for (final String modelFullName : modelFullNames) {
				if (!modelFullName.equals(mainModelName)) {
					final Mapping mapping = modelFullNameMappings.get(modelFullName);
					final String containerModelName = getContainerName(mapping.fullName);
					final Mapping containerMapping = modelFullNameMappings.get(containerModelName);
					final TableMeta tableMeta = findRefedTableMeta(containerMapping.tableMeta, mapping.shortName);
					if (tableMeta != null) {
						final List<DbColumn> columns = tableMeta.getColumns();
						for (final DbColumn column : columns) {
							final String columnName = column.getName();
							final String fieldName = DbUtil.dbNameToJavaName(columnName);
							as.add(new As(mapping.tableName + '.' + columnName,//
									mapping.label + DbUtil.TABLE_NAME_COLUMN_NAME_SEPERATOR + fieldName));
						}
					}
				}
			}

			sb.append(" SELECT ");
			for (final As a : as) {
				sb.append(a).append(',');
			}
			sb.deleteCharAt(sb.length() - 1);

			final Map<String, String> rt = Maps.newHashMap();
			for (final As a : as) {
				rt.put(a.right, a.right.replace(DbUtil.TABLE_NAME_COLUMN_NAME_SEPERATOR_CHAR, '.'));
			}
			return rt;
		}

		void buildFrom(final StringBuilder sb) {
			//main model
			final String mainModelName = sqlConfig.getMainModelName();
			final Mapping mainModelMapping = modelFullNameMappings.get(mainModelName);
			sb.append(" FROM ").append(new As(mainModelMapping.tableName, mainModelName));
			//other models
			for (final String modelName : modelFullNames) {
				if (!modelName.equals(mainModelName)) {
					final Mapping mapping = modelFullNameMappings.get(modelName);
					final TableMeta tableMeta = mapping.tableMeta;

					final String containerModelName = getContainerName(mapping.fullName);
					final TableMeta containerTableMeta = modelFullNameMappings.get(containerModelName).tableMeta;

					final ForeignKey foreignKey = findForeignKey(tableMeta, containerTableMeta);

					final Mapping containerMapping = modelFullNameMappings.get(containerModelName);
					sb.append(" LEFT JOIN ")//
							.append(new As(tableMeta.getName(), mapping.label))//
							.append(" ON ")//
							.append(containerMapping.label).append('.')//
							.append(foreignKey.getReferencingColumn().getName())//
							.append('=')//
							.append(mapping.label).append('.').append(foreignKey.getReferencedColumn().getName());
				}
			}
		}

		private String getContainerName(final String modelFullName) {
			final int lastDotIndex = modelFullName.lastIndexOf('.');
			if (lastDotIndex < 0) {
				return null;
			}
			return modelFullName.substring(0, lastDotIndex);
		}

		private TableMeta findRefedTableMeta(final TableMeta tableMeta, final String modelName) {
			final Collection<ForeignKey> foreignKeys = tableMeta.getForeignKeys();
			if (!Kits.COLL.isEmpty(foreignKeys)) {
				for (final ForeignKey foreignKey : foreignKeys) {
					if (isSame(modelName + "Id", foreignKey.getReferencingColumn().getName())) {
						return foreignKey.getReferencedTable();
					}
				}
			}
			return null;
		}

		private ForeignKey findForeignKey(final TableMeta refedTableMeta, final TableMeta refingtableMeta) {
			final Collection<ForeignKey> foreignKeys = refingtableMeta.getForeignKeys();
			if (Kits.COLL.isEmpty(foreignKeys)) {
				throw new RuntimeException("no foreign key in `" + refingtableMeta + '`');
			}
			for (final ForeignKey foreignKey : foreignKeys) {
				if (foreignKey.getReferencedTable().equals(refedTableMeta)) {
					return foreignKey;
				}
			}
			throw new RuntimeException("no foreign key from `" + refingtableMeta + "` to `" + refedTableMeta + '`');
		}

		void buildWhere(final StringBuilder sb) {
			final List<Condition> conditions = sqlConfig.getConditions();
			if (!Kits.COLL.isEmpty(conditions)) {
				final String mainModelName = sqlConfig.getMainModelName();
				sb.append(" WHERE 1=1 ");
				for (final Condition condition : conditions) {
					String fieldName = condition.getFieldName();
					if (!fieldName.startsWith(mainModelName + '.')) {
						fieldName = mainModelName + '.' + fieldName;
					}
					final int dotIndex = fieldName.lastIndexOf('.');
					assert dotIndex > 0;
					final String modelName = fieldName.substring(0, dotIndex);
					final String shortFieldName = fieldName.substring(dotIndex + 1);

					final Mapping modelMapping = modelFullNameMappings.get(modelName);
					assert modelMapping != null;

					final String columnName = DbUtil.javaNameToDbName(shortFieldName);
					if (containsField(modelMapping.tableMeta, shortFieldName)) {
						//条件中必须用columnName,比较操蛋
						final String resultColumnName = modelMapping.label + '.' + columnName;
						condition.getConditionOperator().buildSqlUsingColumnName(sb.append(" AND "), resultColumnName,
								(sb1, resultColumnName1, index, refedValue) -> {
                                    final String paramedName = resultColumnName1 + '#' + index;
                                    namedParamValueMap.put(paramedName, refedValue);
                                    return paramedName;
                                }, condition.getRefedValues());
					}
				}
			}
		}

		private boolean containsField(final TableMeta tableMeta, final String fieldName) {
			for (final DbColumn column : tableMeta.getColumns()) {
				if (isSame(fieldName, column.getName())) {
					return true;
				}
			}
			return false;
		}

		private boolean isSame(final String fieldName, final String dbName) {
			final String[] dbNameParts = splitDbName(dbName);
			final String[] javaNameParts = splitJavaName(fieldName);
			if (dbNameParts.length != javaNameParts.length) {
				return false;
			}
			for (int i = 0; i < dbNameParts.length; ++i) {
				if (!dbNameParts[i].equalsIgnoreCase(javaNameParts[i])) {
					return false;
				}
			}
			return true;
		}

		private String[] splitDbName(final String name) {
			return LanguageLabelConverter.SQL.splitWords(name);
		}

		private String[] splitJavaName(final String name) {
			return LanguageLabelConverter.CAMEL.splitWords(name);
		}

		public GetOneSqlConfig<?> getSqlConfig() {
			return sqlConfig;
		}
	}

	private static class As {
		String left;
		String right;

		public As(final String left, final String right) {
			super();
			this.left = left;
			this.right = right;
		}

		@Override
		public String toString() {
			return left.equals(right) ? left : left + ' ' + right;
		}
	}
}