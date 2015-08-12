package me.magicall.coll.numseq;

import me.magicall.coll.ElementNotNull;
import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;
import me.magicall.consts.CommonConst;
import me.magicall.mark.Unmodifiable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/**
 * <pre>
 * 本list实现一个有序的,均匀的,不可变的数列(int),使用开始点,步长和数列长度来确定本数列.
 * new SeqList(0,1,10)等价于如下这种写得烦死了的做法,但比它写法更容易,性能(时间开销和空间开销)更好(理论上如此.没检验过):
 * List<Integer> list=new ArrayList<Integer>(10);
 * for(int i=0;i<10;i++){
 * 	list.add(i);
 * }
 * 
 * !开始点和步长支持负数或0
 * !除非步长为0,否则没有重复元素
 * !不可变对象
 * !实现了序列化接口,随机存取接口(RandomAccess)
 * </pre>
 * 
 * @author MaGiCalL
 */
public class LongSeqList extends UnmodifiableListTemplate<Long>//
		implements Unmodifiable, Serializable, List<Long>, RandomAccess, ElementNotNull {

	private static final long serialVersionUID = 1030236818265112075L;

	private final long from;
	private final long step;
	private final int size;
	private final long end;

	public LongSeqList(final long from, final long step, final int size) {
		super();
		if (size < 0) {
			throw new IllegalArgumentException("size < 0,it's " + size);
		}
		if (step <= 0) {
			throw new IllegalArgumentException("step <= 0,it's " + step);
		}
		this.from = from;
		this.step = step;
		this.size = size;
		end = from + step * (size - 1);
		if (step > 0 && end < from || step < 0 && end > from) {
			throw new IllegalArgumentException("overflow,check the from,step,size");
		}
	}

	public LongSeqList(final long from, final int size) {
		this(from, 1, size);
	}

	@Override
	protected Long get0(final int index) {
		return from + index * step;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(final Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof Integer) {
			final int i = (Integer) o;
			return from <= i && i <= end && (step == 1 || (i - from) % step == 0);
		}
		return false;
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		for (final Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int indexOf(final Object o) {
		if (o instanceof Number) {
			final long i = ((Number) o).longValue();
			final long sub = i - from;
			if (step == 1) {
				return (int) sub;
			}
			final long r = sub / step;
			if (r * step == sub) {
				return (int) r;
			}
		}
		return CommonConst.NOT_FOUND_INDEX;
	}

	@Override
	public boolean isEmpty() {
		return size < 1;
	}

	@Override
	public int lastIndexOf(final Object o) {
		return indexOf(o);
	}

	@Override
	protected List<Long> subList0(final int fromIndex, final int toIndex) {
		return new LongSeqList(get(fromIndex), step, toIndex - fromIndex);
	}

	@Override
	public Object[] toArray() {
		final Long[] is = new Long[size];
		return toArray0(is);
	}

	private Long[] toArray0(final Long... is) {
		for (int i = 0; i < size; ++i) {
			is[i] = from + step * i;
		}
		return is;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(final T[] a) {
		if (a instanceof Long[]) {
			return (T[]) (a.length >= size ? toArray0((Long[]) a) : toArray());
		}
		throw new ClassCastException();
	}

	@Override
	public String toString() {
		if (size == 0) {
			return "[]";
		}
		final StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < size; ++i) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(get(i));
		}
		return sb.append(']').toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		long result = 1;
		result = prime * result + from;
		result = prime * result + size;
		result = prime * result + step;
		return (int) result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof List<?>)) {
			return false;
		}
		final List<?> other = (List<?>) obj;
		final int size = size();
		if (size != other.size()) {
			return false;
		}
		if (other instanceof RandomAccess) {
			for (int i = 0; i < size(); ++i) {
				if (!get(i).equals(other.get(i))) {
					return false;
				}
			}
		} else {
			final Iterator<?> it = other.iterator();
			for (Long aLong : this) {
				if (!aLong.equals(it.next())) {
					return false;
				}
			}
		}
		return true;
	}

	public long getFrom() {
		return from;
	}

	public long getStep() {
		return step;
	}

	public int getSize() {
		return size;
	}

	public static void main(final String... args) {
		final List<Long> l = new LongSeqList(1, 1, 10);
		System.out.println(l);
		System.out.println(l.indexOf(1));
		System.out.println(l.lastIndexOf(1));

		final List<Long> l2 = new LongSeqList(-1, -1, 10).subList(1, 6);
		System.out.println(l2);

		final List<Long> list = new ArrayList<>(l2);
		System.out.println(l2.equals(list));
	}
}
