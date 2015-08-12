package me.magicall.coll.unmodifiable;

import me.magicall.coll.CollFactory.C;
import me.magicall.coll.CollFactory.T;
import me.magicall.coll.ElementTransformer;
import me.magicall.coll.tree.Tree;
import me.magicall.coll.tree.Tree.TreeNode;
import me.magicall.coll.tree.TreeNodeTemplate;
import me.magicall.coll.tree.TreeUtil;
import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;
import me.magicall.util.kit.Kits;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class UnmodifiableTreeNodeTemplate<E> extends TreeNodeTemplate<E>//
		implements Unmodifiable, Serializable, Wrapper<TreeNode<E>> {
	private static final long serialVersionUID = 8055421718613235786L;

	private static <T> Collection<T> unmodifiable(final Collection<T> source, final ElementTransformer<T, T> tf) {
		final Collection<T> rt = Kits.COLL.transform(source, tf);
		//若原集合即为不可变,则直接返回即可.否则还需对集合进行不可变包装
		return Kits.COLL.isUnmodifiable(source) ? rt : C.unmodifiable(rt);
	}

	private static <T> List<T> unmodifiable(final List<T> source, final ElementTransformer<T, T> tf) {
		final List<T> rt = Kits.LIST.transform(source, tf);
		//若原集合即为不可变,则直接返回即可.否则还需对集合进行不可变包装
		return Kits.LIST.isUnmodifiable(source) ? rt : Kits.LIST.unmodifiable(rt);
	}

	private ElementTransformer<TreeNode<E>, TreeNode<E>> nodeTf() {
		return TreeUtil.unmodifiableTreeNodeTransformer();
	}

	private ElementTransformer<Tree<E>, Tree<E>> treeTf() {
		return TreeUtil.unmodifiableTreeTransformer();
	}

	@Override
	public final TreeNode<E> getParent() {
		return T.unmodifiable(parent0());
	}

	protected abstract TreeNode<E> parent0();

	@Override
	public final Collection<TreeNode<E>> getDerectChildren() {
		return unmodifiable(derectChildren0(), nodeTf());
	}

	protected abstract Collection<TreeNode<E>> derectChildren0();

	@Override
	public final Collection<Tree<E>> getSubTrees() {
		return unmodifiable(subTrees0(), treeTf());
	}

	protected abstract Collection<Tree<E>> subTrees0();

	@Override
	public final Tree<E> treeFromMe() {
		return T.unmodifiable(treeFromMe0());
	}

	protected abstract Tree<E> treeFromMe0();

	@Override
	public final Collection<TreeNode<E>> getLeavesChildren() {
		return unmodifiable(allLeavesNodes0(), nodeTf());
	}

	protected Collection<TreeNode<E>> allLeavesNodes0() {
		return super.getLeavesChildren();
	}

	@Override
	public final Collection<TreeNode<E>> getAllChildren() {
		return unmodifiable(allChildren0(), nodeTf());
	}

	protected Collection<TreeNode<E>> allChildren0() {
		return super.getAllChildren();
	}

	@Override
	public final Collection<TreeNode<E>> brothers() {
		return unmodifiable(brothers0(), nodeTf());
	}

	protected Collection<TreeNode<E>> brothers0() {
		return super.brothers();
	}

	@Override
	public final List<TreeNode<E>> pathFromRoot() {
		return unmodifiable(pathFromRoot0(), nodeTf());
	}

	protected List<TreeNode<E>> pathFromRoot0() {
		return super.pathFromRoot();
	}

	@Override
	public final List<TreeNode<E>> pathToRoot() {
		return unmodifiable(pathToRoot0(), nodeTf());
	}

	protected List<TreeNode<E>> pathToRoot0() {
		return super.pathToRoot();
	}

	@Override
	public final TreeNode<E> addChild(final E node) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Map<E, TreeNode<E>> addChildren(final Collection<E> elements) {
		throw new UnsupportedOperationException();
	}
}//node class
