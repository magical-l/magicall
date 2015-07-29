package me.magicall.coll.tree;

import me.magicall.coll.tree.Tree.TreeNode;

public interface TreeNodeHandler<E> {

	void handle(TreeNode<E> node);
}
