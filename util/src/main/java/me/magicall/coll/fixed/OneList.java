package me.magicall.coll.fixed;

import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;
import me.magicall.consts.CommonConst;
import me.magicall.mark.Unmodifiable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

public class OneList<E> extends UnmodifiableListTemplate<E>//
		implements List<E>, Fixed, Unmodifiable, Serializable {

	private static final long serialVersionUID = 6019756482395036186L;
	private static final int SIZE = 1;

	private final E e;

	public OneList(final E e) {
		super();
		this.e = e;
	}

	@Override
	protected E get0(final int index) {
		return e;
	}

	@Override
	public int size() {
		return SIZE;
	}

	@Override
	public int indexOf(final Object o) {
		return o == null ? (e == null ? 0 : CommonConst.NOT_FOUND_INDEX) : o.equals(e) ? 0 : CommonConst.NOT_FOUND_INDEX;
	}

	@Override
	public int lastIndexOf(final Object o) {
		return indexOf(o);
	}

	@Override
	protected List<E> subList0(final int fromIndex, final int toIndex) {
		rangeCheck(fromIndex);
		rangeCheck(toIndex);
		return this;
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return c.isEmpty() || c.size() == size() && contains(c.iterator().next());
	}

	@Override
	public Object[] toArray() {
		return new Object[] { e };
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(final T[] a) {
		if (a == null) {
			throw new NullPointerException();
		}
		final int size = size();
		if (a == null || a.length < size) {
			final T[] r = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
			r[0] = (T) e;
			return r;
		} else {
			a[0] = (T) e;
			return a;
		}
	}
}
