package me.magicall.coll.tree;

import java.util.ArrayList;
import java.util.Collection;

import me.magicall.coll.tree.Tree.TreeNode;

public abstract class BaseTreeNode<E> extends TreeNodeTemplate<E> {

	protected final Tree<E> tree;
	protected final TreeNode<E> parent;
	protected final E element;

	protected BaseTreeNode(final Tree<E> tree, final E element, final TreeNode<E> parent) {
		super();
		this.tree = tree;
		this.parent = parent;
		this.element = element;
	}

	@Override
	public E getElement() {
		return element;
	}

	@Override
	public TreeNode<E> getParent() {
		return parent;
	}

	@Override
	public Collection<Tree<E>> getSubTrees() {
		final Collection<TreeNode<E>> derectChildren = getDerectChildren();
		final Collection<Tree<E>> rt = new ArrayList<>(derectChildren.size());
		for (final TreeNode<E> c : derectChildren) {
			rt.add(new SubTree<>(getTree(), c));
		}
		return rt;
	}

	@Override
	public Tree<E> treeFromMe() {
		return new SubTree<>(getTree(), this);
	}

	protected Tree<E> getTree() {
		return tree;
	}

}
