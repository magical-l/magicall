package me.magicall.lang.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@FunctionalInterface
public interface InvocationHandlerMethodInvokator {

	Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method,
				  final Object... args) throws Throwable;

	enum SomeMethodInvocators implements InvocationHandlerMethodInvokator {
		RETURN_NULL {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method, final Object[] args) throws Throwable {
				return null;
			}
		}, //
		NO_IMPLEMENT {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method, final Object[] args)
					throws AbstractMethodError {
				throw new AbstractMethodError();
			}
		}, //
		METHOD_INVOKE {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method, final Object[] args)
					throws InvocationTargetException, IllegalArgumentException, IllegalAccessException {
				return method.invoke(proxy, args);
			}
		}, //
		METHOD_INVOKE_USING_INVOCATION_HANDLER {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method, final Object[] args)
					throws InvocationTargetException, IllegalArgumentException, IllegalAccessException {
				return method.invoke(invocationHandler, args);
			}
		}, //
		EQUALS {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method, final Object[] args) throws Throwable {
				return proxy.equals(args[0]);
			}
		}, //
		EQUALS_USING_INVOCATION_HANDLER {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method, final Object[] args) throws Throwable {
				return invocationHandler.equals(args[0]);
			}
		}, //
		HASH_CODE {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method, final Object[] args) throws Throwable {
				return proxy.hashCode();
			}
		}, //
		HASH_CODE_USING_INVOCATION_HANDLER {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method, final Object[] args) throws Throwable {
				return invocationHandler.hashCode();
			}
		}, //
		TO_STRING {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method, final Object[] args) throws Throwable {
				return proxy.toString();
			}
		}, //
		TO_STRING_USING_INVOCATION_HANDLER {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method, final Object[] args) throws Throwable {
				return invocationHandler.toString();
			}
		}, //
		;
	}
}
