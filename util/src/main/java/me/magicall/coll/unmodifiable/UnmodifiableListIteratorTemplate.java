package me.magicall.coll.unmodifiable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;

import me.magicall.mark.Unmodifiable;


public abstract class UnmodifiableListIteratorTemplate<E> extends UnmodifiableIteratorTemplate<E>//
		implements ListIterator<E>, Iterator<E>, Serializable, Unmodifiable {
	private static final long serialVersionUID = -3649880815646687400L;

	public UnmodifiableListIteratorTemplate() {
		super();
	}

	//-------------------------------------------------
	@Override
	public final void set(final E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void add(final E e) {
		throw new UnsupportedOperationException();
	}
}
