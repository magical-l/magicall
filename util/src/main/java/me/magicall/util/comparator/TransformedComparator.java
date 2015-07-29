package me.magicall.util.comparator;

import java.util.Comparator;

import me.magicall.util.transformer.Transformer;

public class TransformedComparator<T> implements Comparator<T> {

	private final Transformer<T, Comparable<?>> tf;

	public TransformedComparator(final Transformer<T, Comparable<?>> tf) {
		super();
		this.tf = tf;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int compare(final T o1, final T o2) {
		return ((Comparable) tf.transform(o1)).compareTo(tf.transform(o2));
	}

}
