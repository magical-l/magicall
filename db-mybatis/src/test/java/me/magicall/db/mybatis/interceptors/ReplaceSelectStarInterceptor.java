package me.magicall.db.mybatis.interceptors;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.magicall.coll.ElementTransformerUtil;
import me.magicall.db.meta.DbColumn;
import me.magicall.db.meta.TableMeta;
import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.mybatis.util.MyBatisUtil.MappedStatementBuilderBuilder;
import me.magicall.util.kit.Kits;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Intercepts({//
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),//
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }),//
})
@Component
public class ReplaceSelectStarInterceptor extends InterceptorTemplate implements Interceptor {

	private static final int TABLE_NAME_INDEX = 1;
	//matches select * from table
	private static final Pattern SELECT_STAR_FROM_TABLE = Pattern.compile(
			"^\\s*select\\s+\\*\\s+from\\s+(\\w+)(\\s+(.*))?$", Pattern.CASE_INSENSITIVE);

	@Autowired
//	@Qualifier("mybatisTableMetaBuilder")
	private TableMetaAccessor tableMetaBuilder;

	@Override
	protected void internalIntercept(final Invocation invocation) throws Throwable {
		final Object[] executorArgs = invocation.getArgs();

		final MappedStatement mappedStatement = (MappedStatement) executorArgs[0];
		final Object arg = executorArgs[1];

		final BoundSql boundSql = mappedStatement.getBoundSql(arg);

		final String sql = boundSql.getSql();
		final Matcher matcher = SELECT_STAR_FROM_TABLE.matcher(sql);
		if (!matcher.matches()) {
			return;
		}
		final String tableName = matcher.group(TABLE_NAME_INDEX);
		final TableMeta tableMeta = tableMetaBuilder.getTableMetaIgnoreCase(tableName);
		final DbColumn[] columns = tableMeta.getColumnsArray();

		final StringBuilder sb = new StringBuilder("select ");
		Kits.STR.join(sb, Arrays.asList(columns), ",", ElementTransformerUtil.TO_NAME);
		sb.append(" from ").append(tableName);
		final int groupCount = matcher.groupCount();
		for (int i = TABLE_NAME_INDEX + 1; i < groupCount; ++i) {
			sb.append(Kits.STR.checkToEmptyValue(matcher.group(i)));
		}

		final BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sb.toString(),
				boundSql.getParameterMappings(), boundSql.getParameterObject());

		final MappedStatementBuilderBuilder builder = new MappedStatementBuilderBuilder(mappedStatement);
		builder.setSqlSource(new SqlSource() {
			@Override
			public BoundSql getBoundSql(final Object parameterObject) {
				return newBoundSql;
			}
		});

		executorArgs[0] = builder.build().build();
	}
//	public static void main(final String[] args) {
//		final Pattern pattern = Pattern.compile("^\\s*select\\s+\\*\\s+from\\s+(\\w+)(\\s+(.*))?$", Pattern.CASE_INSENSITIVE);
//		final Matcher matcher = pattern.matcher(" SELECT * FROM t1 where id=? and a=b ");
//		System.out.println("@@@@@@" + matcher.matches());
//		if (matcher.matches()) {
//			System.out.println("@@@@@@" + matcher.groupCount());
//			for (int i = 0; i < matcher.groupCount(); ++i) {
//				System.out.println("@@@@@@" + matcher.group(i));
//			}
//		}
//	}

}
