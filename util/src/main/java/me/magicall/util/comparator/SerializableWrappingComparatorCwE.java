package me.magicall.util.comparator;

import me.magicall.util.comparator.ComparatorWithEquivalent.SerializableComparatorWithEquivalent;

public class SerializableWrappingComparatorCwE<T> extends WrappingComparatorCwE<T>//
		implements SerializableComparatorWithEquivalent<T> {

	private static final long serialVersionUID = -3788553586097931549L;

	public SerializableWrappingComparatorCwE(final SerializableComparator<T> comparaor) {
		super(comparaor);
	}
}
