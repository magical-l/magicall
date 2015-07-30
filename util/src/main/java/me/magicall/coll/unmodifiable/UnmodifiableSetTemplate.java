package me.magicall.coll.unmodifiable;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import me.magicall.coll.CollFactory.I;
import me.magicall.mark.Unmodifiable;

public abstract class UnmodifiableSetTemplate<E> extends AbstractSet<E> implements Set<E>, Unmodifiable, Serializable {
	private static final long serialVersionUID = 1186546192702020850L;

	protected UnmodifiableSetTemplate() {
		super();
	}

	//-------------------------------------------------
	@Override
	public final Iterator<E> iterator() {
		return I.unmodifiable(iterator0());
	}

	protected abstract Iterator<E> iterator0();

	//-------------------------------------------------
	@Override
	public final boolean add(final E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean addAll(final Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void clear() {
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
