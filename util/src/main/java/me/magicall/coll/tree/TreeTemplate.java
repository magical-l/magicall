package me.magicall.coll.tree;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.I;
import me.magicall.coll.CollFactory.L;
import me.magicall.coll.ElementTransformer;
import me.magicall.util.kit.Kits;
import me.magicall.util.kit.StrKit;

/**
 * 依赖的方法:
 * rootNode()
 * TreeNode.derectChildren()
 */
public abstract class TreeTemplate<E> extends AbstractCollection<E> implements Tree<E> {

	protected TreeTemplate() {
		super();
	}

	@Override
	public int getLayerCount() {
		return Collections.max(Kits.COLL.transform(getLeavesNodes(), TreeUtil.<E> nodesToLayerTransformer())) + 1;
	}

	protected Collection<TreeNode<E>> createReturnNodeCollection(final int size) {
		return new ArrayList<>(size);
	}

	/**
	 * 不影响本身
	 */
	@Override
	public Collection<TreeNode<E>> getNodes() {
		if (isEmpty()) {
			return Kits.COLL.emptyValue();
		}
		final Collection<TreeNode<E>> rt = new LinkedList<>();
		wideFirst(L.asList(node -> {
            rt.add(node);
        }));
		return rt;
	}

	@Override
	public boolean add(final E e) {
		if (isEmpty()) {
			throw new RuntimeException("no root element");
		}
		return getRootNode().addChild(e) != null;
	}

	@Override
	public int getLeavesCount() {
		return getLeavesNodes().size();
	}

	/**
	 * 不影响本身
	 */
	@Override
	public Collection<E> getLeavesElements() {
		return TreeUtil.nodesToElements(getLeavesNodes());
	}

	@Override
	public Iterator<E> iterator() {
		return I.transformed(getNodes().iterator(), nodeToElementTransformer());
//		return new Iterator<E>() {
//
//			@Override
//			public boolean hasNext() {
//				return false;
//			}
//
//			@Override
//			public E next() {
//				return null;
//			}
//
//			@Override
//			public void remove() {
//				
//			}
//		};
	}

	protected final ElementTransformer<TreeNode<E>, E> nodeToElementTransformer() {
		return TreeUtil.nodesToElementTransformer();
	}

	@Override
	public int size() {
		return isEmpty() ? 0 : getRootNode().getAllChildrenCount() + 1;
	}

	@Override
	public boolean isEmpty() {
		return getRootNode() == null;
	}

	@Override
	public E getRootElement() {
		if (isEmpty()) {
			return null;
		}
		return getRootNode().getElement();
	}

	/**
	 * 不影响本身
	 */
	@Override
	public Collection<TreeNode<E>> getLeavesNodes() {
		if (isEmpty()) {
			return Kits.COLL.emptyValue();
		}
		return getRootNode().getLeavesChildren();
	}

	@Override
	public String toString() {
		//这个toString真™帅!
		if (isEmpty()) {
			return getClass().getSimpleName() + "[empty]";
		}
		final StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append(':').append(StrKit.newLine());
		deepFirst(Collections.<TreeNodeHandler<E>> singletonList(new ToStringNodeHandler<>(sb)));
		return sb.toString();
	}

	/**
	 * 不影响本身
	 */
	@Override
	public void deepFirst(final Collection<? extends TreeNodeHandler<E>> treeNodeHandlers) {
		if (isEmpty() || Kits.COLL.isEmpty(treeNodeHandlers)) {
			return;
		}
		deepFirst0(getRootNode(), treeNodeHandlers);
	}

	private void deepFirst0(final TreeNode<E> node, final Collection<? extends TreeNodeHandler<E>> treeNodeHandlers) {
		handleNode(node, treeNodeHandlers);
		final Collection<TreeNode<E>> children = node.getDerectChildren();
		for (final TreeNode<E> c : children) {
			deepFirst0(c, treeNodeHandlers);
		}
	}

	private void handleNode(final TreeNode<E> node, final Collection<? extends TreeNodeHandler<E>> treeNodeHandlers) {
		for (final TreeNodeHandler<E> h : treeNodeHandlers) {
			h.handle(node);
		}
	}

	@Override
	public void wideFirst(final Collection<? extends TreeNodeHandler<E>> treeNodeHandlers) {
		if (isEmpty() || Kits.COLL.isEmpty(treeNodeHandlers)) {
			return;
		}
		final Queue<TreeNode<E>> queue = new LinkedList<>();
		final TreeNode<E> root = getRootNode();
		queue.add(root);
		while (!queue.isEmpty()) {
			final TreeNode<E> node = queue.poll();
			queue.addAll(node.getDerectChildren());
			handleNode(node, treeNodeHandlers);
		}
	}

	protected static class ToStringNodeHandler<E> implements TreeNodeHandler<E> {

		protected Appendable appendable;

		public ToStringNodeHandler() {
			super();
		}

		public ToStringNodeHandler(final Appendable appendable) {
			super();
			this.appendable = appendable;
		}

		@Override
		public void handle(final TreeNode<E> node) {
			final String identationToParent = getIdentationToParent();
			final Appendable appendable = getAppendable();
			try {
				if (!node.isRoot()) {
					for (int i = node.getLayer(); i > 1; --i) {
						appendable.append(identationToParent);
					}
					appendable.append(getLeading());
				}
				appendable.append(nodeToString(node)).append(StrKit.newLine());
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		protected String getLeading() {
			return " |-";
		}

		protected String nodeToString(final TreeNode<E> node) {
			return String.valueOf(node);
		}

		protected String getIdentationToParent() {
			return " |";
		}

		public Appendable getAppendable() {
			return appendable == null ? (appendable = new StringBuilder()) : appendable;
		}

		public void setAppendable(final Appendable sb) {
			appendable = sb;
		}
	}//ToStringNodeHandler
}//AbsTree