package me.magicall.db.test;

import java.lang.reflect.Method;

public class U {

	static boolean methodHasSql(final Method method) {
		return getSql(method) != null;
	}

	static String getSql(final Method method) {
//		final Annotation[] annotations = method.getAnnotations();
//		for (final Annotation annotation : annotations) {
//			if (annotation.getClass().isAnnotationPresent(IsSql.class)) {
//				return annotation.values();
//			}
//		}
		return null;
	}

	public static String unquote(String name) {
		final String[] quoteSigns = { "`", "'" };
		name = name.trim();
		for (final String s : quoteSigns) {
			if (name.startsWith(s)) {
				name = name.substring(1);
			}
			if (name.endsWith(s)) {
				name = name.substring(0, name.length() - 1);
			}
		}
		return name;
	}

	public static String quoteColumnName(final String name) {
		return '`' + name + '`';
	}
}
