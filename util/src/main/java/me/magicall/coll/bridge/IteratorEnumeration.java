package me.magicall.coll.bridge;

import me.magicall.coll.ElementTransformerUtil.SerializableElementTransformer;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author zengyalin
 */
public class IteratorEnumeration<E> implements Enumeration<E> {

	private final Iterator<E> iterator;

	public IteratorEnumeration(final Iterator<E> iterator) {
		this.iterator = iterator;
	}

	public IteratorEnumeration(final Iterable<E> iterable) {
		this(iterable.iterator());
	}

	@Override
	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	@Override
	public E nextElement() {
		return iterator.next();
	}

	public static class IteratorToEnumerationTransformer<E> implements SerializableElementTransformer<Iterator<E>, Enumeration<E>> {

		private static final long serialVersionUID = -7363714386278634903L;

		@Override
		public Enumeration<E> transform(final int index, final Iterator<E> element) {
			return new IteratorEnumeration<>(element);
		}
	}
}
