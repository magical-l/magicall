package me.magicall.coll.sorted;

import me.magicall.coll.CollFactory.L;
import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;
import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;

import java.util.List;


public class ReverseList<E> extends UnmodifiableListTemplate<E>//
		implements List<E>, Unmodifiable, Wrapper<List<E>>, Sorted {

	private static final long serialVersionUID = 6241766787522166861L;

	private final List<E> source;

	public ReverseList(final List<E> source) {
		super();
		this.source = source;
	}

	@Override
	protected E get0(final int index) {
		return source.get(size() - index - 1);
	}

	@Override
	public int size() {
		return source.size();
	}

	public static void main(final String... args) {
		final List<Integer> source = L.natureSeq(10);
		final List<Integer> list = new ReverseList<>(source);
		System.out.println("@@@@@@" + list);
		System.out.println("@@@@@@" + list.subList(2, 5));
	}

	public List<E> getSource() {
		return source;
	}

	@Override
	public List<E> unwrap() {
		return source;
	}
}
