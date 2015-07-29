package me.magicall.coll.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.L;
import me.magicall.coll.CollFactory.T;
import me.magicall.coll.ElementTransformer;
import me.magicall.coll.empty.EmptyColl;
import me.magicall.coll.tree.Tree.TreeNode;
import me.magicall.util.kit.Kits;

public class TreeUtil {

	@SuppressWarnings("unchecked")
	public static <E> Tree<E> emptyTree() {
		return (Tree) EmptyColl.INSTANCE;
	}

	public static <E> Collection<E> nodesToElements(final Collection<TreeNode<E>> nodes) {
		return Kits.COLL.transform(nodes, TreeUtil.<E> nodesToElementTransformer());
	}

	private static final ElementTransformer<TreeNode<Object>, Object> NODES_TO_ELEMENT_TRANSFORMER = (index,
																									  element) -> element.getElement();

	@SuppressWarnings("unchecked")
	public static <E> ElementTransformer<TreeNode<E>, E> nodesToElementTransformer() {
		return (ElementTransformer) NODES_TO_ELEMENT_TRANSFORMER;
	}

	private static final ElementTransformer<TreeNode<Object>, Integer> NODE_TO_LAYER = (index,
																						element) -> element.getLayer();

	@SuppressWarnings("unchecked")
	public static <E> ElementTransformer<TreeNode<E>, Integer> nodesToLayerTransformer() {
		return (ElementTransformer) NODE_TO_LAYER;
	}

	private static final ElementTransformer<TreeNode<Object>, TreeNode<Object>> UNMODIFIABLE_TF = (index,
																								   element) -> T.unmodifiable(element);

	@SuppressWarnings("unchecked")
	public static final <E> ElementTransformer<TreeNode<E>, TreeNode<E>> unmodifiableTreeNodeTransformer() {
		return (ElementTransformer) UNMODIFIABLE_TF;
	}

	private static final ElementTransformer<Tree<Object>, Tree<Object>> UNMODIFIABLE_TREE_TF = (index,
																								element) -> T.unmodifiable(element);

	@SuppressWarnings("unchecked")
	public static final <E> ElementTransformer<Tree<E>, Tree<E>> unmodifiableTreeTransformer() {
		return (ElementTransformer) UNMODIFIABLE_TREE_TF;
	}

	public static void main(final String... args) {
		final int root = 0;
		final int size = 10;
		final List<Integer> source = new ArrayList<>(L.seq(root, size));
		Collections.shuffle(source);
		System.out.println("@@@@@@" + source);
	}
}
