package me.magicall.db.dbms;

import me.magicall.db.util.FieldType;

import java.util.Map;

public interface DBMS {

	FieldType getType(String dbColumnTypeName);

	String getResultColumnNameOfShowCreateTable();

	String getDriverClassName();

	int getDefaultPort();

	String formatUrl(String host, int port, String dbName, Map<String, ?> params);

	String formatUrl(String host, String dbName, Map<String, ?> params);

	String formatUrl(String host, int port, Map<String, ?> params);

	String formatUrl(String host, Map<String, ?> params);
}
