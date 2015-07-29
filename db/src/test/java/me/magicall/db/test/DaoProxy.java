package me.magicall.db.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import me.magicall.lang.reflect.MethodSelector;
import me.magicall.lang.reflect.proxy.BaseInvocationHandler;
import me.magicall.lang.reflect.proxy.InvocationHandlerMethodInvokator;
import me.magicall.lang.reflect.proxy.ProxyUtil;
import me.magicall.lang.reflect.proxy.bean.BeanProxy;

public class DaoProxy extends BaseInvocationHandler implements InvocationHandler {
	private static final MethodSelector HAS_SQL_METHOD_SELECTOR = method -> U.methodHasSql(method);
	private static final InvocationHandlerMethodInvokator DB_ACCESS_METHOD_INVOCATOR = (invocationHandler, proxy,
																						method, args) -> {
                                                                                            final Class<?> returnType = method.getReturnType();

                                                                                            //TODO:测试中。从method的注解上解析sql，去数据库搞飞机，然后返回适当的东西。
                                                                                            if (args[0].equals(1)) {
                                                                                                final BeanProxy beanProxy = new BeanProxy();
                                                                                                beanProxy.set("id", 1);
                                                                                                beanProxy.set("name", "abc");
                                                                                                final T1Interface bean = ProxyUtil.newProxyInstance(beanProxy, returnType);
                                                                                                return bean;
                                                                                            } else {
                                                                                                return null;
                                                                                            }
                                                                                        };

	public DaoProxy() {
		super();
		setMethodInvokator(HAS_SQL_METHOD_SELECTOR, DB_ACCESS_METHOD_INVOCATOR);
	}
}