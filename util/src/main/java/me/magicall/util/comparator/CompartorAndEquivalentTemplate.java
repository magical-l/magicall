package me.magicall.util.comparator;

public abstract class CompartorAndEquivalentTemplate<T> implements ComparatorWithEquivalent<T> {

	public CompartorAndEquivalentTemplate() {
		super();
	}

	@Override
	public boolean equals(final T o1, final T o2) {
		return compare(o1, o2) == 0;
	}

}