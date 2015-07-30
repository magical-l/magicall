package me.magicall.coll.combo;

import me.magicall.coll.unmodifiable.UnmodifiableCollectionTemplate;
import me.magicall.mark.Unmodifiable;
import me.magicall.util.touple.ThreeTuple;
import me.magicall.util.touple.Tuple;

import java.util.Collection;
import java.util.Iterator;

public class ElementComboCollectionForThreeTuple<E1, E2, E3> //
		extends UnmodifiableCollectionTemplate<ThreeTuple<E1, E2, E3>>//
		implements Collection<ThreeTuple<E1, E2, E3>>, Unmodifiable {
	private static final long serialVersionUID = 2727867044420026760L;

	private final Collection<E1> c1;
	private final Collection<E2> c2;
	private final Collection<E3> c3;

	public ElementComboCollectionForThreeTuple(final Collection<E1> c1, final Collection<E2> c2, final Collection<E3> c3) {
		super();
		if (c1.size() != c2.size() || c1.size() != c3.size()) {
			throw new RuntimeException("不一样长");
		}
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}

	@Override
	protected Iterator<ThreeTuple<E1, E2, E3>> iterator0() {
		return new Iterator<ThreeTuple<E1, E2, E3>>() {

			Iterator<E1> i1 = c1.iterator();
			Iterator<E2> i2 = c2.iterator();
			Iterator<E3> i3 = c3.iterator();

			@Override
			public boolean hasNext() {
				return i1.hasNext();
			}

			@Override
			public ThreeTuple<E1, E2, E3> next() {
				return Tuple.of(i1.next(), i2.next(), i3.next());
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
