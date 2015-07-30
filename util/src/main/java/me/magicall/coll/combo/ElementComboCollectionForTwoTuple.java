package me.magicall.coll.combo;

import me.magicall.coll.unmodifiable.UnmodifiableCollectionTemplate;
import me.magicall.mark.Unmodifiable;
import me.magicall.util.touple.Tuple;
import me.magicall.util.touple.TwoTuple;

import java.util.Collection;
import java.util.Iterator;

public class ElementComboCollectionForTwoTuple<E1, E2> extends UnmodifiableCollectionTemplate<TwoTuple<E1, E2>>//
		implements Collection<TwoTuple<E1, E2>>, Unmodifiable {
	private static final long serialVersionUID = -7668512723494676608L;

	private final Collection<E1> c1;
	private final Collection<E2> c2;

	public ElementComboCollectionForTwoTuple(final Collection<E1> c1, final Collection<E2> c2) {
		super();
		if (c1.size() != c2.size()) {
			throw new RuntimeException("不一样长");
		}
		this.c1 = c1;
		this.c2 = c2;
	}

	@Override
	protected Iterator<TwoTuple<E1, E2>> iterator0() {
		return new Iterator<TwoTuple<E1, E2>>() {

			Iterator<E1> i1 = c1.iterator();
			Iterator<E2> i2 = c2.iterator();

			@Override
			public boolean hasNext() {
				return i1.hasNext();
			}

			@Override
			public TwoTuple<E1, E2> next() {
				return Tuple.of(i1.next(), i2.next());
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public int size() {
		return c1.size();
	}
}
