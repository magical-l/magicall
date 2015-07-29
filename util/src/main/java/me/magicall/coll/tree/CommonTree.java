package me.magicall.coll.tree;

public class CommonTree<E> extends TreeTemplate<E> {

	protected TreeNode<E> rootNode;

	public CommonTree(final E element) {
		rootNode = newTreeNode(element, null);
	}

	protected TreeNode<E> newTreeNode(final E element, final TreeNode<E> parent) {
		return new CommonTreeNode<>(this, element, null);
	}

	@Override
	public TreeNode<E> getRootNode() {
		return rootNode;
	}

	public static void main(final String... args) {
		final Tree<Integer> tree = new CommonTree<>(0);
		final TreeNode<Integer> root = tree.getRootNode();
		root.addChild(1).addChild(2).addChild(3);
		root.addChild(4).addChild(5).addChild(6);
		root.addChild(7).addChild(8).addChild(9);
		System.out.println("@@@@@@CommonTree.main():" + tree);
	}
}