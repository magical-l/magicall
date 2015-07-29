package me.magicall.util.comparator;

import java.lang.reflect.Field;
import java.util.Comparator;

public class ComparableFieldComparator<T> implements Comparator<T> {

	private final Field field;

	public ComparableFieldComparator(final Field field) {
		super();
		if (!Comparable.class.isAssignableFrom(field.getType())) {
			throw new IllegalArgumentException("field should be comparable but this is not:" + field.getName());
		}
		this.field = field;
	}

	public ComparableFieldComparator(final Class<? extends T> clazz, final String fieldName) throws SecurityException, NoSuchFieldException {
		this(clazz.getField(fieldName));
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compare(final T o1, final T o2) {
		if (!field.getDeclaringClass().isAssignableFrom(o1.getClass())) {
			throw new IllegalArgumentException("arguments' class has no field named " + field.getName());
		}
		final boolean accessable = field.isAccessible();
		if (!accessable) {
			field.setAccessible(true);
		}
		try {
			@SuppressWarnings("rawtypes")
			final Comparable c1 = (Comparable) field.get(o1);
			@SuppressWarnings("rawtypes")
			final Comparable c2 = (Comparable) field.get(o2);
			return c1.compareTo(c2);
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("can not access the field : " + field.getName());
		} finally {
			if (!accessable) {
				field.setAccessible(false);
			}
		}
	}

}
