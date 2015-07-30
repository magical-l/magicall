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

public class ThreeList<E> extends UnmodifiableListTemplate<E>//
		implements List<E>, Fixed, Unmodifiable, Serializable {

	private static final long serialVersionUID = 6019756482395036187L;
	private static final int SIZE = 3;

	private final E e1;
	private final E e2;
	private final E e3;

	public ThreeList(final E e1, final E e2, final E e3) {
		super();
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
	}

	@Override
	protected E get0(final int index) {
		switch (index) {
		case 0:
			return e1;
		case 1:
			return e2;
		case 2:
			return e3;
		}
		//不会到达
		return null;
	}

	@Override
	public int indexOf(final Object o) {
		return ObjKit.INSTANCE.deepEquals(o, e1) ? 0//
				: ObjKit.INSTANCE.deepEquals(o, e2) ? 1//
						: ObjKit.INSTANCE.deepEquals(o, e3) ? 2//
								: CommonConst.NOT_FOUND_INDEX;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int lastIndexOf(final Object o) {
		return ObjKit.INSTANCE.deepEquals(o, e3) ? 2//
				: ObjKit.INSTANCE.deepEquals(o, e2) ? 1//
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
		if (fromIndex == toIndex) {
			return Collections.emptyList();
		}
		if (fromIndex == 0) {
			if (toIndex == 1) {
				return L.asList(e1);
			} else if (toIndex == 2) {
				return L.asList(e1, e2);
			} else {
				return this;
			}
		} else if (fromIndex == 1) {
			return toIndex == 2 ? L.asList(e2) : L.asList(e2, e3);
		} else {
			return L.asList(e3);
		}
	}

	@Override
	public Object[] toArray() {
		return new Object[] { e1, e2, e3 };
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
			r[2] = (T) e3;
			return r;
		} else {
			a[0] = (T) e1;
			a[1] = (T) e2;
			a[2] = (T) e3;
			return a;
		}
	}

}
