package me.magicall.lang.reflect.proxy.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.magicall.lang.reflect.MethodSelector;
import me.magicall.lang.reflect.MethodSelector.SomeMethodSelectors;
import me.magicall.lang.reflect.MethodSelectorUsingSignature;
import me.magicall.lang.reflect.proxy.BaseInvocationHandler;
import me.magicall.lang.reflect.proxy.InvocationHandlerMethodInvokator;
import me.magicall.util.BeanUtil;
import me.magicall.util.ToMapable;
import me.magicall.util.kit.Kits;

public class MapBeanInvocationHandler extends BaseInvocationHandler implements ToMapable<String, Object> {

	protected static final InvocationHandlerMethodInvokator GETTER_INVOCATOR = (invocationHandler, proxy, method,
																				args) -> ((BeanProxy) invocationHandler).get(BeanUtil.getterNameToFieldName(method.getName()));
	protected static final InvocationHandlerMethodInvokator SETTER_INVOCATOR = (invocationHandler, proxy, method,
																				args) -> {
                                                                                    ((BeanProxy) invocationHandler).set(BeanUtil.setterNameToFieldName(method.getName()), args[0]);
                                                                                    return null;
                                                                                };
	protected static final MethodSelector GET_ALL_FIELDS_SELECTOR = new MethodSelectorUsingSignature("allFields");
	protected static final InvocationHandlerMethodInvokator GET_ALL_FIELDS = new InvocationHandlerMethodInvokator() {
		@Override
		public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method,
				final Object[] args) throws Throwable {
			final MapBeanInvocationHandler handler = (MapBeanInvocationHandler) invocationHandler;
			return handler.fields.keySet().toArray(new String[handler.fields.size()]);
		}
	};
	/**
	 * 代理bean所有字段和值
	 */
	protected final Map<String, Object> fields;

	public MapBeanInvocationHandler() {
		this(new HashMap<>());
	}

	public MapBeanInvocationHandler(final Map<String, Object> fields) {
		super();
		this.fields = fields;
		setMethodInvokator(SomeMethodSelectors.GETTER, GETTER_INVOCATOR);
		setMethodInvokator(SomeMethodSelectors.SETTER, SETTER_INVOCATOR);
		setMethodInvokator(GET_ALL_FIELDS_SELECTOR, GET_ALL_FIELDS);
		//可以替换掉父类赋予的equals的invocator，因为bean通常会有id之类的key字段可以使用。
		//但是对于两个bean实例，最好在程序内保持使用==来做equals判断，同时用一个工具方法实现用key来进行比较的equals方法。
		//因为key相同的两个bean实例，其他字段可能是不一致的，如果贸然重写原生equals方法，可能会带来潜在的bug。

		setMethodInvokator(SomeMethodSelectors.ALL, new InvocationHandlerMethodInvokator() {
			@Override
			public Object invoke(final InvocationHandler invocationHandler, final Object proxy, final Method method,
					final Object[] args)
					throws IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchMethodException, IllegalAccessException {
				final Method m = getClass().getMethod(method.getName(), method.getParameterTypes());
				return m.invoke(this, args);
			}
		});
	}

	public void set(final String fieldName, final Object value) {
		fields.put(fieldName, value);
	}

	public Object get(final String fieldName) {
		return fields.get(fieldName);
	}

	@Override
	public Map<String, Object> toMap() {
		return new LinkedHashMap<>(fields);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final Entry<?, ?> e : fields.entrySet()) {
			sb.append(Kits.OBJ.deepToString(e.getKey())).append('=')//
					.append(Kits.OBJ.deepToString(e.getValue())).append(' ');
		}
		return sb.toString();
	}
}