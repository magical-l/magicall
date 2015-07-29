package me.magicall.db.mybatis.util;

import me.magicall.util.ArrayUtil;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.session.Configuration;

import com.google.common.base.Joiner;

public class MyBatisUtil {

	public static class MappedStatementBuilderBuilder {
		private final MappedStatement mappedStatement;
		private Configuration configuration;
		private String id;
		private SqlSource sqlSource;
		private SqlCommandType sqlCommandType;

		public MappedStatementBuilderBuilder(final MappedStatement mappedStatement) {
			super();
			this.mappedStatement = mappedStatement;
		}

		public Builder build() {
			final Builder builder = new MappedStatement.Builder(//
					configuration == null ? mappedStatement.getConfiguration() : configuration,//
					id == null ? mappedStatement.getId() : id,//
					sqlSource == null ? mappedStatement.getSqlSource() : sqlSource,//
					sqlCommandType == null ? mappedStatement.getSqlCommandType() : sqlCommandType);

			builder.databaseId(mappedStatement.getDatabaseId());
			builder.cache(mappedStatement.getCache());
			builder.fetchSize(mappedStatement.getFetchSize());
			builder.flushCacheRequired(mappedStatement.isFlushCacheRequired());
			final String[] keyColumns = mappedStatement.getKeyColumns();
			join(builder, keyColumns);
			builder.keyGenerator(mappedStatement.getKeyGenerator());
			final String[] keyProperties = mappedStatement.getKeyProperties();
			join(builder, keyProperties);
			builder.parameterMap(mappedStatement.getParameterMap());
			builder.resource(mappedStatement.getResource());
			builder.resultMaps(mappedStatement.getResultMaps());
			builder.resultSetType(mappedStatement.getResultSetType());
			builder.statementType(mappedStatement.getStatementType());
			builder.timeout(mappedStatement.getTimeout());
			builder.useCache(mappedStatement.isUseCache());

			return builder;
		}

		public Configuration getConfiguration() {
			return configuration;
		}

		public void setConfiguration(final Configuration configuration) {
			this.configuration = configuration;
		}

		public String getId() {
			return id;
		}

		public void setId(final String id) {
			this.id = id;
		}

		public SqlSource getSqlSource() {
			return sqlSource;
		}

		public void setSqlSource(final SqlSource sqlSource) {
			this.sqlSource = sqlSource;
		}

		public SqlCommandType getSqlCommandType() {
			return sqlCommandType;
		}

		public void setSqlCommandType(final SqlCommandType sqlCommandType) {
			this.sqlCommandType = sqlCommandType;
		}
	}

	public static final Joiner COMMA = Joiner.on(',');

	private static void join(final Builder builder, final String... arr) {
		if (!ArrayUtil.isEmpty(arr)) {
			builder.keyColumn(COMMA.join(arr));
		}
	}
}
