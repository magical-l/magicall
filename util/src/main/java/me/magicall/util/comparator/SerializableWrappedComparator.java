package me.magicall.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

public class SerializableWrappedComparator<T> implements Comparator<T>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6191126804089042069L;

	private transient Comparator<T> comparator;
	private final Class<Comparator<T>> clazz;

	@SuppressWarnings("unchecked")
	public SerializableWrappedComparator(final Comparator<? super T> comparator) {
		super();
		this.comparator = (Comparator<T>) comparator;
		clazz = (Class<Comparator<T>>) comparator.getClass();
	}

	public SerializableWrappedComparator(final Class<Comparator<T>> clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public int compare(final T o1, final T o2) {
		if (comparator == null) {
			synchronized (clazz) {
				if (comparator == null) {
					try {
						comparator = clazz.newInstance();
					} catch (final InstantiationException e) {
						throw new IllegalStateException("wrapped comparator cannot be init", e);
					} catch (final IllegalAccessException e) {
						throw new IllegalStateException("wrapped comparator cannot be init", e);
					}
				}
			}
		}
		return comparator.compare(o1, o2);
	}

}
