package me.magicall.coll.tree;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.ElementTransformerUtil.SerializableElementTransformer;
import me.magicall.coll.unmodifiable.UnmodifiableTreeTemplate;
import me.magicall.coll.wrap.UnmodifiableWrapTreeNode;
import me.magicall.util.kit.Kits;

import java.util.Collection;
import java.util.List;

/**
 * 专用于subTree()这个方法返回的类
 * 
 * @author Administrator
 * @param <E>
 */
class SubTree<E> extends UnmodifiableTreeTemplate<E> {

	private static final long serialVersionUID = -3747176680533544345L;

	private final TreeNode<E> root;
	private final int layerIndex;

	private class SubTreeNode extends UnmodifiableWrapTreeNode<E> {
		private static final long serialVersionUID = -2183616890313966257L;

		public SubTreeNode(final TreeNode<E> node) {
			super(node);
		}

		@Override
		protected TreeNode<E> parent0() {
			final TreeNode<E> parent = super.parent0();
			if (parent == null) {
				return parent;
			}
			return new SubTreeNode(parent);
		}

		@Override
		protected Collection<TreeNode<E>> derectChildren0() {
			return tf(super.derectChildren0());
		}

		@Override
		protected Collection<TreeNode<E>> allLeavesNodes0() {
			return tf(super.allLeavesNodes0());
		}

		@Override
		protected Collection<TreeNode<E>> allChildren0() {
			return tf(super.allChildren0());
		}

		@Override
		protected Collection<TreeNode<E>> brothers0() {
			return tf(super.brothers0());
		}

		@Override
		protected List<TreeNode<E>> pathFromRoot0() {
			return tf(super.pathFromRoot0());
		}

		@Override
		protected List<TreeNode<E>> pathToRoot0() {
			return tf(super.pathToRoot0());
		}

		@Override
		public int getLayer() {
			return super.getLayer() - layerIndex;
		}

		@Override
		public boolean isChildOf(final TreeNode<E> target) {
			final TreeNode<E> p = getParent();
			return p == target || p.isChildOf(target);
		}
	}//SubTreeNode

	public SubTree(final Tree<E> source, final TreeNode<E> r) {
		layerIndex = r.getLayer();
		root = new SubTreeNode(r) {

			private static final long serialVersionUID = 798547396362666928L;

			@Override
			protected Collection<TreeNode<E>> brothers0() {
				return Kits.COLL.emptyValue();
			}

			@Override
			public boolean isRoot() {
				return true;
			}

			@Override
			protected List<TreeNode<E>> pathFromRoot0() {
				return Kits.LIST.emptyValue();
			}

			@Override
			protected List<TreeNode<E>> pathToRoot0() {
				return Kits.LIST.emptyValue();
			}

			@Override
			protected Tree<E> treeFromMe0() {
				return SubTree.this;
			}

			@Override
			protected TreeNode<E> parent0() {
				return null;//别忘了,在这棵"子树"里面,此节点是根,所以没有parent
			}

			@Override
			public int getLayer() {
				return 0;
			}
		};
	}

	@Override
	protected TreeNode<E> rootNode0() {
		return root;
	}

	@SuppressWarnings("unchecked")
	private Collection<TreeNode<E>> tf(final Collection<TreeNode<E>> source) {
		return Kits.COLL.transform(source, (ElementTransformer) tf);
	}

	@SuppressWarnings("unchecked")
	private List<TreeNode<E>> tf(final List<TreeNode<E>> source) {
		return Kits.LIST.transform(source, (ElementTransformer) tf);
	}

	private final ElementTransformer<TreeNode<E>, SubTreeNode> tf = new SerializableElementTransformer<TreeNode<E>, SubTreeNode>() {
		private static final long serialVersionUID = 210549037850213110L;

		@Override
		public SubTreeNode transform(final int index, final TreeNode<E> element) {
			return new SubTreeNode(element);
		}
	};
}//SubTree