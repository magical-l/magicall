package me.magicall.db.mybatis.interceptors;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

public abstract class InterceptorTemplate implements Interceptor {

	@Override
	public Object intercept(final Invocation invocation) throws Throwable {
		internalIntercept(invocation);
		return invocation.proceed();
	}

	protected abstract void internalIntercept(final Invocation invocation) throws Throwable;

	@Override
	public Object plugin(final Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(final Properties properties) {
	}
}
