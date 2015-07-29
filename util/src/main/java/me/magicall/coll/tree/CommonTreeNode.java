package me.magicall.coll.tree;

import java.util.ArrayList;
import java.util.Collection;

import me.magicall.coll.tree.Tree.TreeNode;

public class CommonTreeNode<E> extends BaseTreeNode<E> {

	protected Collection<TreeNode<E>> derectChildren;

	protected CommonTreeNode(final Tree<E> tree, final E element, final TreeNode<E> parent, final Collection<TreeNode<E>> derectChildren) {
		super(tree, element, parent);
		this.derectChildren = derectChildren;
	}

	public CommonTreeNode(final Tree<E> tree, final E element, final TreeNode<E> parent) {
		this(tree, element, parent, null);
	}

	@Override
	public Collection<TreeNode<E>> getDerectChildren() {
		return derectChildren == null ? (derectChildren = buildDerectChildren()) : derectChildren;
	}

	protected Collection<TreeNode<E>> buildDerectChildren() {
		return new ArrayList<>();
	}

	@Override
	public TreeNode<E> addChild(final E element) {
		final TreeNode<E> node = new CommonTreeNode<>(getTree(), element, this);
		getDerectChildren().add(node);
		return node;
	}

}//AbsTreeNode