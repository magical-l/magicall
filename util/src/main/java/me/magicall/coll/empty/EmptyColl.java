/**
 *
 */
package me.magicall.coll.empty;

import me.magicall.coll.fixed.Fixed;
import me.magicall.coll.sorted.Sorted;
import me.magicall.coll.tree.Tree;
import me.magicall.coll.tree.TreeNodeHandler;
import me.magicall.mark.Unmodifiable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

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
        super();
    }

    @Override
    public int size() {
        return Collections.emptyList().size();
    }

    @Override
    public boolean isEmpty() {
        return Collections.emptyList().isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return Collections.emptyList().contains(o);
    }

    @Override
    public Iterator<Object> iterator() {
        return Collections.emptyList().iterator();
    }

    @Override
    public Object[] toArray() {
        return Collections.emptyList().toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return Collections.<T>emptyList().toArray(a);
    }

    @Override
    public boolean add(final Object o) {
        return Collections.emptyList().add(o);
    }

    @Override
    public boolean remove(final Object o) {
        return Collections.emptyList().remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return Collections.emptyList().containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<?> c) {
        return Collections.emptyList().addAll(c);
    }

    @Override
    public boolean addAll(final int index, final Collection<?> c) {
        return Collections.emptyList().addAll(index, c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return Collections.emptyList().removeAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return Collections.emptyList().retainAll(c);
    }

    @Override
    public void replaceAll(final UnaryOperator<Object> operator) {
        Collections.emptyList().replaceAll(operator);
    }

    @Override
    public void sort(final Comparator<? super Object> c) {
        Collections.emptyList().sort(c);
    }

    @Override
    public void clear() {
        Collections.emptyList().clear();
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof List) {
            return Collections.emptyList().equals(o);
        } else if (o instanceof Set) {
            return Collections.emptySet().equals(o);
        } else if (o instanceof Tree) {
            final Tree<?> tree = (Tree) o;
            return tree.isEmpty();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Collections.emptyList().hashCode();
    }

    @Override
    public Object get(final int index) {
        return Collections.emptyList().get(index);
    }

    @Override
    public Object set(final int index, final Object element) {
        return Collections.emptyList().set(index, element);
    }

    @Override
    public void add(final int index, final Object element) {
        Collections.emptyList().add(index, element);
    }

    @Override
    public Object remove(final int index) {
        return Collections.emptyList().remove(index);
    }

    @Override
    public int indexOf(final Object o) {
        return Collections.emptyList().indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return Collections.emptyList().lastIndexOf(o);
    }

    @Override
    public ListIterator<Object> listIterator() {
        return Collections.emptyList().listIterator();
    }

    @Override
    public ListIterator<Object> listIterator(final int index) {
        return Collections.emptyList().listIterator(index);
    }

    @Override
    public List<Object> subList(final int fromIndex, final int toIndex) {
        return Collections.emptyList().subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<Object> spliterator() {
        return Collections.emptyList().spliterator();
    }

    @Override
    public boolean removeIf(final Predicate<? super Object> filter) {
        return Collections.emptyList().removeIf(filter);
    }

    @Override
    public Stream<Object> stream() {
        return Collections.emptyList().stream();
    }

    @Override
    public Stream<Object> parallelStream() {
        return Collections.emptyList().parallelStream();
    }

    @Override
    public void forEach(final Consumer<? super Object> action) {
        Collections.emptyList().forEach(action);
    }

    @Override
    public Comparator<? super Object> comparator() {
        return Collections.emptySortedSet().comparator();
    }

    @Override
    public SortedSet<Object> subSet(final Object fromElement, final Object toElement) {
        return Collections.emptySortedSet().subSet(fromElement, toElement);
    }

    @Override
    public SortedSet<Object> headSet(final Object toElement) {
        return Collections.emptySortedSet().headSet(toElement);
    }

    @Override
    public SortedSet<Object> tailSet(final Object fromElement) {
        return Collections.emptySortedSet().tailSet(fromElement);
    }

    @Override
    public Object first() {
        return Collections.emptySortedSet().first();
    }

    @Override
    public Object last() {
        return Collections.emptySortedSet().last();
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
    public int getLayerCount() {
        return 0;
    }

    @Override
    public Collection<Object> getLeavesElements() {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<TreeNode<Object>> getLeavesNodes() {
        return (Collection) this;
    }

    @Override
    public int getLeavesCount() {
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<TreeNode<Object>> getNodes() {
        return (Collection) this;
    }

    @Override
    public void deepFirst(final Collection<? extends TreeNodeHandler<Object>> treeNodeHandlers) {
    }

    @Override
    public void wideFirst(final Collection<? extends TreeNodeHandler<Object>> treeNodeHandlers) {
    }

    //=================================================

    private static final long serialVersionUID = -7070927356384498504L;

    private Object readResolve() {
        return INSTANCE;
    }
}