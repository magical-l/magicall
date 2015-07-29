package me.magicall.coll.tree;

import java.util.Collection;

import me.magicall.util.kit.Kits;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

class MapTree<E> extends TreeTemplate<E> {

	/*
	 * all elements are in values
	 * null-root
	 * root-sons
	 * son-grandsons
	 */
	protected final Multimap<TreeNode<E>, TreeNode<E>> subs;

	MapTree() {
		super();
		subs = buildMap();
	}

	MapTree(final Multimap<TreeNode<E>, TreeNode<E>> subs) {
		super();
		this.subs = subs;
	}

	public MapTree(final E rootElement) {
		this();
		final MapTreeNode parentOfRoot = null;
		putToSubs(parentOfRoot, new MapTreeNode(parentOfRoot, rootElement));
	}

	protected Multimap<TreeNode<E>, TreeNode<E>> buildMap() {
		return ArrayListMultimap.create();
	}

	protected boolean putToSubs(final TreeNode<E> parent, final TreeNode<E> child) {
		return subs.put(parent, child);
	}

	protected boolean putAllToSubs(final TreeNode<E> parent, final Collection<TreeNode<E>> children) {
		return subs.putAll(parent, children);
	}

	@Override
	public int getLeavesCount() {
		return getNodes().size() - subs.keySet().size();
	}

	@Override
	public TreeNode<E> getRootNode() {
		final Collection<TreeNode<E>> layer0 = subs.get(null);
		return Kits.COLL.isEmpty(layer0) ? null : layer0.iterator().next();
	}

	@Override
	public void clear() {
		subs.clear();
	}

	@Override
	public Collection<TreeNode<E>> getNodes() {
		return subs.values();
	}

	class MapTreeNode extends BaseTreeNode<E> implements TreeNode<E> {

		MapTreeNode(final MapTreeNode parent, final E element) {
			super(MapTree.this, element, parent);
		}

		@Override
		public MapTreeNode addChild(final E node) {
			final MapTreeNode child = new MapTreeNode(this, node);
			return putToSubs(this, child) ? child : null;
		}

		@Override
		public boolean addSubTree(final Tree<E> tree) {
			boolean modified = false;
			putToSubs(this, tree.getRootNode());//注意:参数的tree的TreeNode可能不是本类的实例
			for (final TreeNode<E> n : tree.getNodes()) {
				if (!n.isLeaf()) {
					modified |= putAllToSubs(n, n.getDerectChildren());
				}
			}
			return modified;
		}

		@Override
		public Collection<TreeNode<E>> getDerectChildren() {
			return subs.get(this);
		}
	}//MapTreeNode
}//MapTree