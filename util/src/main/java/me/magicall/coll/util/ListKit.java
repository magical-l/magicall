/**
 * 
 */
package me.magicall.coll.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.Set;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.L;
import me.magicall.coll.ElementTransformer;
import me.magicall.coll.bridge.CollectionList;
import me.magicall.coll.empty.EmptyColl;
import me.magicall.coll.sorted.ReverseList;
import me.magicall.coll.transformed.TransformedList;
import me.magicall.coll.transformed.TransformedList.RandomAccessTransformedList;
import me.magicall.coll.wrap.UnmodifiableWrapList;
import me.magicall.tagInterfaces.Unmodifiable;
import me.magicall.util.comparator.ComparatorAndEquivalentUtil;
import me.magicall.util.kit.Kits;

public final class ListKit extends AbsCollectionKit<List<?>> {

	private static final Class<?> MAIN_CLASS = List.class;

	/**
	 * <code>java.util.Collections.UnmodifiableList</code>的Class对象，由于该类不可见，只能通过这个方法获得它的Class对象
	 */
	private static final Class<?> JDK_COLLECTIONS_UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(Collections.emptyList()).getClass();

	//----------------------------------------------
	public static final ListKit INSTANCE = new ListKit();

	//------------------------------------------------
	@SuppressWarnings("unchecked")
	private ListKit() {
		super((Class<List<?>>) MAIN_CLASS, EmptyColl.INSTANCE);
	}

