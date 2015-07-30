package me.magicall.coll.unmodifiable;

import me.magicall.coll.CollFactory.I;
import me.magicall.coll.sorted.Sorted;
import me.magicall.mark.Unmodifiable;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static me.magicall.consts.CommonConst.NOT_FOUND_INDEX;

public abstract class UnmodifiableListTemplate<E> extends AbstractList<E>//
		implements Unmodifiable, Serializable, List<E>, Sorted {

	private static final long serialVersionUID = -4636171047705942558L;

	protected UnmodifiableListTemplate() {
		super();
	}

	//-------------------------------------------------

	@Override
	public final Iterator<E> iterator() {
		return I.unmodifiable(iterator0());
	}

	protected Iterator<E> iterator0() {
		return super.iterator();
	}

	@Override
	public final ListIterator<E> listIterator() {
		return super.listIterator();
	}

	@Override
	public final ListIterator<E> listIterator(final int index) {
		return I.unmodifiable(listIterator0(index));
	}

	protected ListIterator<E> listIterator0(final int index) {
		return super.listIterator(index);
	}

	@Override
	public final List<E> subList(final int fromIndex, final int toIndex) {
		return subList0(fromIndex, toIndex);
	}

	protected List<E> subList0(final int fromIndex, final int toIndex) {
		return super.subList(fromIndex, toIndex);
	}

	//-------------------------------------------------

	protected final void rangeCheck(final int index) {
		rangeCheck(index, 0, size());
	}

	protected final void rangeCheck(final int index, final int min, final int max) {
		if (index < min || index >= max) {
			throw new IndexOutOfBoundsException("Index: " + index + ", min: " + min + ", max:" + max);
		}
	}

	//-------------------------------------------------

	@Override
	public boolean contains(final Object o) {
		return indexOf(o) > NOT_FOUND_INDEX;
	}

	@Override
	public final E get(final int index) {
		rangeCheck(index);
		return get0(index);
	}

	protected abstract E get0(int index);

	//-------------------------------------------------
	@Override
	public final boolean add(final E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void add(final int index, final E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean addAll(final int index, final Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public final E remove(final int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected final void removeRange(final int fromIndex, final int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final E set(final int index, final E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean addAll(final Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean remove(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean removeAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}
}
