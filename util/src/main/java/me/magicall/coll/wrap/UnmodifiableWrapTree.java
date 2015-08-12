package me.magicall.coll.wrap;

import me.magicall.coll.tree.Tree;
import me.magicall.coll.unmodifiable.UnmodifiableTreeTemplate;
import me.magicall.mark.Wrapper;

import java.util.Collection;
import java.util.Iterator;

public class UnmodifiableWrapTree<E> extends UnmodifiableTreeTemplate<E>//
		implements Wrapper<Tree<E>> {
	private static final long serialVersionUID = -6233847775289865089L;

	private final Tree<E> tree;

	public UnmodifiableWrapTree(final Tree<E> tree) {
		super();
		this.tree = tree;
	}

	@Override
	protected Iterator<E> iterator0() {
		return tree.iterator();
	}

	@Override
	public String toString() {
		return tree.toString();
	}

	@Override
	protected TreeNode<E> rootNode0() {
		return tree.getRootNode();
	}

	@Override
	public E getRootElement() {
		return tree.getRootElement();
	}

	@Override
	public int getLayerCount() {
		return tree.getLayerCount();
	}

	@Override
	public Collection<E> getLeavesElements() {
		return tree.getLeavesElements();
	}

	@Override
	protected Collection<TreeNode<E>> leavesNodes0() {
		return tree.getLeavesNodes();
	}

	@Override
	public int getLeavesCount() {
		return tree.getLeavesCount();
	}

	@Override
	protected Collection<TreeNode<E>> nodes0() {
		return tree.getNodes();
	}

	@Override
	public int size() {
		return tree.size();
	}

	@Override
	public boolean isEmpty() {
		return tree.isEmpty();
	}

	@Override
	public boolean contains(final Object o) {
		return tree.contains(o);
	}

	@Override
	public Object[] toArray() {
		return tree.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return tree.toArray(a);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return tree.containsAll(c);
	}

	@Override
	public boolean equals(final Object o) {
		return tree.equals(o);
	}

	@Override
	public int hashCode() {
		return tree.hashCode();
	}

	@Override
	public Tree<E> unwrap() {
		return this;
	}
}
