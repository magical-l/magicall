package me.magicall.coll.unmodifiable;

import java.io.Serializable;
import java.util.Iterator;

import me.magicall.mark.Unmodifiable;

public abstract class UnmodifiableIteratorTemplate<E> implements Iterator<E>, Unmodifiable, Serializable {
	private static final long serialVersionUID = 6484039531495107645L;

	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
}
