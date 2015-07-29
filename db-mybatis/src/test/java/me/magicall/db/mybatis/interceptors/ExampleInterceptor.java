package me.magicall.db.mybatis.interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * mybatis拦截器，将拦截mybatis执行时如下sql映射：
 * <select id="getUser" resultType="int">
 * [resource="user:register"]
 * SELECT login_name$needAuthc("loginName"), password$needAuthc("password") FROM users WHERE login_name=#{loginName}
 * </select>
 * 进行权限校验后返回拥有权限的字段查询结果
 * 
 * @version 1.00 2011-12-6
 * @author Yockii Hsu
 */
@Intercepts(value = {
		@Signature(args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }, method = "query", type = Executor.class),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class ExampleInterceptor implements Interceptor {

	private Properties properties;

	@Override
	public Object intercept(final Invocation invocation) throws Throwable {
		final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		final Object parameter = invocation.getArgs()[1];
		final BoundSql boundSql = mappedStatement.getBoundSql(parameter);

		final String methodName = invocation.getMethod().getName();

		String sql = boundSql.getSql().trim();

		if (sql.startsWith("[resource")) {
			final StringBuffer resource = new StringBuffer(sql.split("\"]")[0].substring(sql.indexOf("\"") + 1));
			String nextSql = sql.split("\"]")[1].trim();

			if (nextSql.matches("^(?i)select.*\\$needAuthc\\(\"[a-z0-9]*\"\\).*")) {
				final String regEx = "(?i)[_a-z0-9]+\\$needAuthc\\(\"[a-z0-9]*\"\\)";
				final Pattern p = Pattern.compile(regEx);
				final Matcher m = p.matcher(nextSql);
				final List<String> list = new ArrayList<>();
				while (m.find()) {
					list.add(m.group());
				}
//				final Subject subject = org.apache.shiro.SecurityUtils.getSubject();
				for (int i = list.size() - 1; i >= 0; i--) {
					final String str = list.get(i);
					final String fieldPermission = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
					final String permission = resource.append(fieldPermission).toString();
//					if (subject.isPermitted(permission)) {
//						list.remove(i);
//					}
				}
				for (final String str : list) {
					nextSql = nextSql.replaceAll(str.replaceAll("\\)", "\\\\\\\\\\)").replaceAll("\\$", "\\\\\\\\\\$").replaceAll("\\(", "\\\\\\\\\\("), "");
//					logger.debug(str);
//					logger.debug(nextSql);
				}
			}

			// 将sql转换为正常的sql语句
			sql = nextSql.replaceAll("(?i)\\$needAuthc\\(\"[a-z0-9]*\"\\)", "");
		}

		//替换原来的拼装模板
		final BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());

		final Builder builder = new MappedStatement.Builder(mappedStatement.getConfiguration(), mappedStatement.getId(), new SqlSource() {

			@Override
			public BoundSql getBoundSql(final Object parameterObject) {
				return newBoundSql;
			}
		}, mappedStatement.getSqlCommandType());
		builder.cache(mappedStatement.getCache());
		builder.fetchSize(mappedStatement.getFetchSize());
		builder.flushCacheRequired(mappedStatement.isFlushCacheRequired());
		builder.keyGenerator(mappedStatement.getKeyGenerator());
//		builder.keyProperty(mappedStatement.getKeyProperty());
		builder.resource(mappedStatement.getResource());
		builder.resultMaps(mappedStatement.getResultMaps());
		builder.resultSetType(mappedStatement.getResultSetType());
		builder.statementType(mappedStatement.getStatementType());
		builder.timeout(mappedStatement.getTimeout());
		builder.useCache(mappedStatement.isUseCache());

		final MappedStatement newMappedStatement = builder.build();
		invocation.getArgs()[0] = newMappedStatement;

		return invocation.proceed();
	}

	@Override
	public Object plugin(final Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(final Properties properties) {
		this.properties = properties;
	}

}