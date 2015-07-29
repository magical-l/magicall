package me.magicall.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.magicall.consts.StrConst;
import me.magicall.util.kit.Kits;

public class MethodUtil {
	public static boolean isSetter(final Method method, final boolean checkReturnType) {
		{//name check
			final String methodName = method.getName();
			if (methodName.length() <= StrConst.SET_LEN) {
				return false;
			}
			if (!methodName.startsWith(StrConst.SET)) {
				return false;
			}
			//set后的首字母不能是小写.
			//注:在无大小写区别的语言中(比如汉语),isLowerCase和isUpperCase都返回false,
			//因此只能检查该字符是否小写字符,若是,则认为是一个以"set"开头的单词的一部分,而非一个"set xxx"
			if (Character.isLowerCase(methodName.charAt(StrConst.SET_LEN))) {
				return false;
			}
		}
		{//param check
			final Class<?>[] paramClasses = method.getParameterTypes();
			if (ArrayUtil.isEmpty(paramClasses)) {
				return false;
			}
			if (paramClasses.length > 1) {//没有参数的setXxx()方法或有2个以上参数的setXxx(),不处理
				return false;
			}
		}
		{//return type check
			if (checkReturnType && method.getReturnType() != Void.TYPE) {
				return false;
			}
		}
		return true;
	}

