package me.magicall.coll.bridge;

import me.magicall.coll.unmodifiable.UnmodifiableIteratorTemplate;

import java.util.Enumeration;

public class EnumerationIterator<E> extends UnmodifiableIteratorTemplate<E> {
	private static final long serialVersionUID = -8534778639922297883L;

	private final Enumeration<E> enumeration;

	public EnumerationIterator(final Enumeration<E> enumeration) {
		super();
		this.enumeration = enumeration;
	}

	@Override
	public boolean hasNext() {
		return enumeration.hasMoreElements();
	}

	@Override
	public E next() {
		return enumeration.nextElement();
	}
}
