package me.magicall.coll.wrap;

import me.magicall.coll.sorted.Sorted;
import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;
import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class UnmodifiableWrapList<E> extends UnmodifiableListTemplate<E>//
		implements List<E>, Wrapper<List<E>>, Serializable, Unmodifiable, Sorted {

	private static final long serialVersionUID = -4222922423845326955L;

	private final List<E> list;

	public UnmodifiableWrapList(final List<E> list) {
		super();
		this.list = list;
	}

	@Override
	protected E get0(final int index) {
		return list.get(index);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	protected Iterator<E> iterator0() {
		return list.iterator();
	}

	@Override
	protected ListIterator<E> listIterator0(final int index) {
		return list.listIterator(index);
	}

	@Override
	public boolean contains(final Object o) {
		return list.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean equals(final Object o) {
		return list.equals(o);
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public int indexOf(final Object o) {
		return list.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public int lastIndexOf(final Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	protected List<E> subList0(final int fromIndex, final int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return list.toArray(a);
	}

	@Override
	public String toString() {
		return list.toString();
	}

	@Override
	public List<E> unwrap() {
		return this;
	}
}
