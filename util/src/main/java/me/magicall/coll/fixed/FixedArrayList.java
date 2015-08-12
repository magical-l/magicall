package me.magicall.coll.fixed;

import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;
import me.magicall.consts.CommonConst;
import me.magicall.mark.Unmodifiable;
import me.magicall.util.ArrayUtil;
import me.magicall.util.kit.Kits;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class FixedArrayList<E> extends UnmodifiableListTemplate<E>//
		implements List<E>, RandomAccess, Unmodifiable, Fixed {

	private static final long serialVersionUID = -1735597066322861930L;

	private final E[] a;

	@SafeVarargs
	public FixedArrayList(final E... array) {
		super();
		a = array;
	}

	@Override
	public int size() {
		return a == null ? 0 : a.length;
	}

	@Override
	public Object[] toArray() {
		return a == null ? Kits.OBJ.emptyArray() : a.clone();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(final T[] arr) {
		if (a == null) {
			return arr;
		}
		final int size = size();
		if (arr.length < size) {
			return Arrays.copyOf(a, size, (Class<? extends T[]>) arr.getClass());
		}
		System.arraycopy(a, 0, arr, 0, size);
		if (arr.length > size) {
			arr[size] = null;
		}
		return arr;
	}

	@Override
	protected E get0(final int index) {
		if (a == null) {
			throw new NoSuchElementException();
		}
		return a[index];
	}

	@Override
	public int indexOf(final Object o) {
		return a == null ? CommonConst.NOT_FOUND_INDEX : ArrayUtil.indexOf(o, a);
	}

}
