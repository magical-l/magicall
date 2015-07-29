/**
 * 
 */
package me.magicall.coll.empty;

import me.magicall.coll.CollFactory.I;
import me.magicall.coll.fixed.Fixed;
import me.magicall.coll.sorted.Sorted;
import me.magicall.coll.tree.Tree;
import me.magicall.coll.tree.TreeNodeHandler;
import me.magicall.tagInterfaces.Unmodifiable;
import me.magicall.util.kit.Kits;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;

import static me.magicall.consts.CommonConst.NOT_FOUND_INDEX;

/**
 * 这是一个通用实现类，实现了Collection、List、Set、SortedSet等常见的集合接口
 * 
 * @author MaGiCalL
 */
public final class EmptyColl //
		implements Collection<Object>,//
		List<Object>,//
		Set<Object>, SortedSet<Object>,//
		Tree<Object>,//
		RandomAccess, Serializable, Unmodifiable, Sorted, Fixed {

	public static final EmptyColl INSTANCE = new EmptyColl();

	private EmptyColl() {

	}

	@Override
	public Object get(final int index) {
		throw new IndexOutOfBoundsException("Index: " + index + ", min: " + 0 + ", max:" + 0);
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Comparator<? super Object> comparator() {
		return null;
	}

	@Override
	public Object first() {
		return null;
	}

	@Override
	public SortedSet<Object> headSet(final Object toElement) {
		return Kits.SET.emptySortedSet();
	}

	@Override
	public Object last() {
		return null;
	}

	@Override
	public SortedSet<Object> subSet(final Object fromElement, final Object toElement) {
		return Kits.SET.emptySortedSet();
	}

	@Override
	public SortedSet<Object> tailSet(final Object fromElement) {
		return Kits.SET.emptySortedSet();
	}

	@Override
	public int getLayerCount() {
		return 0;
	}

	@Override
	public Collection<Object> getLeavesElements() {
		return Kits.COLL.emptyValue();
	}

	@Override
	public int getLeavesCount() {
		return 0;
	}

	@Override
	public Collection<TreeNode<Object>> getLeavesNodes() {
		return Kits.COLL.emptyValue();
	}

	@Override
	public Collection<TreeNode<Object>> getNodes() {
		return Kits.COLL.emptyValue();
	}

	@Override
	public Object getRootElement() {
		return null;
	}

	@Override
	public TreeNode<Object> getRootNode() {
		return null;
	}

	@Override
	public void deepFirst(final Collection<? extends TreeNodeHandler<Object>> treeNodeHandlers) {
	}

	@Override
	public void wideFirst(final Collection<? extends TreeNodeHandler<Object>> treeNodeHandlers) {
	}

	@Override
	public boolean contains(final Object o) {
		//参照Collections.EmptyList写的，永远返回false，即使参数为null
		return false;
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return false;
	}

	@Override
	public Iterator<Object> iterator() {
		return I.emptyIterator();
	}

	@Override
	public ListIterator<Object> listIterator(final int index) {
		return I.emptyListIterator();
	}

	@Override
	public List<Object> subList(final int fromIndex, final int toIndex) {
		if (fromIndex < 0) {
			throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
		}
		if (toIndex > 0) {
			throw new IndexOutOfBoundsException("toIndex = " + toIndex);
		}
		if (fromIndex > toIndex) {
			throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ')');
		}
		if (fromIndex == 0 && toIndex == 0) {
			return this;
		}
		throw new IndexOutOfBoundsException("Index: " + fromIndex + ", min: " + 0 + ", max:" + 0);
	}

	@Override
	public boolean equals(final Object o) {
		//参照AbstractList实现。
		return o instanceof Collection<?> && ((Collection<?>) o).isEmpty();
	}

	@Override
	public int hashCode() {
		//参照AbstractList实现。当没有元素时，hash code为1
		return 1;
	}

	@Override
	public int indexOf(final Object o) {
		return NOT_FOUND_INDEX;
	}

	@Override
	public int lastIndexOf(final Object o) {
		return NOT_FOUND_INDEX;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public Object[] toArray() {
		return Kits.OBJ.emptyArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return a;
	}

	@Override
	public String toString() {
		return "[]";
	}

	@Override
	public boolean add(final Object e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(final int index, final Object element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(final Collection<? extends Object> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends Object> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<Object> listIterator() {
		return I.emptyListIterator();
	}

	@Override
	public boolean remove(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object remove(final int index) {
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
	public Object set(final int index, final Object element) {
		throw new UnsupportedOperationException();
	}

	private static final long serialVersionUID = -7070927356384498504L;

	private Object readResolve() {
		return INSTANCE;
	}

}