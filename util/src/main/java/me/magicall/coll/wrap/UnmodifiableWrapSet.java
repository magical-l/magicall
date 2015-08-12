package me.magicall.coll.wrap;

import me.magicall.coll.unmodifiable.UnmodifiableSetTemplate;
import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


public class UnmodifiableWrapSet<E> extends UnmodifiableSetTemplate<E>//
		implements Set<E>, Serializable, Unmodifiable, Wrapper<Set<E>> {
	private static final long serialVersionUID = -6517522329389834416L;

	private final Set<E> set;

	public UnmodifiableWrapSet(final Set<E> set) {
		super();
		this.set = set;
	}

	@Override
	public boolean contains(final Object o) {
		return set.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	protected Iterator<E> iterator0() {
		return set.iterator();
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean equals(final Object o) {
		return set.equals(o);
	}

	@Override
	public int hashCode() {
		return set.hashCode();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return set.toArray(a);
	}

	@Override
	public String toString() {
		return set.toString();
	}

	@Override
	public Set<E> unwrap() {
		return this;
	}
}
