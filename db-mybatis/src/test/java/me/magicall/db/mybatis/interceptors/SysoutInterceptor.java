package me.magicall.db.mybatis.interceptors;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;


import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts( {//
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),//
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class,
				BoundSql.class }),//
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),//
		@Signature(type = Executor.class, method = "flushStatements", args = {}),//
		@Signature(type = Executor.class, method = "commit", args = { boolean.class }),//
		@Signature(type = Executor.class, method = "close", args = { boolean.class }),//

		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }),//
		@Signature(type = StatementHandler.class, method = "parameterize", args = { Statement.class }),//
		@Signature(type = StatementHandler.class, method = "update", args = { Statement.class }),//
		@Signature(type = StatementHandler.class, method = "query", args = { Statement.class, ResultHandler.class }),//

		@Signature(type = ParameterHandler.class, method = "getParameterObject", args = {}),//
		@Signature(type = ParameterHandler.class, method = "setParameters", args = { PreparedStatement.class }),//

		@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }),//
		@Signature(type = ResultSetHandler.class, method = "handleOutputParameters", args = { CallableStatement.class }),//
})
public class SysoutInterceptor extends InterceptorTemplate implements Interceptor {
	//执行顺序基本如下：
	//plugin (Reuse)Executor
	//intercept Executor.query //args[0:MappedStatement,1:Mapper方法传入的参数,2:RowBounds]
	//plugin (Default)ParameterHandler
	//plugin (Fast)ResultSetHandler
	//plugin (Routing)StatementHandler
	//intercept StatementHandler.prepare
	//intercept StatementHandler.parameterize
	//intercept ParameterHandler.setParameters
	//intercept StatementHandler.query
	//intercept ResultSetHandler.handleResultSets
	//intercept Executor.commit
	//intercept Executor.close
	@Override
	protected void internalIntercept(final Invocation invocation) throws Throwable {
		if (print) {
			final Object target = invocation.getTarget();
			final Method method = invocation.getMethod();
			final Object[] args = invocation.getArgs();

//		System.out.println("@@@@@@TestInterceptor.intercept:target:" + target);
//		System.out.println("@@@@@@TestInterceptor.intercept:method:" + method);
//		System.out.println("@@@@@@TestInterceptor.intercept:args:" + Utils.OBJ.deepToString(args));

			if (target instanceof Executor && method.getName().equals("query") && args.length == 4) {
				final MappedStatement mappedStatement = (MappedStatement) args[0];
				final Object arg = args[1];
				final RowBounds rowBounds = (RowBounds) args[2];
				final ResultHandler resultHandler = (ResultHandler) args[3];

				final BoundSql boundSql = mappedStatement.getBoundSql(arg);

				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.dbId:" + mappedStatement.getDatabaseId());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.id:" + mappedStatement.getId());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.resource:" + mappedStatement.getResource());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.fetchSize:" + mappedStatement.getFetchSize());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.timeout:" + mappedStatement.getTimeout());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.cache:" + mappedStatement.getCache());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.keyGenerator:" + mappedStatement.getKeyGenerator());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.keyColumns:" + Arrays.toString(mappedStatement.getKeyColumns()));
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.keyProperties:" + Arrays.toString(mappedStatement.getKeyProperties()));
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.parameterMap:" + mappedStatement.getParameterMap());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.resultMaps:" + mappedStatement.getResultMaps());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.resultsetType:" + mappedStatement.getResultSetType());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.sqlCommandType:" + mappedStatement.getSqlCommandType());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.sqlSource:" + mappedStatement.getSqlSource());
				System.out.println("@@@@@@TestInterceptor.intercept:mappedStatement.statementType:" + mappedStatement.getStatementType());

				System.out.println("@@@@@@TestInterceptor.intercept:arg:" + arg);

				System.out.println("@@@@@@TestInterceptor.intercept:rowBounds:" + rowBounds.getOffset() + ' ' + rowBounds.getLimit());

				System.out.println("@@@@@@TestInterceptor.intercept:resultHandler:" + resultHandler);

				System.out.println("@@@@@@TestInterceptor.intercept:boundSql.sql:" + boundSql.getSql());
				System.out.println("@@@@@@TestInterceptor.intercept:boundSql.parameterMappings:" + boundSql.getParameterMappings());
				System.out.println("@@@@@@TestInterceptor.intercept:boundSql.parameterObject:" + boundSql.getParameterObject());
			}
		}
	}

	@Override
	public Object plugin(final Object target) {
//		System.out.println("@@@@@@TestInterceptor.plugin:target:" + target);
		return super.plugin(target);
	}

	private boolean print;

	@Override
	public void setProperties(final Properties properties) {
		//相当于init方法，仅在创建本类实例时会调用一次。
//		System.out.println("@@@@@@TestInterceptor.setProperties:" + properties);
		print = Boolean.valueOf((String) properties.get("print"));
		super.setProperties(properties);
	}

}
