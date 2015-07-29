package me.magicall.db.outsea;

import me.magicall.db.util.DbUtil;

public enum CommonModelNameTableNameTransformer implements ModelNameTableNameTransformer {

	COMMON {
		@Override
		public String modelNameToTableName(final String modelName) {
			return DbUtil.javaNameToDbName(modelName);
		}

		@Override
		public String tableNameToModelName(final String tableName) {
			return DbUtil.dbNameToJavaName(tableName);
		}
	}, //
	SAME_NAME {
		@Override
		public String modelNameToTableName(final String modelName) {
			return modelName;
		}

		@Override
		public String tableNameToModelName(final String tableName) {
			return tableName;
		}
	}, //
	;
}
