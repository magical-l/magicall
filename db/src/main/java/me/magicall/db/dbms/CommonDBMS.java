package me.magicall.db.dbms;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.magicall.db.util.FieldType;
import me.magicall.util.kit.Kits;

public enum CommonDBMS implements DBMS {
		MYSQL("com.mysql.jdbc.Driver", 3306,//
				"Create Table",//
				new Object[][] {//
				{ "binary", FieldType.VARBINARY },//
						{ "datetime", FieldType.TIMESTAMP },//
						{ "enum", FieldType.CHAR },//
						{ "float", FieldType.REAL },//
						{ "int", FieldType.INTEGER },//
//						have no "longblob",
						{ "mediumblob", FieldType.BLOB }, { "mediumint", FieldType.INTEGER },//
						{ "numeric", FieldType.DECIMAL },//
						{ "real", FieldType.DOUBLE },//
						{ "set", FieldType.CHAR },//
						{ "text", FieldType.VARCHAR }, { "tinyblob", FieldType.BINARY }, { "tinytext", FieldType.VARCHAR },//
						{ "year", FieldType.DATE },//
				}), //
		;

		private final String driverClassName;
		private final int defaultPort;
		private final Map<String, FieldType> specialDataTypeRef;
		private final String resultColumnNameOfShowCreateTable;

		private CommonDBMS(final String driverClassName,//
				final int defaultPort,//
				final String showCreateTableColumnName, final Object[][] os) {
			this.driverClassName = driverClassName;
			this.defaultPort = defaultPort;
			resultColumnNameOfShowCreateTable = showCreateTableColumnName;
			specialDataTypeRef = new HashMap<>();
			for (final Object[] o : os) {
				specialDataTypeRef.put((String) o[0], (FieldType) o[1]);
			}
		}

		@Override
		public FieldType getType(final String dbColumnTypeName) {
			final FieldType t = specialDataTypeRef.get(dbColumnTypeName.toLowerCase());
			return t == null ? getTypeFromFieldType(dbColumnTypeName) : t;
		}

		private static FieldType getTypeFromFieldType(final String dbColumnTypeName) {
			final FieldType[] types = FieldType.values();
			for (final FieldType t : types) {
				if (t.name().equalsIgnoreCase(dbColumnTypeName)) {
					return t;
				}
			}
			return null;
		}

		@Override
		public String getResultColumnNameOfShowCreateTable() {
			return resultColumnNameOfShowCreateTable;
		}

		@Override
		public String getDriverClassName() {
			return driverClassName;
		}

		@Override
		public int getDefaultPort() {
			return defaultPort;
		}

		@Override
		public String formatUrl(final String host, final int port, final Map<String, ?> params) {
			return formatUrl(host, port, Kits.STR.emptyValue(), params);
		}

		@Override
		public String formatUrl(final String host, final String dbName, final Map<String, ?> params) {
			return formatUrl(host, getDefaultPort(), dbName, params);
		}

		@Override
		public String formatUrl(final String host, final Map<String, ?> params) {
			return formatUrl(host, getDefaultPort(), Kits.STR.emptyValue(), params);
		}

		@Override
		public String formatUrl(final String host, final int port, final String dbName, final Map<String, ?> params) {
			final StringBuilder sb = new StringBuilder("jdbc:mysql://").append(host).append(':').append(port);
			if (!Kits.STR.isEmpty(dbName)) {
				sb.append('/').append(dbName);
			}
			if (!Kits.MAP.isEmpty(params)) {
				sb.append('?');
				for (final Entry<String, ?> e : params.entrySet()) {
					sb.append(e.getKey()).append('=').append(e.getValue()).append('&');
				}
			}
			return sb.toString();
		}

	}