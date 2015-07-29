/**
 * 
 */
package me.magicall.coll.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.empty.EmptyColl;
import me.magicall.coll.transformed.TransformedSet;
import me.magicall.coll.wrap.UnmodifiableWrapSet;
import me.magicall.tagInterfaces.Unmodifiable;
import me.magicall.util.comparator.ComparatorAndEquivalentUtil;
import me.magicall.util.comparator.ComparatorUtil;
import me.magicall.util.kit.Kits;

public final class SetKit extends AbsCollectionKit<Set<?>> {

	private static final Class<?> MAIN_CLASS = Set.class;
	//-----------------------------------------
	public static final SetKit INSTANCE = new SetKit();

	//-----------------------------------------
	@SuppressWarnings("unchecked")
	private SetKit() {
		super((Class<Set<?>>) MAIN_CLASS, EmptyColl.INSTANCE);
	}

	//-----------------------------------------
	//这些覆盖父类方法,是为了返回值能带上泛型
	@SuppressWarnings("unchecked")
	@Override
	public <E, E1> Set<E> emptyValue() {
		return (Set) EmptyColl.INSTANCE;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Set<T> forceSuit(final Set<?> source) {
		return (Set<T>) source;
	}

	//-----------------------------------------
	public <E> E randomOne(final Set<E> source) {
		return Kits.COLL.randomOne(source);
	}

	@SuppressWarnings("unchecked")
	public <E> SortedSet<E> emptySortedSet() {
		return (SortedSet) EmptyColl.INSTANCE;
	}

	@SuppressWarnings("unchecked")
	public <T, T1 extends T> Set<T> suit(final Set<T1> from) {
		return (Set<T>) from;
	}

	public <F, T> Set<T> transform(final Set<? extends F> source, final ElementTransformer<? super F, ? extends T> transformer) {
		return new TransformedSet<>(source, transformer);
	}

	public <T> Set<T> unmodifiable(final Set<T> source) {
		if (source instanceof Unmodifiable) {
			return source;
		}
		if (source == Collections.EMPTY_SET) {
			return source;
		}
		return new UnmodifiableWrapSet<>(source);
	}

	public <E> E maxElement(final Set<E> source, final Comparator<? super E> comparator) {
		if (isEmpty(source)) {
			return null;
		}
		final Iterator<E> i = source.iterator();
		E rt = i.next();
		while (i.hasNext()) {
			final E e = i.next();
			if (comparator.compare(rt, e) < 0) {
				rt = e;
			}
		}
		return rt;
	}

	public <E> E minElement(final Set<E> source, final Comparator<? super E> comparator) {
		if (isEmpty(source)) {
			return null;
		}
		final Iterator<E> i = source.iterator();
		E rt = i.next();
		while (i.hasNext()) {
			final E e = i.next();
			if (comparator.compare(rt, e) > 0) {
				rt = e;
			}
		}
		return rt;
	}

	@SuppressWarnings("unchecked")
	public <E> E maxElement(final Set<E> source) {
		final Comparator comparator = ComparatorAndEquivalentUtil.<Comparable>comparableComparator();
		return (E) maxElement(source, comparator);
	}

	@SuppressWarnings("unchecked")
	public <E> E minElement(final Set<E> source) {
		final Comparator comparator = ComparatorAndEquivalentUtil.<Comparable>comparableComparator();
		return (E) minElement(source, comparator);
	}

	//====================================================
	private static final long serialVersionUID = 6362477550287752819L;

	private Object readResolve() {
		return INSTANCE;
	}
}