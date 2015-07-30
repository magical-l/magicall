/**
 * 
 */
package me.magicall.coll.util;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.empty.EmptyColl;
import me.magicall.coll.transformed.TransformedSet;
import me.magicall.coll.wrap.UnmodifiableWrapSet;
import me.magicall.mark.Unmodifiable;
import me.magicall.util.kit.Kits;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;

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

	//====================================================

	private static final long serialVersionUID = 6362477550287752819L;

	private Object readResolve() {
		return INSTANCE;
	}
}