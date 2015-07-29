package me.magicall.coll.combo;

import me.magicall.coll.CollFactory.L;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 这个飞机List的实现专注于需要经常将别的collection加入本List.源Collection越大,性能提升越显著.
 * 使用组合方式,所以对addAll(Collection)特别在行.此方法的性能约是ArrayList的30+倍,LinkedList的20~30倍.
 * 其他方面马马虎虎,最重要的遍历性能约是ArrayList的一半,是LinkedList的1/3
 * 
 * @author MaGiCalL
 * @param <E>
 */
public class ComboList<E> extends AbstractList<E> implements List<E> {

	private final List<List<? extends E>> lists;
	private int size;

	public ComboList() {
		lists = new ArrayList<>();
		size = 0;
	}

	@Override
	public boolean add(final E e) {
		return addAll(L.asList(e));
	}

	/**
	 * 这个方法暂时不会实现.只能插入到末尾
	 */
	@Override
	public void add(final int index, final E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(final Collection<? extends E> c) {
		size += c.size();
		return c instanceof List<?> ? lists.add((List<? extends E>) c)//
				: lists.add(new ArrayList<>(c));
	}

	/**
	 * 这个方法暂时不会实现.只能插入到末尾
	 */
	@Override
	public boolean addAll(final int index, final Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(final Object o) {
		for (final Collection<? extends E> e : lists) {
			if (e.contains(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		for (final Collection<? extends E> e : lists) {
			if (e.containsAll(c)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public E get(int index) {
		for (final List<? extends E> e : lists) {
			final int size = e.size();
			if (size > index) {
				return e.get(index);
			} else {
				index -= size;
			}
		}
		throw new NoSuchElementException();
	}

	@Override
	public int indexOf(final Object o) {
		int base = 0;
		for (final List<? extends E> e : lists) {
			final int i = e.indexOf(o);
			if (i < 0) {
				base += e.size();
			} else {
				return base + i;
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		for (final List<? extends E> e : lists) {
			if (!e.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	@Override
	public int lastIndexOf(final Object o) {
		int rt = size();
		for (int i = lists.size() - 1; i >= 0; --i) {
			final List<? extends E> list = lists.get(i);
			final int in = list.lastIndexOf(o);
			if (in < 0) {
				rt -= list.size();
			} else {
				return rt - list.size() + in;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		return new Itr();
	}

	@Override
	public ListIterator<E> listIterator(final int index) {
		return new Itr();
	}

	@Override
	public boolean remove(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(final int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(final int index, final E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(final int fromIndex, final int toIndex) {
		if (fromIndex < 0) {
			throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
		}
		if (toIndex > size()) {
			throw new IndexOutOfBoundsException("toIndex = " + toIndex);
		}
		int f, t;
		if (fromIndex > toIndex) {
			f = toIndex;
			t = fromIndex;
		} else {
			f = fromIndex;
			t = toIndex;
		}
		final ComboList<E> rt = new ComboList<>();
		boolean findingFrom = true;
		for (final List<? extends E> list : lists) {
			final int size = list.size();
			if (findingFrom) {
				if (size > f) {
					findingFrom = false;
					if (size > t) {
						rt.addAll(list.subList(f, t));
						return rt;
					} else {
						rt.addAll(list.subList(f, list.size()));
					}
				} else {
					f -= size;
					t -= size;
				}
			} else {
				if (size > t) {
					rt.addAll(list.subList(0, t));
					return rt;
				} else {
					rt.addAll(list);
					t -= size;
				}
			}
		}
		throw new IndexOutOfBoundsException("toIndex = " + toIndex);
	}

	private class Itr implements ListIterator<E> {

		private final ListIterator<List<? extends E>> all;
		private ListIterator<? extends E> cur;
		private int curIndex;

		private Itr() {
			all = lists.listIterator();
			cur = all.next().listIterator();//
			curIndex = 0;
		}

		@Override
		public void add(final E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNext() {
			if (cur.hasNext()) {
				return true;
			}
			if (!all.hasNext()) {
				return false;
			}
			final boolean b = all.next().iterator().hasNext();
			all.previous();
			return b;
		}

		@Override
		public boolean hasPrevious() {
			if (cur.hasPrevious()) {
				return true;
			}
			if (all.hasPrevious()) {
				final List<? extends E> previous = all.previous();
				return previous.listIterator(previous.size() - 1).hasPrevious();
			}
			return false;
		}

		@Override
		public E next() {
			if (cur.hasNext()) {
				return cur.next();
			} else {
				if (all.hasNext()) {
					cur = all.next().listIterator();
					return cur.next();
				} else {
					throw new NoSuchElementException();
				}
			}
		}

		@Override
		public int nextIndex() {
			return curIndex++;
		}

		@Override
		public E previous() {
			if (cur.hasPrevious()) {
				return cur.previous();
			}
			if (all.hasPrevious()) {
				final List<? extends E> previous = all.previous();
				cur = previous.listIterator(previous.size() - 1);
				return cur.previous();
			}
			throw new NoSuchElementException();
		}

		@Override
		public int previousIndex() {
			return curIndex--;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(final E e) {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(final String... args) {
		final ArrayList<Integer> source = new ArrayList<>();

		final ArrayList<Integer> l1 = new ArrayList<>();
		final LinkedList<Integer> l3 = new LinkedList<>();
		final ComboList<Integer> l2 = new ComboList<>();
		for (int i = 0; i < 100; i++) {
			source.add(i);
		}

		add(l1, source);//30+

		add(l3, source);//20

		add(l2, source);//1

		System.out.println(l2.equals(l1) && l2.equals(l3));

		iter(l1);//1.5

		iter(l3);//1

		iter(l2);//3
	}

	private static <E> void add(final List<E> list, final List<E> source) {
		final long b;
		final long e;
		b = System.nanoTime();
		for (int i = 0; i < 100; i++) {
			list.addAll(source);
		}
		e = System.nanoTime();
		System.out.println(e - b);
	}

	private static <E> void iter(final List<E> list) {
		final long b;
		final long e;
		b = System.nanoTime();
		for (final Iterator<E> i = list.iterator(); i.hasNext(); i.next()) {
		}
		e = System.nanoTime();
		System.out.println("@@@@@@" + (e - b));
	}

//	/**
//	 * 这个可以用二分查找法.有空再说
//	 * 
//	 * @param index
//	 * @return
//	 */
//	private List<? extends E> findList(int index) {
//		for (final List<? extends E> e : lists) {
//			if (e.size() > index) {
//				return e;
//			} else {
//				index -= e.size();
//			}
//		}
//		return null;
//	}
}
