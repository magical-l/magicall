package me.magicall.db.outsea.springjdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.magicall.db.Condition;
import me.magicall.db.FieldComparator;
import me.magicall.db.FieldFilter;
import me.magicall.db.SqlValueHandler;
import me.magicall.db.meta.DbColumn;
import me.magicall.db.meta.ForeignKey;
import me.magicall.db.meta.TableMeta;
import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.outsea.CountSqlConfig;
import me.magicall.db.outsea.FieldNameColumnNameTransformer;
import me.magicall.db.outsea.GetOneSqlConfig;
import me.magicall.db.outsea.ModelMapTransformer;
import me.magicall.db.util.DbOrder;
import me.magicall.db.util.DbUtil;
import me.magicall.db.util.FieldType;
import me.magicall.util.kit.Kit;
import me.magicall.util.kit.Kits;
import me.magicall.util.touple.TwoTuple;

class SqlUtil {

	public static StringBuilder appendWhere(final StringBuilder sb, final TableMeta tableMeta,//
			final FieldNameColumnNameTransformer fieldNameColumnNameTransformer,//
			final List<Condition> conditions, final Map<String, Object> namedParams) {
		if (!Kits.COLL.isEmpty(conditions)) {
			sb.append(" WHERE 1=1 ");
			final List<String> columnNames = tableMeta.getColumnNames();
			for (final Condition condition : conditions) {
				sb.append(" AND ");
				final String fieldName = condition.getFieldName();
				final String columnName = fieldNameColumnNameTransformer.fieldNameToColumnName(fieldName);
				if (columnNames.contains(columnName)) {
					condition.getConditionOperator().buildSqlUsingColumnName(sb, columnName,
							(sb1, fieldName1, index, refedValue) -> {
                                final String paramedName = fieldName1 + '#' + index;
                                namedParams.put(paramedName, refedValue);
                                return paramedName;
                            }, condition.getRefedValues());
				}
			}
		}
		return sb;
	}

	public static class ModelMapping {
		String modelName;//全名
		TableMeta tableMeta;
		ModelMapping parent;
		String parentModelName;//全名
		ForeignKey foreignKey;
		String asName;//全名
		String shortModelName;
		String foreignKeyColumnName;
		boolean isMainModel = false;
	}

	public static Map<String, ModelMapping> buildModelMappings(final TableMetaAccessor tableMetaAccessor,
			final FieldNameColumnNameTransformer fieldNameColumnNameTransformer,//
			final GetOneSqlConfig<?> sqlConfig) {
		final String mainModelName = sqlConfig.getMainModelName();
		final TableMeta mainTableMeta = tableMetaAccessor.getTableMetaIgnoreCase(mainModelName);
		if (mainTableMeta == null) {
			throw new IllegalArgumentException("model '" + mainModelName + "' has no tableMeta");
		}

		return buildModelMappings(mainModelName, mainTableMeta, sqlConfig.getOtherModelsNames(),
				fieldNameColumnNameTransformer);
	}

	public static Map<String, String> buildSelectFromWhere(final StringBuilder sb,
			final Map<String, Object> namedParams, final FieldNameColumnNameTransformer fieldNameColumnNameTransformer,
			final GetOneSqlConfig<?> sqlConfig, final Map<String, ModelMapping> modelMappings) {
		final FieldFilter fieldFilter = sqlConfig.getFieldFilter();
		final List<Condition> conditions = sqlConfig.getConditions();
		final String mainModelName = sqlConfig.getMainModelName();

		final Map<String, String> resultLabelToFieldNameMapping = new HashMap<>();
		//select
		buildSelect(sb, fieldNameColumnNameTransformer, modelMappings, fieldFilter, resultLabelToFieldNameMapping);
		//from
		appendFrom(sb, modelMappings);
		//where
		appendWhereOfConditions(sb, namedParams, fieldNameColumnNameTransformer, mainModelName, conditions,
				modelMappings);
		//构建外键条件
		appendWhereOfForeignKey(sb, modelMappings);
		return resultLabelToFieldNameMapping;
	}

