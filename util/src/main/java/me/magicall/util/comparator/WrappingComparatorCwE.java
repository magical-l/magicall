package me.magicall.util.comparator;

import java.util.Comparator;

public class WrappingComparatorCwE<T> extends CompartorAndEquivalentTemplate<T> {

	private final Comparator<? super T> comparaor;

	public WrappingComparatorCwE(final Comparator<? super T> comparaor) {
		this.comparaor = comparaor;
	}

	@Override
	public int compare(final T o1, final T o2) {
		return getComparaor().compare(o1, o2);
	}

	@SuppressWarnings("unchecked")
	public Comparator<T> getComparaor() {
		return (Comparator<T>) comparaor;
	}
}
