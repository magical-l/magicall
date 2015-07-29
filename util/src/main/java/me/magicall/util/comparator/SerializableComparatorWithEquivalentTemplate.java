package me.magicall.util.comparator;

import me.magicall.util.comparator.ComparatorWithEquivalent.SerializableComparatorWithEquivalent;

public abstract class SerializableComparatorWithEquivalentTemplate<T> implements
		SerializableComparatorWithEquivalent<T> {

	private static final long serialVersionUID = -1694832279086847832L;

	@Override
	public boolean equals(final T o1, final T o2) {
		return compare(o1, o2) == 0;
	}

}
