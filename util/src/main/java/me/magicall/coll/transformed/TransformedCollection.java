package me.magicall.coll.transformed;

import me.magicall.coll.ElementNotNull;
import me.magicall.coll.ElementTransformer;
import me.magicall.coll.unmodifiable.UnmodifiableCollectionTemplate;
import me.magicall.coll.unmodifiable.UnmodifiableIteratorTemplate;
import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;
import me.magicall.util.kit.Kits;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class TransformedCollection<F, T> extends UnmodifiableCollectionTemplate<T>//
		implements Collection<T>, Unmodifiable, ElementNotNull, Wrapper<Collection<T>>, Serializable {
	private static final long serialVersionUID = 2825834495087793417L;

	protected final Collection<? extends F> collection;
	protected final ElementTransformer<? super F, ? extends T> transformer;

	public TransformedCollection(final Collection<? extends F> collection, final ElementTransformer<? super F, ? extends T> transformer) {
		super();
		this.collection = Kits.COLL.checkToEmptyValue(collection);
		this.transformer = transformer;
	}

	protected T transform(final int index, final F from) {
		return transformer.transform(index, from);
	}

	@Override
	protected Iterator<T> iterator0() {
		return new UnmodifiableIteratorTemplate<T>() {
			private static final long serialVersionUID = 8120376368162093901L;

			int index = 0;
			Iterator<? extends F> iter = collection.iterator();

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public T next() {
				return transform(index++, iter.next());
			}
		};
	}

	@Override
	public int size() {
		return collection.size();
	}

	@Override
	public Collection<T> unwrap() {
		return this;
	}
}
