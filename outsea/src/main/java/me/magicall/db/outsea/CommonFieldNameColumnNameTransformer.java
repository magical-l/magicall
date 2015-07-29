package me.magicall.db.outsea;

import me.magicall.db.util.DbUtil;

public enum CommonFieldNameColumnNameTransformer implements FieldNameColumnNameTransformer {

	COMMON {
		@Override
		public String fieldNameToColumnName(final String fieldName) {
			return DbUtil.javaNameToDbName(fieldName);
		}

		@Override
		public String columnNameToFieldName(final String columnName) {
			return DbUtil.dbNameToJavaName(columnName);
		}
	}, //
	SAME_NAME {
		@Override
		public String fieldNameToColumnName(final String fieldName) {
			return fieldName;
		}

		@Override
		public String columnNameToFieldName(final String columnName) {
			return columnName;
		}
	}, //
	;
}
