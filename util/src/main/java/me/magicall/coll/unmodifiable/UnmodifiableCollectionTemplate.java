package me.magicall.coll.unmodifiable;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import me.magicall.coll.CollFactory.I;
import me.magicall.mark.Unmodifiable;


public abstract class UnmodifiableCollectionTemplate<E> extends AbstractCollection<E>//
		implements Collection<E>, Serializable, Unmodifiable {
	private static final long serialVersionUID = 4025111063775614660L;

	public UnmodifiableCollectionTemplate() {
		super();
	}

	//-----------------------------------
	@Override
	public final Iterator<E> iterator() {
		return I.unmodifiable(iterator0());
	}

	protected abstract Iterator<E> iterator0();

	//------------------------------------
	@Override
	public final void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		for (final Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return size() < 1;
	}

	//-------------------------------------
	@Override
	public final boolean add(final E e) {
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
