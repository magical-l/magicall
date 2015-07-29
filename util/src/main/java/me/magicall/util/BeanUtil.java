package me.magicall.util;

import me.magicall.consts.StrConst;
import me.magicall.lang.reflect.proxy.ProxyUtil;
import me.magicall.lang.reflect.proxy.bean.BeanProxy;

public class BeanUtil {

	public static String toAdderName(final String fieldName) {
		return "add" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}

	public static String fieldNameToGetterName(final String fieldName) {
		return StrConst.GET + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}

	public static String fieldNameToSetterName(final String fieldName) {
		return StrConst.SET + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}

	public static String getterNameToFieldName(final String methodName) {
		return Character.toLowerCase(methodName.charAt(StrConst.GET_LEN))
				+ methodName.substring(StrConst.GETTER_FIELD_NAME);
	}

	public static String setterNameToFieldName(final String methodName) {
		return Character.toLowerCase(methodName.charAt(StrConst.SET_LEN))
				+ methodName.substring(StrConst.SETTER_FIELD_NAME);
	}

	/**
	 * 创建参数Class的一个实例对象. 如果参数Class代表一个类(class),则使用其无参构造函数创建一个实例,如果该类没有无参构造函数,则返回null; 如果参数Class代表一个接口(interface),则使用动态代理生成一个代理实例.由于是bean,因此该代理实例仅代理getter和setter方法.
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newBean(final Class<? extends T> clazz) {
		return (T) (clazz.isInterface() ? ProxyUtil.newProxyInstance(new BeanProxy(), clazz) : ClassUtil
				.newInstance(clazz));
	}
}