	//------------------------------------------------
	//这里覆盖父类方法,是为了返回值能带上泛型.这个方法不好
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <E, E1> List<E> emptyValue() {
		return (List) EmptyColl.INSTANCE;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> forceSuit(final List<?> source) {
		return (List<E>) source;
	}

	public <E> E last(final List<E> source) {
		return isEmpty(source) ? null : source.get(source.size() - 1);
	}

	//------------------------------------------------
	public <E> E randomOne(final List<E> list) {
		if (isEmpty(list)) {
			return null;
		}
		final int size = list.size();
		return list.get(size == 1 ? 0 : RANDOM.nextInt(size));
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> suit(final List<? extends T> from) {
		return (List<T>) from;
	}

	public <F, T> List<T> transform(final List<? extends F> source, final ElementTransformer<? super F, ? extends T> transformer) {
		return source instanceof RandomAccess ? //
			   new RandomAccessTransformedList<>(source, transformer)//
				: new TransformedList<>(source, transformer);
	}

	/**
	 * 返回一个迭代顺序相反的List
	 * 
	 * @param <T>
	 * @param source
	 * @return
	 */
	public <T> List<T> reverse(final List<T> source) {
		return source instanceof ReverseList<?> ? ((ReverseList<T>) source).getSource() : new ReverseList<>(source);
	}

	public <T> List<T> unmodifiable(final List<T> source) {
		if (source instanceof Unmodifiable) {
			return source;
		}
		if (source == Collections.EMPTY_LIST) {
			return source;
		}
		if (source.getClass() == JDK_COLLECTIONS_UNMODIFIABLE_LIST_CLASS) {
			return source;
		}
		return new UnmodifiableWrapList<>(source);
	}

	/**
	 * 返回后面一部分作为一个list
	 * 
	 * @param <T>
	 * @param source
	 * @param from
	 * @return
	 */
	public <T> List<T> tail(final List<T> source, final int from) {
		return subList(source, from, Integer.MAX_VALUE);
	}

	/**
	 * 返回前面的一部分作为一个list
	 * 
	 * @param <T>
	 * @param source
	 * @param to
	 * @return
	 */
	public <T> List<T> head(final List<T> source, final int to) {
		return subList(source, 0, to);
	}

	/**
	 * 加强版subList.对于from>to的情况,会反转两个参数的值.如下情况会返回空列表而非抛异常或者null:
	 * 1，source是"空的";
	 * 2，from==to;
	 * 3，from,to都>size;
	 * 4，from,to都<0;
	 * 5，一切内部异常
	 * 
	 * @param <T>
	 * @param source
	 *            源
	 * @param from
	 *            起始下标（包含）
	 * @param to
	 *            终止下标（不包含）
	 * @return 你要的东西！
	 */
	public <T> List<T> subList(final List<T> source, final int from, final int to) {
		if (isEmpty(source)) {
			return emptyValue();
		}
		try {
			if (from == to) {
				return emptyValue();
			}

			int f, t;
			if (from < to) {
				f = from;
				t = to;
			} else {
				f = to;
				t = from;
			}
			//此时f必<=t
			final int size = source.size();
			if (f < 0) {
				if (t > size) {//源list是所需的子集
					return source;
				} else if (t < 0) {
					return emptyValue();
				} else {
					f = 0;
				}
			} else if (f > size) {//
				return emptyValue();
			} else if (t > size) {
				t = size;
			}
			return source.subList(f, t);
		} catch (final Throwable e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public boolean isUnmodifiable(final List<?> source) {
		return source instanceof Unmodifiable || source == Collections.EMPTY_LIST || source.getClass() == JDK_COLLECTIONS_UNMODIFIABLE_LIST_CLASS;
	}

	public <T> List<T> toList(final Collection<T> source) {
		return source instanceof List<?> ? (List<T>) source : new CollectionList<>(source);
	}

	public <T> List<T> shuffle(final List<T> source) {
		final List<T> rt = isUnmodifiable(source) ? new ArrayList<>(source) : source;
		Collections.shuffle(rt, RANDOM);
		return rt;
	}

	public <T> List<T> shuffle(final Collection<T> source) {
		return shuffle(toList(source));
	}

	public <T> List<T> sort(final Collection<T> source) {
		return sort(source, null);
	}

	public <T> List<T> sort(final Collection<T> source, final Comparator<? super T> comparator) {
		List<T> rt = toList(source);
		if (Kits.COLL.isUnmodifiable(source)) {
			rt = new ArrayList<>(source);
		}
		Collections.sort(rt, comparator);
		return rt;
	}

	public <E> E maxElement(final List<E> source, final Comparator<? super E> comparator) {
		if (isEmpty(source)) {
			return null;
		}
		final Iterator<E> i = source.iterator();
		E rt = i.next();
		while (i.hasNext()) {
			final E e = i.next();
			if (comparator.compare(rt, e) < 0) {
				rt = e;
			}
		}
		return rt;
	}

	public <E> E minElement(final List<E> source, final Comparator<? super E> comparator) {
		if (isEmpty(source)) {
			return null;
		}
		final Iterator<E> i = source.iterator();
		E rt = i.next();
		while (i.hasNext()) {
			final E e = i.next();
			if (comparator.compare(rt, e) > 0) {
				rt = e;
			}
		}
		return rt;
	}

	@SuppressWarnings("unchecked")
	public <E> E maxElement(final List<E> source) {
		@SuppressWarnings("rawtypes")
		final Comparator comparator = ComparatorAndEquivalentUtil.<Comparable> comparableComparator();
		return (E) maxElement(source, comparator);
	}

	@SuppressWarnings("unchecked")
	public <E> E minElement(final List<E> source) {
		@SuppressWarnings("rawtypes")
		final Comparator comparator = ComparatorAndEquivalentUtil.<Comparable> comparableComparator();
		return (E) minElement(source, comparator);
	}

	public <T> List<T> fromSet(final Set<T> source) {
		return new CollectionList<>(source);
	}

	public List<Integer> toIndexList(final List<?> source) {
		return L.seq(0, source.size());
	}

	/**
	 * 从一个list里随机选取一段区间的简单实现.
	 * 注:事实上每个元素能被选出的概率不一样大.所以仅适用于不严格要求随机性的场景
	 * 
	 * @param <E>
	 * @param source
	 * @param size
	 * @return
	 */
	public <E> List<E> randomSection(final List<E> source, final int size) {
		if (isEmpty(source)) {
			return emptyValue();
		}
		if (source.size() <= size) {
			return source;
		}
		final int maxFromIndex = RANDOM.nextInt(source.size() - size);
		return subList(source, maxFromIndex, maxFromIndex + size);
	}

	/**
	 * 从一个list里随机取出size个元素的简单实现.
	 * 注:事实上每个元素能被选出的概率不一样大.所以仅适用于不严格要求随机性的场景
	 * 
	 * @param <E>
	 * @param source
	 * @param size
	 * @return
	 */
	public <E> List<E> randomElements(final List<E> source, final int size) {
		if (isEmpty(source)) {
			return emptyValue();
		}
		final int sourceSize = source.size();
		if (sourceSize <= size) {
			return source;
		}
		final List<E> rt = new ArrayList<>(size);
		int remainSourceSize = sourceSize;
		int remainNeedSize = size;
		int nextFromIndex = 0;
		while (rt.size() < size) {
			final int index = nextFromIndex + RANDOM.nextInt(remainSourceSize / remainNeedSize);
			rt.add(source.get(index));
			remainNeedSize--;
			nextFromIndex = index + 1;
			remainSourceSize = sourceSize - nextFromIndex;
		}
		return rt;
	}

//	/**
//	 * 从source中抽取指定的一个片段,生成一个ResultView
//	 * 
//	 * @param <E>
//	 * @param source
//	 * @param c
//	 * @param curpage
//	 * @param pagesize
//	 * @return
//	 */
//	public <E> ResultView<E> sub(final Collection<E> source, final Comparator<E> c, final int curpage, final int pagesize) {
//		if (Utils.COLL.isEmpty(source)) {
//			return ResultView.emptyResultView();
//		}
//		List<E> list;
//		if (c == null) {
//			list = toList(source);
//		} else {
//			list = new ArrayList<E>(source);
//			Collections.sort(list, c);
//		}
//		final int offset = curpage * pagesize;
//		final ResultView<E> rt = new ResultView<E>();
//		rt.setList(subList(list, offset, offset + pagesize));
//		rt.setTheNumbers(list.size(), offset, pagesize);
//		return rt;
//	}

	//=======================================================
	private static final long serialVersionUID = 7338971757402227665L;

	private Object readResolve() {
		return INSTANCE;
	}
}