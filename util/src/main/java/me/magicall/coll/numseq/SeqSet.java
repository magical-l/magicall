package me.magicall.coll.numseq;

import me.magicall.coll.CollFactory.L;
import me.magicall.coll.ElementNotNull;
import me.magicall.coll.unmodifiable.UnmodifiableSetTemplate;
import me.magicall.mark.Unmodifiable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * <pre>
 * 一个有序的,均匀的,不可修改的数列(int),使用开始点,步长和数列长度来确定本数列.
 * 可参考SeqList
 * !若步长为0,则无论size是多少,都只有一个元素(set的特性)
 * !实现了SortedSet<Integer>接口
 * !实现了序列化接口,
 * </pre>
 * 
 * @author MaGicalL
 */
public class SeqSet extends UnmodifiableSetTemplate<Integer>//
		implements Unmodifiable, Serializable, Set<Integer>, SortedSet<Integer>, ElementNotNull {
	private static final long serialVersionUID = -2710231552869771339L;

	private final List<Integer> list;

	/**
	 * 若step为0,则无论size是多少,都只有一个元素(set的特性)
	 * 
	 * @param from 开始点
	 * @param step 步长
	 * @param size 数列长度
	 */
	public SeqSet(final int from, final int step, final int size) {
		super();
		list = buildList(from, step, size);
	}

	private SeqSet(final List<Integer> list) {
		super();
		this.list = list;
	}

	private static List<Integer> buildList(final int from, final int step, final int size) {
		return step == 0 ? //
		L.asList(from)
				: new SeqList(from, step, size);
	}

	public SeqSet(final int from, final int size) {
		this(from, 1, size);
	}

	@Override
	public boolean contains(final Object o) {
		return list.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	protected Iterator<Integer> iterator0() {
		return list.iterator();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return list.toArray(a);
	}

	private transient volatile Comparator<Integer> comp;

	@Override
	public Comparator<? super Integer> comparator() {
		if (comp == null) {
			if (list.get(list.size() - 1) >= list.get(0)) {
				comp = Comparator.naturalOrder();
			} else {
				comp = Collections.reverseOrder();
			}
		}
		return comp;
	}

	@Override
	public Integer first() {
		return list.get(0);
	}

	@Override
	public SortedSet<Integer> headSet(final Integer toElement) {
		return new SeqSet(list.subList(0, list.indexOf(toElement)));
	}

	@Override
	public Integer last() {
		return list.get(list.size() - 1);
	}

	@Override
	public SortedSet<Integer> subSet(final Integer fromElement, final Integer toElement) {
		return new SeqSet(list.subList(list.indexOf(fromElement), list.indexOf(toElement)));
	}

	@Override
	public SortedSet<Integer> tailSet(final Integer fromElement) {
		return new SeqSet(list.subList(list.indexOf(fromElement), list.size() - 1));
	}

	public List<Integer> toList() {
		return list;
	}
}
