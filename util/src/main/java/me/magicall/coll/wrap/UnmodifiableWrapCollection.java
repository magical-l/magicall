package me.magicall.coll.wrap;

import me.magicall.coll.unmodifiable.UnmodifiableCollectionTemplate;
import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class UnmodifiableWrapCollection<E> extends UnmodifiableCollectionTemplate<E>//
		implements Collection<E>, Unmodifiable, Wrapper<Collection<E>>, Serializable {
	private static final long serialVersionUID = 5693290480930330250L;

	private final Collection<E> coll;

	public UnmodifiableWrapCollection(final Collection<E> coll) {
		super();
		this.coll = coll;
	}

	@Override
	public boolean contains(final Object o) {
		return coll.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return coll.containsAll(c);
	}

	@Override
	public boolean equals(final Object o) {
		return coll.equals(o);
	}

	@Override
	public int hashCode() {
		return coll.hashCode();
	}

	@Override
	protected Iterator<E> iterator0() {
		return coll.iterator();
	}

	@Override
	public int size() {
		return coll.size();
	}

	@Override
	public boolean isEmpty() {
		return coll.isEmpty();
	}

	@Override
	public Object[] toArray() {
		return coll.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return coll.toArray(a);
	}

	@Override
	public String toString() {
		return coll.toString();
	}

	@Override
	public Collection<E> unwrap() {
		return this;
	}
}
