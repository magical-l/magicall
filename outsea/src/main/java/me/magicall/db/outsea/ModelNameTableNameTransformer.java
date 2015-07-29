package me.magicall.db.outsea;

public interface ModelNameTableNameTransformer {

	String modelNameToTableName(String modelName);

	String tableNameToModelName(String tableName);

}