	/**
	 * @param sb
	 * @param fieldNameColumnNameTransformer
	 * @param modelMappings
	 * @param fieldFilter
	 * @param resultLabel_fieldName_mapping
	 */
	protected static void buildSelect(final StringBuilder sb,
			final FieldNameColumnNameTransformer fieldNameColumnNameTransformer,
			final Map<String, ModelMapping> modelMappings, final FieldFilter fieldFilter,
			final Map<String, String> resultLabel_fieldName_mapping) {
		sb.append("SELECT ");
		for (final Entry<String, ModelMapping> e : modelMappings.entrySet()) {
			final ModelMapping modelMapping = e.getValue();
			final TableMeta tableMeta = modelMapping.tableMeta;
			final List<DbColumn> columns = tableMeta.getColumns();
			for (final DbColumn column : columns) {
				final String fieldName = fieldNameColumnNameTransformer.columnNameToFieldName(column.getName());
				if (fieldFilter == null || fieldFilter.accept(tableMeta, column)) {
					final String asName = //modelMapping.isMainModel ? fieldName : //
					modelMapping.asName + DbUtil.TABLE_NAME_COLUMN_NAME_SEPERATOR + fieldName;
					sb.append(/* modelMapping.isMainModel ? mainModelName : */modelMapping.asName)//
							.append('.').append(column.getName()).append(" AS ")//
							.append(asName).append(',');
					resultLabel_fieldName_mapping.put(asName, modelMapping.modelName + '.' + fieldName);
				}
			}
		}
		sb.deleteCharAt(sb.length() - 1);
	}

	public static <T> void buildSelectCountFromWhere(final StringBuilder sb,
			final Map<String, Object> namedParams,//
			final TableMetaAccessor tableMetaAccessor,
			final FieldNameColumnNameTransformer fieldNameColumnNameTransformer,//
			final CountSqlConfig<T> sqlConfig) {
		final String mainModelName = sqlConfig.getMainModelName();
		final TableMeta mainTableMeta = tableMetaAccessor.getTableMetaIgnoreCase(mainModelName);
		if (mainTableMeta == null) {
			throw new IllegalArgumentException("model '" + mainModelName + "' has no tableMeta");
		}
		final Collection<String> otherModelsNames = sqlConfig.getOtherModelsNames();
		final List<Condition> conditions = sqlConfig.getConditions();

		final Map<String, ModelMapping> modelMappings = buildModelMappings(mainModelName, mainTableMeta,
				otherModelsNames, fieldNameColumnNameTransformer);
		//select
		sb.append("SELECT COUNT(1)");
		//from
		appendFrom(sb, modelMappings);
		//where
		appendWhereOfConditions(sb, namedParams, fieldNameColumnNameTransformer, mainModelName, conditions,
				modelMappings);
		//构建外键条件
		appendWhereOfForeignKey(sb, modelMappings);
	}

	private static void appendWhereOfConditions(final StringBuilder sb, final Map<String, Object> namedParams,//
			final FieldNameColumnNameTransformer fieldNameColumnNameTransformer, final String mainModelName,//
			final List<Condition> conditions, final Map<String, ModelMapping> modelMappings) {
		sb.append(" WHERE 1=1 ");
		if (Kits.COLL.isEmpty(conditions)) {
			return;
		}
		for (final Condition condition : conditions) {
			String fieldName = condition.getFieldName();
			if (!fieldName.startsWith(mainModelName + '.')) {
				fieldName = mainModelName + '.' + fieldName;
			}
			final int dotIndex = fieldName.lastIndexOf('.');
			assert dotIndex > 0;
			final String modelName = fieldName.substring(0, dotIndex);
			final String shortFieldName = fieldName.substring(dotIndex + 1);

			final ModelMapping modelMapping = modelMappings.get(modelName);
			assert modelMapping != null;

			final String columnName = fieldNameColumnNameTransformer.fieldNameToColumnName(shortFieldName);
			if (containsField(modelMapping.tableMeta, shortFieldName)) {
				//条件中必须用columnName,比较操蛋
				final String resultColumnName = tableNameAs(modelMapping) + '.' + columnName;
				condition.getConditionOperator().buildSqlUsingColumnName(sb.append(" AND "), resultColumnName,
						(sb1, resultColumnName1, index, refedValue) -> {
                            final String paramedName = resultColumnName1 + '#' + index;
                            namedParams.put(paramedName, refedValue);
                            return paramedName;
                        }, condition.getRefedValues());
			}
		}
	}

	private static boolean containsField(final TableMeta tableMeta, final String fieldName) {
		for (final DbColumn column : tableMeta.getColumns()) {
			if (javaNameEqualsDbName(fieldName, column.getName())) {
				return true;
			}
		}
		return false;
	}

	private static void appendWhereOfForeignKey(final StringBuilder sb, final Map<String, ModelMapping> modelMappings) {
		for (final Entry<String, ModelMapping> e : modelMappings.entrySet()) {
			final ModelMapping modelMapping = e.getValue();
			final ForeignKey foreignKey = modelMapping.foreignKey;
			if (foreignKey != null) {
				sb.append(" AND ")
						//
						.append(modelMapping.asName).append('.').append(foreignKey.getReferencedColumn().getName())
						.append('=')//
						.append(modelMapping.parent.asName)//
						.append('.').append(foreignKey.getReferencingColumn().getName());
			}
		}
	}

