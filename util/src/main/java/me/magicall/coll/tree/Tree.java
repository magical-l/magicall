package me.magicall.coll.tree;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Tree<E> extends Collection<E> {

	/**
	 * 根节点元素
	 * 
	 * @return
	 */
	E getRootElement();

	/**
	 * 根节点
	 * 
	 * @return
	 */
	TreeNode<E> getRootNode();

	/**
	 * 树的深度
	 * 
	 * @return
	 */
	int getLayerCount();

	/**
	 * 所有叶子元素
	 * 
	 * @return
	 */
	Collection<E> getLeavesElements();

	/**
	 * 所有叶子节点
	 * 
	 * @return
	 */
	Collection<TreeNode<E>> getLeavesNodes();

	/**
	 * 叶子数
	 * 
	 * @return
	 */
	int getLeavesCount();

	/**
	 * 所有节点
	 * 
	 * @return
	 */
	Collection<TreeNode<E>> getNodes();

	/**
	 * 深度优先遍历
	 * 
	 * @return
	 */
	void deepFirst(Collection<? extends TreeNodeHandler<E>> treeNodeHandlers);

	/**
	 * 广度优先
	 * 
	 * @return
	 */
	void wideFirst(Collection<? extends TreeNodeHandler<E>> treeNodeHandlers);

	public interface TreeNode<E> {

		E getElement();

		/**
		 * 父节点
		 * 
		 * @return
		 */
		TreeNode<E> getParent();

		/**
		 * 直接子节点的数量(==derectChildren().size()==subTrees().size())
		 * 
		 * @return
		 */
		int getDerectChildrenCount();

		/**
		 * 直接子节点
		 * 
		 * @return
		 */
		Collection<TreeNode<E>> getDerectChildren();

		/**
		 * 所有子孙节点的数量
		 * 
		 * @return
		 */
		int getAllChildrenCount();

		/**
		 * 所有子孙节点
		 * 
		 * @return
		 */
		Collection<TreeNode<E>> getAllChildren();

		/**
		 * 子树
		 * 
		 * @return
		 */
		Collection<Tree<E>> getSubTrees();

		/**
		 * 以本节点为根的子树.(<=原树)
		 * 
		 * @return
		 */
		Tree<E> treeFromMe();

		/**
		 * 是否本树的根节点
		 * 
		 * @return
		 */
		boolean isRoot();

		/**
		 * 是否本树的叶子节点
		 * 
		 * @return
		 */
		boolean isLeaf();

		/**
		 * 本节点到达根的路径
		 * 不包含本节点
		 * 与pathFromRoot反序
		 * 
		 * @return
		 */
		List<TreeNode<E>> pathToRoot();

		/**
		 * 从根到本节点的路径
		 * 不包含本节点
		 * 与pathToRoot反序
		 * 
		 * @return
		 */
		List<TreeNode<E>> pathFromRoot();

		/**
		 * 本节点在树的第几层.根节点为第0层
		 * 
		 * @return
		 */
		int getLayer();

		/**
		 * 兄弟节点
		 * 
		 * @return
		 */
		Collection<TreeNode<E>> brothers();

		/**
		 * 此节点的子孙节点中的所有叶子节点
		 * 
		 * @return
		 */
		Collection<TreeNode<E>> getLeavesChildren();

		/**
		 * 判断本节点是否参数节点的子孙
		 * 若参数节点即为本节点,也返回false(自己不是自己的子)
		 * 若参数为null,也返回false
		 * 
		 * @param target
		 * @return
		 */
		boolean isChildOf(TreeNode<E> target);

		/**
		 * 喜得贵子
		 * 
		 * @param node
		 * @return
		 */
		TreeNode<E> addChild(E node);

		boolean addSubTree(Tree<E> tree);

		Map<E, TreeNode<E>> addChildren(Collection<E> elements);

	}
}
