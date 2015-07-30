package me.magicall.util.comparator;

import java.util.Comparator;

public interface ComparatorWithEquivalent<T> extends Comparator<T>, Equivalent<T> {

	interface SerializableComparatorWithEquivalent<T> //
			extends ComparatorWithEquivalent<T>, SerializableComparator<T>, SerializableEquivalent<T> {

	}
}
