package me.magicall.coll.transformed;

import java.util.Set;

import me.magicall.coll.ElementTransformer;


public class TransformedSet<F, T> extends TransformedCollection<F, T> implements Set<T> {
	private static final long serialVersionUID = -7222478374638494264L;

	public TransformedSet(final Set<? extends F> set, final ElementTransformer<? super F,? extends  T> transformer) {
		super(set, transformer);
	}

}