	public static boolean isGetter(final Method method) {
		{//name check
			final String methodName = method.getName();
			if (methodName.length() <= StrConst.GET_LEN) {
				return false;
			}
			if (!methodName.startsWith(StrConst.GET)) {
				return false;
			}
			//get后的首字母不能是小写.
			//注:在无大小写区别的语言中(比如汉语),isLowerCase和isUpperCase都返回false,
			//因此只能检查该字符是否小写字符,若是,则认为是一个以"get"开头的单词的一部分,而非一个"get xxx"
			if (Character.isLowerCase(methodName.charAt(StrConst.GET_LEN))) {
				return false;
			}
		}
		{//param check
			final Class<?>[] paramClasses = method.getParameterTypes();
			if (!ArrayUtil.isEmpty(paramClasses)) {
				return false;
			}
		}
		{//return type check
			if (method.getReturnType() == Void.TYPE) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取指定类的指定字段的adder方法（addFieldName），忽略名字大小写，并且是参数包容的。该字段的名字为Class的简单名，首字母小写。
	 * （如：某类A有setString(Object o)方法，用A.class和String.class调用本方法，可以获得此Method对象)。
	 * 
	 * @param clazz
	 * @param fieldClass
	 * @return
	 */
	public static Method getAdder(final Class<?> clazz, final Class<?> fieldClass) {
		return findMethodIgnoreCaseAndArgsTypesAssigned(clazz.getMethods(), BeanUtil.toAdderName(fieldClass.getSimpleName()), fieldClass);
	}

	/**
	 * 获取某字段的setter，忽略名字大小写，并且是参数包容的。该字段的名字为Class的简单名，首字母小写。
	 * （如：某类A有setString(Object o)方法，用A.class和String.class调用本方法，可以获得此Method对象)。
	 * 
	 * @param clazz
	 * @param fieldClass
	 * @return
	 */
	public static Method getSetterIgnoreNameCaseAndTypeAssigned(final Class<?> clazz, final Class<?> fieldClass) {//ignore case and type
		return findMethodIgnoreCaseAndArgsTypesAssigned(clazz.getMethods(), BeanUtil.fieldNameToSetterName(fieldClass.getSimpleName()), fieldClass);
	}

	/**
	 * 执行方法。不会抛出受检异常。如果执行时抛出异常，则返回null。对执行结果关心度不高的场景很有效。
	 * 
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(final Object obj, final Method method, final Object... args) {
		try {
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}
			return method.invoke(obj, args);
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param obj
	 * @param methods
	 * @param methodName
	 * @param args
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethodIgnoreCaseAndArgsTypesAssigned(final Object obj, final Method[] methods, final String methodName, final Object... args)
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return invokeMethodIgnoreCaseAndArgsTypesAssigned(obj, Arrays.asList(methods), methodName, args);
	}

	public static Object invokeMethodIgnoreCaseAndArgsTypesAssigned(final Object obj, final Collection<Method> methods, final String methodName,
			final Object... args) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		final Method method = findMethodIgnoreCaseAndArgsTypesAssigned(methods, methodName,
				ClassUtil.objsToClasses(args));
		if (method == null) {
			throw new NoSuchMethodException();
		}
		return method.invoke(obj, args);
	}

	/**
	 * 从一些方法中按名字和参数获取某方法，忽略名字大小写，并且是参数包容的。该字段的名字为Class的简单名，首字母小写。
	 * （如：某类A有setString(Object o)方法，用A.class和String.class调用本方法，可以获得此Method对象)。
	 * 
	 * @param methods
	 * @param methodName
	 * @param argsClasses
	 * @return
	 */
	public static Method findMethodIgnoreCaseAndArgsTypesAssigned(final Method[] methods, final String methodName, final Class<?>... argsClasses) {
		return findMethodIgnoreCaseAndArgsTypesAssigned(Arrays.asList(methods), methodName, argsClasses);
	}

	/**
	 * 从一些方法中按名字和参数获取某方法，忽略名字大小写，并且是参数包容的。该字段的名字为Class的简单名，首字母小写。
	 * （如：某类A有setString(Object o)方法，用A.class和String.class调用本方法，可以获得此Method对象)。
	 * 
	 * @param methods
	 * @param methodName
	 * @param argsClasses
	 * @return
	 */
	public static Method findMethodIgnoreCaseAndArgsTypesAssigned(final Collection<Method> methods, final String methodName, final Class<?>... argsClasses) {
		Method method = null;
		for (final Method m : methods) {
			final String name = m.getName();
			final Class<?>[] argTypes = m.getParameterTypes();
			if (argsClasses.length != argTypes.length) {
				continue;
			}
			if (name.equalsIgnoreCase(methodName)) {
				if (Arrays.equals(argTypes, argsClasses)) {
					return m;
				}
				int i = 0;
				for (final Class<?> argType : argTypes) {
					if (argType.isAssignableFrom(argsClasses[i])) {
						++i;
					} else {
						break;
					}
				}
				if (i == argsClasses.length) {
					method = m;
				}
			}
		}
		return method;
	}

	private static ConcurrentHashMap<Class<?>, Collection<Method>> ALL_GETTERS_WITHOUT_GET_CLASS_CACHE = new ConcurrentHashMap<>();

	/**
	 * 获取一个类的所有getter,不包括getClass
	 * 
	 * @param clazz
	 * @return
	 */
	public static Collection<Method> getAllGettersWithoutGetClass(final Class<?> clazz) {
		Collection<Method> collection = ALL_GETTERS_WITHOUT_GET_CLASS_CACHE.get(clazz);
		if (collection == null) {
			collection = new ArrayList<>();
			final Method[] methods = clazz.getMethods();
			for (final Method m : methods) {
				final String name = m.getName();
				if (name.startsWith(StrConst.GET)//以get开头
						&& !name.equals("getClass")//不是getClass
						&& name.length() > StrConst.GET_LEN//get后面还有字
						&& Character.isUpperCase(name.charAt(StrConst.GET_LEN))//get后面的字符是大写字符
						&& m.getParameterTypes().length == 0//没有参数
						&& m.getReturnType() != Void.class//有返回值
				) {
					collection.add(m);
				}
			}
			ALL_GETTERS_WITHOUT_GET_CLASS_CACHE.put(clazz, collection);
		}
		return collection;
	}

	private static ConcurrentHashMap<Class<?>, Collection<Method>> ALL_SETTERS_CACHE = new ConcurrentHashMap<>();

	/**
	 * 获取一个类的所有setter.
	 * 对返回结果进行修改是安全的.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Collection<Method> getAllSetters(final Class<?> clazz) {
		Collection<Method> collection = ALL_SETTERS_CACHE.get(clazz);
		if (collection == null) {
			final Method[] methods = clazz.getMethods();
			collection = new ArrayList<>(methods.length);
			for (final Method m : methods) {
				final String name = m.getName();
				if (name.startsWith(StrConst.SET)//以set开头
						&& name.length() > StrConst.SET_LEN//set后面还有字
						&& Character.isUpperCase(name.charAt(StrConst.SET_LEN))//set后面的字符是大写字符
						&& m.getParameterTypes().length == 1//有一个参数
				) {
					collection.add(m);
				}
			}
			ALL_SETTERS_CACHE.put(clazz, collection);
		}
		return new ArrayList<>(collection);
	}

	/**
	 * 获取一个类的所有setter.如果存在两个名字相同(且都是1个参数)的setter,保留参数的类型(Class)较"小"者.
	 * 对返回结果进行修改是安全的.
	 * 
	 * @param clazz
	 * @return
	 * @throws RuntimeException 如果存在两个名字相同(且都是1个参数)的setter,但它们的参数不相容.
	 */
	public static Collection<Method> getAllSettersForField(final Class<?> clazz) throws RuntimeException {
		final Collection<Method> setters0 = getAllSetters(clazz);
		if (Kits.COLL.isEmpty(setters0)) {
			return setters0;
		}
		final Map<String, Method> setterNameMap = new LinkedHashMap<>(setters0.size());
		for (final Method setter : setters0) {
			if (!isSetter(setter, false)) {
				continue;
			}
			final Class<?> fieldClass = setter.getParameterTypes()[0];
			final String fieldName = BeanUtil.setterNameToFieldName(setter.getName());
			final Method setterFromMap = setterNameMap.get(fieldName);
			if (setterFromMap == null) {
				setterNameMap.put(fieldName, setter);
			} else {
				final Class<?> setterArgFromMap = setterFromMap.getParameterTypes()[0];
				if (fieldClass.isAssignableFrom(setterArgFromMap)) {
					continue;
				} else if (setterArgFromMap.isAssignableFrom(fieldClass)) {
					setterNameMap.put(fieldName, setter);
				} else {
					throw new RuntimeException('类' + clazz.getSimpleName() + '的' + fieldName + "字段有两个setter方法,但是参数却又不相容");
				}
			}
		}
		return new ArrayList<>(setterNameMap.values());
	}

	public static Method getGetter(final String fieldName, final Class<?> c) {
		try {
			return c.getMethod(BeanUtil.fieldNameToGetterName(fieldName));
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
