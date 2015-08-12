package me.magicall.coll.tree;

import me.magicall.coll.tree.Tree.TreeNode;
import me.magicall.util.kit.Kits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 依赖的方法:
 * getDerectChildren()
 * 	|-allLeavesNodes()
 * 	|-allChildren()
 * 	|-allChildrenCount()
 * 	|	|-isLeaf()
 * 	|		|-allLeavesNodes()
 * 	|-brothers()
 * 	|-derectChildrenCount()
 * getParent()
 * 	|-brothers()
 * 	|-isRoot()
 * 	|	|-layer()
 * 	|-pathFromRoot()
 * 	|-pathToRoot()
 * 	|-layer()
 * 	|-isChild()
 * addChild()
 * 	|-addChildren()
 * addSubTree()
 * </pre>
 */
public abstract class TreeNodeTemplate<E> implements TreeNode<E> {

	@Override
	public Collection<TreeNode<E>> getLeavesChildren() {
		return addAllLeaves(this, new LinkedList<>());
	}

	/*
	 * 递归
	 * 依赖:getDerectChildren(),isLeaf()
	 * 不影响本身
	 */
	private Collection<TreeNode<E>> addAllLeaves(final TreeNode<E> node, final Collection<TreeNode<E>> rt) {
		final Collection<TreeNode<E>> derectChildren = node.getDerectChildren();
		for (final TreeNode<E> n : derectChildren) {
			if (n.isLeaf()) {
				rt.add(n);
			} else {
				addAllLeaves(n, rt);
			}
		}
		return rt;
	}

	/*
	 * 递归
	 * 依赖:getDerectChildren()
	 * 不影响本身
	 * (深度优先)
	 */
	@Override
	public Collection<TreeNode<E>> getAllChildren() {
		final Collection<TreeNode<E>> rt = new LinkedList<>();
		final Collection<TreeNode<E>> children = getDerectChildren();
		for (final TreeNode<E> son : children) {
			rt.add(son);
			rt.addAll(son.getAllChildren());
		}
		return rt;
	}

	/*
	 * 递归
	 * 依赖:getDerectChildren()
	 */
	@Override
	public int getAllChildrenCount() {
		int rt = 0;
		final Collection<TreeNode<E>> children = getDerectChildren();
		rt += children.size();
		for (final TreeNode<E> n : children) {
			rt += n.getAllChildrenCount();
		}
		return rt;
	}

	/*
	 * 依赖:getParent(),getDerectChildren()
	 * 不影响本身
	 */
	@Override
	public Collection<TreeNode<E>> brothers() {
		if (isRoot()) {
			return Kits.COLL.emptyValue();
		}
		final Collection<TreeNode<E>> rt = new ArrayList<>();
		final Collection<TreeNode<E>> generation = getParent().getDerectChildren();
		for (final TreeNode<E> n : generation) {
			if (n != this) {
				rt.add(n);
			}
		}
		return rt;
	}

	/*
	 * 依赖:getParent()
	 */
	@Override
	public boolean isRoot() {
		return getParent() == null;
	}

	/*
	 * 依赖:derectChildrenCount()
	 */
	@Override
	public boolean isLeaf() {
		return getDerectChildrenCount() == 0;
	}

	/*
	 * 依赖:getDerectChildren()
	 */
	@Override
	public int getDerectChildrenCount() {
		return getDerectChildren().size();
	}

	/*
	 * 依赖:getParent()
	 * 不影响本身
	 */
	@Override
	public List<TreeNode<E>> pathFromRoot() {
		if (isRoot()) {
			return Kits.LIST.emptyValue();
		}
		final List<TreeNode<E>> rt = new LinkedList<>();
		for (TreeNode<E> n = getParent(); n != null; n = n.getParent()) {
			rt.add(0, n);
		}
		return rt;
	}

	/*
	 * 依赖:getParent()
	 * 不影响本身
	 */
	@Override
	public List<TreeNode<E>> pathToRoot() {
		if (isRoot()) {
			return Kits.LIST.emptyValue();
		}
		final List<TreeNode<E>> rt = new LinkedList<>();
		for (TreeNode<E> n = getParent(); n != null; n = n.getParent()) {
			rt.add(n);
		}
		return rt;
	}

	/*
	 * 递归
	 * 依赖:isRoot(),getParent()
	 */
	@Override
	public int getLayer() {
		return isRoot() ? 0 : getParent().getLayer() + 1;
	}

	/*
	 * 依赖:getParent()
	 */
	@Override
	public boolean isChildOf(final TreeNode<E> target) {
		final TreeNode<E> p = getParent();
		return p == target || p.isChildOf(target);
	}

	/*
	 * 依赖:addChild()
	 */
	@Override
	public Map<E, TreeNode<E>> addChildren(final Collection<E> elements) {
		final Map<E, TreeNode<E>> map = new HashMap<>();
		for (final E e : elements) {
			map.put(e, addChild(e));
		}
		return map;
	}

	@Override
	public TreeNode<E> addChild(final E node) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addSubTree(final Tree<E> tree) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "TreeNode:{element:" + getElement() + ",parent:" + (isRoot() ? "null" : getParent().getElement()) + ",layer:" + getLayer() + ",children:"
				+ TreeUtil.nodesToElements(getDerectChildren());
	}
}//AbsTreeNode