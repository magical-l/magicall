package me.magicall.util.comparator;

public abstract class ComparatorAndEquivalentUsingComparbleFieldTemplate<T> extends CompartorAndEquivalentTemplate<T> {

	@Override
	@SuppressWarnings("unchecked")
	public int compare(final T o1, final T o2) {
		@SuppressWarnings("rawtypes")
		final Comparable c1 = comparableField(o1);
		@SuppressWarnings("rawtypes")
		final Comparable c2 = comparableField(o2);
		return c1.compareTo(c2);
	}

	protected abstract Comparable<?> comparableField(T o);

	public static abstract class SerializableComparatorAndEquivalentUsingFieldTemplate<T> //
			extends ComparatorAndEquivalentUsingComparbleFieldTemplate<T>//
			implements SerializableComparatorWithEquivalent<T> {

		private static final long serialVersionUID = 8749566554152625300L;
	}
}
