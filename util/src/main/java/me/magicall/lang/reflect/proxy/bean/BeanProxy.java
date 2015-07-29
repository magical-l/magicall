package me.magicall.lang.reflect.proxy.bean;

import java.lang.reflect.InvocationHandler;
import java.util.Map;

/**
 * 这是一个简单java bean的动态代理类
 * 
 * @author MaGiCalL
 */
public class BeanProxy extends MapBeanInvocationHandler implements InvocationHandler {
	public BeanProxy() {
		super();
	}

	public BeanProxy(final Map<String, Object> fields) {
		super(fields);
		//可以替换掉父类赋予的equals的invocator，因为bean通常会有id之类的key字段可以使用。
		//但是对于两个bean实例，最好在程序内保持使用==来做equals判断，同时用一个工具方法实现用key来进行比较的equals方法。
		//因为key相同的两个bean实例，其他字段可能是不一致的，如果贸然重写原生equals方法，可能会带来潜在的bug。
	}
}