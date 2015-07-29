package me.magicall.coll.bridge;

import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;
import me.magicall.tagInterfaces.Unmodifiable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class CollectionList<E> extends UnmodifiableListTemplate<E> //
		implements Serializable, List<E>, Set<E>, Unmodifiable {

	private static final long serialVersionUID = 6757154213056307387L;

	private final Collection<E> source;

	public CollectionList(final Collection<E> source) {
		super();
		this.source = source;
	}

	@Override
	protected E get0(final int index) {
		int i = 0;
		for (final E e : source) {
			if (i++ == index) {
				return e;
			}
		}
		return null;
	}

	@Override
	protected Iterator<E> iterator0() {
		return source.iterator();
	}

	@Override
	public int size() {
		return source.size();
	}

	@Override
	public Object[] toArray() {
		return source.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return source.toArray(a);
	}

	@Override
	public boolean contains(final Object o) {
		return source.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return source.containsAll(c);
	}

	@Override
	public boolean equals(final Object o) {
		return source.equals(o);
	}

	@Override
	public int hashCode() {
		return source.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return source.isEmpty();
	}

	@Override
	public String toString() {
		return source.toString();
	}
}
