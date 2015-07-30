package me.magicall.lang.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.magicall.lang.reflect.MethodSelector.SomeMethodSelectors;
import me.magicall.util.ArrayUtil;
import me.magicall.util.ClassUtil;

public class ProxyUtil {

	private ProxyUtil() {
	}

	public static void main(final String... args) throws Exception {
		List<Integer> list = new ArrayList<>();
		list = synchronizedWrapper(list);
		System.out.println("@@@@@@ProxyUtil.main():" + list.size());

		final String s = "";
		final CharSequence c = synchronizedWrapper(s);
		System.out.println("@@@@@@ProxyUtil.main():" + c.length());

		final Number//this class must be interface
		n = synchronizedWrapper(1);
		System.out.println("@@@@@@ProxyUtil.main():" + n);
	}

	static class A {
		private int id;

		public int getId() {
			return id;
		}

		public void setId(final int id) {
			this.id = id;
		}
	}

	public static <I, C extends I> I synchronizedWrapper(final C source) {
		final InvocationHandlerMethodInvokator methodInvocator = new InvocationHandlerMethodInvokator() {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method,
					final Object[] args)
					throws InvocationTargetException, IllegalArgumentException, IllegalAccessException {
				synchronized (this) {
					return method.invoke(source, args);
				}
			}
		};
		final BaseInvocationHandler h = new BaseInvocationHandler();
		h.setMethodInvokator(SomeMethodSelectors.ALL, methodInvocator);
		return newProxyInstance(h, ClassUtil.getAllInterfaces(source.getClass()));
	}

	public static <T> T newProxyInstance(final InvocationHandler h, final Collection<Class<?>> interfacesToImplement) {
		return newProxyInstance(h.getClass().getClassLoader(), h, interfacesToImplement);
	}

	public static <T> T newProxyInstance(final ClassLoader loader, final InvocationHandler h,
			final Collection<Class<?>> interfacesToImplement) {
		return newProxyInstance(loader, h, interfacesToImplement.toArray(new Class<?>[interfacesToImplement.size()]));
	}

	@SuppressWarnings("unchecked")
	public static <T> T newProxyInstance(final InvocationHandler h, final Class<?> interfacesToImplement) {
		return newProxyInstance(h, ArrayUtil.asArray(interfacesToImplement));
	}

	public static <T> T newProxyInstance(final InvocationHandler h, final Class<?>... interfacesToImplement) {
		return newProxyInstance(h.getClass().getClassLoader(), h, interfacesToImplement);
	}

	public static <T> T newProxyInstance(final ClassLoader loader, final InvocationHandler h,
			final Class<?> interfacesToImplement) {
		return newProxyInstance(loader, h, interfacesToImplement);
	}

	@SuppressWarnings("unchecked")
	public static <T> T newProxyInstance(final ClassLoader loader, final InvocationHandler h,
			final Class<?>... interfacesToImplement) {
		return (T) Proxy.newProxyInstance(loader, interfacesToImplement, h);
	}
}
