package me.magicall.lang.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import me.magicall.lang.reflect.MethodSelector;
import me.magicall.lang.reflect.MethodSelector.SomeMethodSelectors;
import me.magicall.lang.reflect.proxy.InvocationHandlerMethodInvokator.SomeMethodInvocators;
import me.magicall.util.touple.Tuple;
import me.magicall.util.touple.TwoTuple;

/**
 * 这是一个实现动态代理的基础类，它代理了几个主要的基本方法
 * 
 * @author MaGiCalL
 */
public class BaseInvocationHandler implements InvocationHandler {

	/**
	 * 从后向前匹配。排在后面的（后加入的）优先级高
	 */
	protected final List<TwoTuple<MethodSelector, InvocationHandlerMethodInvokator>> beanProxyMethodDispatchers;

	public BaseInvocationHandler() {
		this(new ArrayList<>());
		//设置一个默认的，代理所有由Object定义的非final方法：toString、hashCode、equals、finalize、clone
		//代理方式是直接用本对象调用该方法。如toString将会调用到本对象的toString
		//子类如果要重新定义这些方法的调用方式，需要调用removeMethodInvokator方法移除SomeMethodSelectors.ALL_OBJECT_METHOD
		setMethodInvokator(SomeMethodSelectors.ALL, SomeMethodInvocators.NO_IMPLEMENT);
		setMethodInvokator(SomeMethodSelectors.ALL_OBJECT_METHOD, SomeMethodInvocators.METHOD_INVOKE_USING_INVOCATION_HANDLER);
	}

	protected BaseInvocationHandler(final List<TwoTuple<MethodSelector, InvocationHandlerMethodInvokator>> beanProxyMethodDispatchers) {
		super();
		this.beanProxyMethodDispatchers = beanProxyMethodDispatchers;
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		final ListIterator<TwoTuple<MethodSelector, InvocationHandlerMethodInvokator>> i//
		= beanProxyMethodDispatchers.listIterator(beanProxyMethodDispatchers.size());
		while (i.hasPrevious()) {
			final TwoTuple<MethodSelector, InvocationHandlerMethodInvokator> e = i.previous();
			final MethodSelector methodSelector = e.getFirst();
			if (methodSelector.accept(method)) {
				return invoke(methodSelector, e.getSecond(), proxy, method, args);
			}
		}
		//will not come here
		return noImplementedMethod(proxy, method, args);
	}

	/**
	 * @param proxy
	 * @param method
	 * @param args
	 * @param e
	 * @return
	 * @throws Throwable
	 */
	protected Object invoke(final MethodSelector methodSelector, final InvocationHandlerMethodInvokator methodInvocator, final Object proxy,
			final Method method, final Object... args) throws Throwable {
		return methodInvocator.invoke(this, proxy, method, args);
	}

	/**
	 * @param proxy
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	protected Object noImplementedMethod(final Object proxy, final Method method, final Object... args) throws Throwable {
		return SomeMethodInvocators.NO_IMPLEMENT.invoke(this, proxy, method, args);
	}

	public void setMethodInvokator(final MethodSelector methodSelector, final InvocationHandlerMethodInvokator methodInvocator) {
		//原先并没有这个东西，就加上
		beanProxyMethodDispatchers.add(Tuple.of(methodSelector, methodInvocator));
	}

	public void removeMethodInvokator(final MethodSelector methodSelector) {
		for (final Iterator<TwoTuple<MethodSelector, InvocationHandlerMethodInvokator>> i = beanProxyMethodDispatchers.iterator(); i.hasNext();) {
			final TwoTuple<MethodSelector, InvocationHandlerMethodInvokator> t = i.next();
			if (t.getFirst().equals(methodSelector)) {
				i.remove();//maybe more matched
			}
		}
	}
}
