package me.magicall.coll.wrap;

import java.io.Serializable;
import java.util.Iterator;

import me.magicall.coll.unmodifiable.UnmodifiableIteratorTemplate;
import me.magicall.tagInterfaces.Unmodifiable;
import me.magicall.tagInterfaces.Wrapper;

public class UnmodifiableWrapIterator<E> extends UnmodifiableIteratorTemplate<E>//
		implements Iterator<E>, Unmodifiable, Wrapper, Serializable {

	private static final long serialVersionUID = -2521809833880889447L;

	private final Iterator<E> i;

	public UnmodifiableWrapIterator(final Iterator<E> i) {
		super();
		this.i = i;
	}

	@Override
	public boolean hasNext() {
		return i.hasNext();
	}

	@Override
	public E next() {
		return i.next();
	}

	@Override
	public String toString() {
		return i.toString();
	}
}
