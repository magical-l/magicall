package me.magicall.db.outsea;


public enum CommonNameTransformer implements ModelNameTableNameTransformer, FieldNameColumnNameTransformer {
	COMMON(CommonFieldNameColumnNameTransformer.COMMON, CommonModelNameTableNameTransformer.COMMON), //
	SAME_NAME(CommonFieldNameColumnNameTransformer.SAME_NAME, CommonModelNameTableNameTransformer.SAME_NAME), //
	;
	private final FieldNameColumnNameTransformer fieldNameColumnNameTransformer;
	private final ModelNameTableNameTransformer modelNameTableNameTransformer;

	private CommonNameTransformer(final FieldNameColumnNameTransformer fieldNameColumnNameTransformer,
			final ModelNameTableNameTransformer modelNameTableNameTransformer) {
		this.fieldNameColumnNameTransformer = fieldNameColumnNameTransformer;
		this.modelNameTableNameTransformer = modelNameTableNameTransformer;
	}

	@Override
	public String modelNameToTableName(final String modelName) {
		return modelNameTableNameTransformer.modelNameToTableName(modelName);
	}

	@Override
	public String tableNameToModelName(final String tableName) {
		return modelNameTableNameTransformer.tableNameToModelName(tableName);
	}

	@Override
	public String fieldNameToColumnName(final String fieldName) {
		return fieldNameColumnNameTransformer.fieldNameToColumnName(fieldName);
	}

	@Override
	public String columnNameToFieldName(final String columnName) {
		return fieldNameColumnNameTransformer.columnNameToFieldName(columnName);
	}
}
