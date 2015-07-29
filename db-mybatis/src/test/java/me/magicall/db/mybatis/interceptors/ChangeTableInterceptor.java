package me.magicall.db.mybatis.interceptors;

import me.magicall.db.mybatis.util.MyBatisUtil.MappedStatementBuilderBuilder;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts( {//
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class,
				BoundSql.class }),//
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),//
})
public class ChangeTableInterceptor extends InterceptorTemplate implements Interceptor {

	@Override
	protected void internalIntercept(final Invocation invocation) throws Throwable {
		final Object[] args = invocation.getArgs();

		final MappedStatement mappedStatement = (MappedStatement) args[0];
		final Object arg = args[1];

		final BoundSql boundSql = mappedStatement.getBoundSql(arg);

		final String sql = boundSql.getSql().replace(" t1 ", " t1_copy ");

		final BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());

		final MappedStatementBuilderBuilder builder2 = new MappedStatementBuilderBuilder(mappedStatement);
		builder2.setSqlSource(new SqlSource() {
			@Override
			public BoundSql getBoundSql(final Object parameterObject) {
				return newBoundSql;
			}
		});

		args[0] = builder2.build().build();
	}
}