	private static void appendFrom(final StringBuilder sb, final Map<String, ModelMapping> modelMappings) {
		sb.append(" FROM ");
		for (final Entry<String, ModelMapping> e : modelMappings.entrySet()) {
			final ModelMapping modelMapping = e.getValue();
			sb.append(modelMapping.tableMeta.getName()).append(" AS ").append(tableNameAs(modelMapping)).append(',');
		}
		sb.deleteCharAt(sb.length() - 1);
	}

	private static String tableNameAs(final ModelMapping modelMapping) {
		return modelMapping.asName;
	}

	private static final Comparator<String> LENGTH_ASC = (o1, o2) -> o1.length() - o2.length();

	private static boolean javaNameEqualsDbName(final String javaName, final String dbName) {
		return javaName.replace("_", "").toLowerCase().equals(dbName.replace("_", "").toLowerCase());
	}

	private static Map<String, ModelMapping> buildModelMappings(final String mainModelName,
			final TableMeta mainTableMeta, final Collection<String> otherModelsNames,
			final FieldNameColumnNameTransformer fieldNameColumnNameTransformer) {
		final Map<String, ModelMapping> modelMappings = new HashMap<>();//key is modelName
		//main model
		final ModelMapping mainModelMapping = new ModelMapping();
		mainModelMapping.modelName = mainModelName;
		mainModelMapping.tableMeta = mainTableMeta;
		mainModelMapping.asName = mainModelName;//"";
		mainModelMapping.isMainModel = true;
		mainModelMapping.shortModelName = mainModelName;
		modelMappings.put(mainModelMapping.modelName, mainModelMapping);
		//other models
		//这里用名字长度排一个序,为了确保父model出现在子model之前
		final List<String> otherModelsNames0 = otherModelsNames instanceof List<?>
				&& !Kits.COLL.isUnmodifiable(otherModelsNames)//
		? (List<String>) otherModelsNames : new ArrayList<>(otherModelsNames);
		Collections.sort(otherModelsNames0, LENGTH_ASC);

		buildOtherModelMapping(mainModelName, modelMappings, otherModelsNames0);

		//处理出现在父model之前的model.它们现在还没有parentModel
		for (final Entry<String, ModelMapping> e : modelMappings.entrySet()) {
			final ModelMapping modelMapping = e.getValue();
			if (!modelMapping.isMainModel && modelMapping.parent == null) {
				final ModelMapping parentModelMapping = modelMappings.get(modelMapping.parentModelName);
				if (parentModelMapping == null) {
					throw new IllegalArgumentException("parent modelName of '" + e.getKey() + "'("
							+ modelMapping.parentModelName + ") has no tableMeta");
				} else {
					modelMapping.parent = parentModelMapping;
					withParent(modelMapping, parentModelMapping);
				}
			}
		}//for (final Entry<String, ModelMapping> e : modelMappings.entrySet())
		return modelMappings;
	}

	/**
	 * @param mainModelName
	 * @param modelMappings
	 * @param otherModelsNames0
	 */
	protected static void buildOtherModelMapping(final String mainModelName,
			final Map<String, ModelMapping> modelMappings, final List<String> otherModelsNames0) {
		if (!Kits.LIST.isEmpty(otherModelsNames0)) {
			for (final String otherModelName : otherModelsNames0) {//service.outDevice service.outDevice.deviceProduct service.outDevice.deviceProduct.deviceType
				final ModelMapping modelMapping = buildMapping(mainModelName, modelMappings, otherModelName);
				modelMappings.put(modelMapping.modelName, modelMapping);
			}//for otherModelsNames
		}
	}

	protected static void withParent(final ModelMapping modelMapping, final ModelMapping parentModelMapping) {
		final ForeignKey foreignKey = findForeignKey(parentModelMapping.tableMeta, modelMapping);
		modelMapping.foreignKey = foreignKey;
		modelMapping.tableMeta = foreignKey.getReferencedTable();
		modelMapping.asName = modelMapping.parent.asName + DbUtil.TABLE_NAME_COLUMN_NAME_SEPERATOR
				+ modelMapping.shortModelName;
	}

	protected static ModelMapping buildMapping(final String mainModelName,
			final Map<String, ModelMapping> modelMappings, String otherModelName) {
		final ModelMapping modelMapping = new ModelMapping();
		//自动加上mainModelName
		if (!otherModelName.startsWith(mainModelName + '.')) {
			otherModelName = mainModelName + '.' + otherModelName;
		}
		modelMapping.modelName = otherModelName;

		final int dotIndex = otherModelName.lastIndexOf('.');
		assert dotIndex >= 0;
		modelMapping.parentModelName = otherModelName.substring(0, dotIndex);//service service.outDevice service.outDevice.deviceProduct
		modelMapping.shortModelName = otherModelName.substring(dotIndex + 1);//outDevce deviceProduct deviceType

		modelMapping.foreignKeyColumnName = modelMapping.shortModelName + "Id";// fieldNameColumnNameTransformer.fieldNameToColumnName(modelMapping.shortModelName) + "_id";//out_device_id
		final ModelMapping parentModelMapping = modelMappings.get(modelMapping.parentModelName);
		if (parentModelMapping != null) {
			modelMapping.parent = parentModelMapping;
			final TableMeta parentTableMeta = parentModelMapping.tableMeta;
			if (parentTableMeta != null) {
				withParent(modelMapping, parentModelMapping);
			}
		}
		return modelMapping;
	}

