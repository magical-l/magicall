package me.magicall.coll.transformed;

import java.io.Serializable;
import java.util.Iterator;

import me.magicall.coll.ElementTransformer;
import me.magicall.mark.Wrapper;


public class TransformedIterator<F, T> implements Iterator<T>, Wrapper, Serializable {

	private static final long serialVersionUID = -8417815490370366682L;

	private final Iterator<F> source;
	private final ElementTransformer<F, T> tf;
	private int index;

	public TransformedIterator(final Iterator<F> source, final ElementTransformer<F, T> tf, final int index) {
		super();
		this.source = source;
		this.tf = tf;
		this.index = index;
	}

	public TransformedIterator(final Iterator<F> source, final ElementTransformer<F, T> tf) {
		this(source, tf, 0);
	}

	@Override
	public boolean hasNext() {
		return source.hasNext();
	}

	@Override
	public T next() {
		return tf.transform(index++, source.next());
	}

	@Override
	public void remove() {
		source.remove();
	}

}
