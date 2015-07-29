package me.magicall.coll.combo;

import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;

import java.util.List;


public class AppendList<E> extends UnmodifiableListTemplate<E> {

	private static final long serialVersionUID = -6994525729391557799L;

	private final List<E> list;
	private final E first;
	private E second;
	private E third;

	private final int size;

	public AppendList(final List<E> list, final E first) {
		super();
		this.list = list;
		this.first = first;
		size = list.size() + 1;
	}

	public AppendList(final List<E> list, final E first, final E second) {
		super();
		this.list = list;
		this.first = first;
		this.second = second;
		size = list.size() + 2;
	}

	public AppendList(final List<E> list, final E first, final E second, final E third) {
		super();
		this.list = list;
		this.first = first;
		this.second = second;
		this.third = third;
		size = list.size() + 3;
	}

	@Override
	protected E get0(final int index) {
		rangeCheck(index);
		final int i = index - list.size();
		if (i < 0) {
			return list.get(index);
		} else if (i == 0) {
			return first;
		} else if (i == 1) {
			return second;
		} else {
			return third;
		}
	}

	@Override
	public int size() {
		return size;
	}

}