	private static ForeignKey findForeignKey(final TableMeta parentTableMeta, final ModelMapping modelMapping) {
		final Collection<ForeignKey> foreignKeys = parentTableMeta.getForeignKeys();
		for (final ForeignKey foreignKey : foreignKeys) {
			if (javaNameEqualsDbName(modelMapping.foreignKeyColumnName, foreignKey.getReferencingColumn().getName())) {
				return foreignKey;
			}
		}
		assert false;
		return null;
	}

	public static <T> T handlerResultMap(
			final Map<String, String> resultLabelToFieldNameMapping,//
			final String mainModelName, final Map<String, Object> resultMap,
			final ModelMapTransformer<T> modelMapTransformer) {
		return modelMapTransformer.mapToModel(resultMap, mainModelName);
	}

	public static Object getValue(final DbColumn column, final ResultSet resultSet, final int i) throws SQLException {
		if (column == null) {
			return resultSet.getObject(i);
		}
		final FieldType type = column.getType();
		if (type == null) {
			return resultSet.getObject(i);
		}
		final Kit<?> kit = type.getKit();
		if (kit == null) {
			return resultSet.getObject(i);
		}
		final String string = resultSet.getString(i);
		if (column.getNullable() && string == null) {
			return null;
		}
		return kit.fromString(string);
	}

	public static <T> Map<String, Object> mapRow(
			final TableMetaAccessor tableMetaAccessor,//
			final FieldNameColumnNameTransformer fieldNameColumnNameTransformer,
			final GetOneSqlConfig<T> sqlConfig,//
			final Map<String, String> resultLabelToFieldNameMapping,
			final Map<String, ModelMapping> modelNameToModelMapping,//
			final ResultSet rs) throws SQLException {
		final ResultSetMetaData resultSetMetaData = rs.getMetaData();
		final int columnCount = resultSetMetaData.getColumnCount();
		final Map<String, Object> rt = new HashMap<>();
		for (int i = DbUtil.RESULT_SET_COLUMN_START_INDEX; i <= columnCount; ++i) {
			final String label = resultSetMetaData.getColumnLabel(i);
			final String fieldName = resultLabelToFieldNameMapping.get(label);

			final int dotIndex = fieldName.lastIndexOf('.');
			assert dotIndex >= 0;
			final String modelName = fieldName.substring(0, dotIndex);
			final ModelMapping modelMapping = modelNameToModelMapping.get(modelName);

			final TableMeta tableMeta = modelMapping.tableMeta;
			if (tableMeta == null) {
				System.out.println("@@@@@@SqlUtil.mapRow():" + label);
			}
			final String columnName = Kits.STR.subStringAfterLastSeq(
					//
					fieldNameColumnNameTransformer.fieldNameToColumnName(label),
					DbUtil.TABLE_NAME_COLUMN_NAME_SEPERATOR);

			final Object value = getValue(tableMeta.getColumn(columnName), rs, i);

			rt.put(fieldName, value);
		}
		return rt;
	}

	public static StringBuilder appendOrderBy(final StringBuilder sb, final FieldComparator<?> fieldComparator,
			final String mainModelName,//
			final FieldNameColumnNameTransformer fieldNameColumnNameTransformer) {
		if (fieldComparator != null) {
			final List<TwoTuple<String, DbOrder>> comparingFieldsNamesAndOrders = fieldComparator
					.getComparingFieldsNamesAndOrders();
			if (!Kits.COLL.isEmpty(comparingFieldsNamesAndOrders)) {
				sb.append(" ORDER BY ");
				for (final TwoTuple<String, DbOrder> t : comparingFieldsNamesAndOrders) {
					final String fieldName = t.first;
					final DbOrder order = t.second;
					sb.append(mainModelName).append('.')
							.append(fieldNameColumnNameTransformer.fieldNameToColumnName(fieldName)).append(' ');
					if (order == null) {
						sb.append(DbOrder.ASC.toSql()).append(',');
					} else {
						sb.append(order.toSql()).append(',');
					}
				}
				sb.deleteCharAt(sb.length() - 1);
			}
		}
		return sb;
	}
}
