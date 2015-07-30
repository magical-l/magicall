package me.magicall.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.magicall.coll.KeyAccessor;
import me.magicall.coll.util.IdAccessor;
import me.magicall.mark.HasIdGetter;

import com.google.common.collect.Maps;

public class EnumUtil<E extends Enum<E>> {

	private EnumUtil() {
	}

	public static <E extends Enum<E>> E valueOf(final Class<E> clazz, final String name) {
		if (clazz == null) {
			return null;
		}
		final E[] es = clazz.getEnumConstants();
		for (final E e : es) {
			if (e.name().equalsIgnoreCase(name)) {
				return e;
			}
		}
		return null;
	}

	//==================下面是新写的20110319
	private static class EnumInfo<E extends Enum<E>> {
		Map<Object, E> keyEnumMap = Maps.newHashMap();
	}

	private static final ConcurrentHashMap<Class<?>, ConcurrentHashMap<KeyAccessor<?, ?>, EnumInfo<?>>> map = new ConcurrentHashMap<>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <E extends Enum<E>> ConcurrentHashMap<KeyAccessor<?, E>, EnumInfo<E>> get(final Class<?> c) {
		return (ConcurrentHashMap) map.get(c);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E extends Enum<E>> void reg(final Class<E> c, final KeyAccessor<?, E> idFieldSelector) {
		ConcurrentHashMap<KeyAccessor<?, E>, EnumInfo<E>> m = get(c);
		if (m == null) {
			m = new ConcurrentHashMap<>();
			map.putIfAbsent(c, (ConcurrentHashMap) m);
		}
		EnumInfo<E> i = m.get(idFieldSelector);
		if (i == null) {
			i = new EnumInfo<>();
			final E[] values = c.getEnumConstants();
			for (final E e : values) {
				i.keyEnumMap.put(idFieldSelector.extract(e), e);
			}
			m.putIfAbsent(idFieldSelector, i);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E extends Enum<E> & HasIdGetter<?>> void reg(final Class<E> c) {
		reg(c, (KeyAccessor) IdAccessor.INSTANCE);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E extends Enum<E>> E get(final Class<E> c, final Object key) {
		return (E) get(c, (KeyAccessor) IdAccessor.INSTANCE, key);
	}

	public static <E extends Enum<E>> E get(final Class<E> c, final KeyAccessor<?, E> idFieldSelector, final Object key) {
		ConcurrentHashMap<KeyAccessor<?, E>, EnumInfo<E>> m = get(c);
		if (m == null) {
			reg(c, idFieldSelector);
			m = get(c);
		}
		final EnumInfo<E> i = m.get(idFieldSelector);
		if (i == null) {
			return null;
		}
		return i.keyEnumMap.get(key);
	}
}
