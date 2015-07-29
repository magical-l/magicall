package me.magicall.db.outsea;

public interface FieldNameColumnNameTransformer {

	String fieldNameToColumnName(String fieldName);

	String columnNameToFieldName(String columnName);
}
