package me.magicall.coll.fixed;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

import me.magicall.coll.CollFactory.L;
import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;
import me.magicall.consts.CommonConst;
import me.magicall.mark.Unmodifiable;
import me.magicall.util.kit.ObjKit;

public class TwoList<E> extends UnmodifiableListTemplate<E>//
		implements List<E>, Fixed, Unmodifiable, Serializable {

	private static final long serialVersionUID = 6019756482395036187L;
	private static final int SIZE = 2;

	private final E e1;
	private final E e2;

	public TwoList(final E e1, final E e2) {
		super();
		this.e1 = e1;
		this.e2 = e2;
	}

	@Override
	protected E get0(final int index) {
		return index == 0 ? e1 : e2;
	}

	@Override
	public int indexOf(final Object o) {
		return ObjKit.INSTANCE.deepEquals(o, e1) ? 0//
				: ObjKit.INSTANCE.deepEquals(o, e2) ? 1//
						: CommonConst.NOT_FOUND_INDEX;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int lastIndexOf(final Object o) {
		return ObjKit.INSTANCE.deepEquals(o, e2) ? 1//
				: ObjKit.INSTANCE.deepEquals(o, e1) ? 0//
						: CommonConst.NOT_FOUND_INDEX;
	}

	@Override
	public int size() {
		return SIZE;
	}

	@Override
	protected List<E> subList0(final int fromIndex, final int toIndex) {
		rangeCheck(fromIndex, 0, toIndex);
		rangeCheck(toIndex, fromIndex, size());
		if (fromIndex == 1) {
			return L.asList(e2);
		} else if (fromIndex == 0) {
			return Collections.emptyList();
		} else {//fromIndex=0
			return toIndex == 1 ? L.asList(e1) : this;
		}
	}

	@Override
	public Object[] toArray() {
		return new Object[] { e1, e2 };
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
			r[0] = (T) e1;
			r[1] = (T) e2;
			return r;
		} else {
			a[0] = (T) e1;
			a[1] = (T) e2;
			return a;
		}
	}
}
