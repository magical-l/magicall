package me.magicall.coll.bridge;

import me.magicall.coll.sorted.Sorted;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;


public class ListSet<E> extends AbstractList<E>//
		implements List<E>, Set<E>, SortedSet<E>, Serializable, Sorted {
	private static final long serialVersionUID = -7260955667013286199L;

	private final List<E> list;

	//------------------------------

	/**
	 * 这个list需要保证没有重复元素
	 * @param list
	 */
	public ListSet(final List<E> list) {
		super();
		this.list = list;
	}

	public ListSet() {
		this(new ArrayList<>());
	}

	//-------------------------------collection

	public String toString() {
		return list.toString();
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean add(final E e) {
		return !list.contains(e) && list.add(e);
	}

	@Override
	public boolean addAll(final Collection<? extends E> c) {
		return super.addAll(c);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(final Object o) {
		return list.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return list.containsAll(c);
	}

	public boolean equals(final Object o) {
		return list.equals(o);
	}

	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean remove(final Object o) {
		return list.remove(o);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return list.toArray(a);
	}

	//---------------------------list

	@Override
	public void add(final int index, final E element) {
		super.add(index, element);
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends E> c) {
		return super.addAll(index, c);
	}

	@Override
	public E get(final int index) {
		return list.get(index);
	}

	@Override
	public int indexOf(final Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(final Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return super.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(final int index) {
		return super.listIterator(index);
	}

	@Override
	public E remove(final int index) {
		return list.remove(index);
	}

	@Override
	public E set(final int index, final E element) {
		return list.set(index, element);
	}

	@Override
	public List<E> subList(final int fromIndex, final int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	//-----------------------------sorted set

	@Override
	public E first() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return list.get(0);
	}

	@Override
	public SortedSet<E> headSet(final E toElement) {
		final int index = i(toElement);
		if (index < 0) {
			throw new IllegalArgumentException();
		}
		return new ListSet<>(list.subList(0, index));
	}

	@Override
	public E last() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return list.get(list.size() - 1);
	}

	@Override
	public SortedSet<E> subSet(final E fromElement, final E toElement) {
		final int from = i(fromElement);
		if (from < 0) {
			throw new IllegalArgumentException();
		}
		final int to = i(toElement);
		if (to < 0) {
			throw new IllegalArgumentException();
		}
		return new ListSet<>(list.subList(from, to));
	}

	@Override
	public SortedSet<E> tailSet(final E fromElement) {
		final int from = i(fromElement);
		if (from < 0) {
			throw new IllegalArgumentException();
		}
		return new ListSet<>(list.subList(from, list.size()));
	}

	@Override
	public Comparator<? super E> comparator() {
		return null;
	}

	private int i(final E element) {
		return list.indexOf(element);
	}

}
