package me.magicall.db.mybatis.interceptors;

import me.magicall.db.meta.TableMeta;
import me.magicall.db.mybatis.mappers.CommonMapper;
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
@Deprecated
public class CommonMapperInterceptor extends InterceptorTemplate implements Interceptor {

	private static String mapperName(final String sqlId) {
		final char seperator = '.';
		final int lastIndex = sqlId.lastIndexOf(seperator);
		return sqlId.substring(0, lastIndex);
	}

	@Override
	protected void internalIntercept(final Invocation invocation) throws IllegalArgumentException {
		final Object[] executorArgs = invocation.getArgs();
		final MappedStatement mappedStatement = (MappedStatement) executorArgs[0];

		final String mapperName = mapperName(mappedStatement.getId());
		if (!CommonMapper.class.getName().equals(mapperName)) {
			return;
		}

		final Object arg = executorArgs[1];
		if (!(arg instanceof Object[])) {
			throw new IllegalArgumentException("CommonMap method[" + mappedStatement.getId() + "] has no arguments");
		}
		final Object[] mapperArgs = (Object[]) arg;
		if (!(mapperArgs[0] instanceof TableMeta)) {
			throw new IllegalArgumentException("CommonMap method[" + mappedStatement.getId() + "] 1st arguments is not TableMeta");
		}
		final TableMeta tableMeta = (TableMeta) mapperArgs[0];

		final BoundSql boundSql = mappedStatement.getBoundSql(arg);

		final String sql = boundSql.getSql().replace(" t1 ", " t1_copy ");

		final BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());

		final MappedStatementBuilderBuilder builder = new MappedStatementBuilderBuilder(mappedStatement);
		builder.setSqlSource(new SqlSource() {
			@Override
			public BoundSql getBoundSql(final Object parameterObject) {
				return newBoundSql;
			}
		});

		executorArgs[0] = builder.build().build();
	}
}
