package me.magicall.coll.unmodifiable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import me.magicall.coll.CollFactory.C;
import me.magicall.coll.CollFactory.I;
import me.magicall.coll.CollFactory.T;
import me.magicall.coll.ElementTransformer;
import me.magicall.coll.tree.TreeTemplate;
import me.magicall.coll.tree.TreeUtil;
import me.magicall.mark.Unmodifiable;
import me.magicall.util.kit.Kits;

public abstract class UnmodifiableTreeTemplate<E> extends TreeTemplate<E> implements Serializable, Unmodifiable {

	private static final long serialVersionUID = -6018891976321902391L;

	private Collection<TreeNode<E>> unmodifiable(final Collection<TreeNode<E>> source, final ElementTransformer<TreeNode<E>, TreeNode<E>> tf) {
		final Collection<TreeNode<E>> rt = Kits.COLL.transform(source, tf);
		//若原集合即为不可变,则直接返回即可.否则还需对集合进行不可变包装
		return Kits.COLL.isUnmodifiable(source) ? rt : C.unmodifiable(rt);
	}

	@Override
	public final TreeNode<E> getRootNode() {
		return T.unmodifiable(rootNode0());
	}

	protected abstract TreeNode<E> rootNode0();

	@Override
	public final Collection<TreeNode<E>> getNodes() {
		return unmodifiable(nodes0(), TreeUtil.<E> unmodifiableTreeNodeTransformer());
	}

	protected Collection<TreeNode<E>> nodes0() {
		return super.getNodes();
	}

	@Override
	public final Collection<TreeNode<E>> getLeavesNodes() {
		return unmodifiable(leavesNodes0(), TreeUtil.<E> unmodifiableTreeNodeTransformer());
	}

	protected Collection<TreeNode<E>> leavesNodes0() {
		return super.getLeavesNodes();
	}

	@Override
	public final boolean add(final E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Iterator<E> iterator() {
		return I.unmodifiable(iterator0());
	}

	protected Iterator<E> iterator0() {
		return super.iterator();
	}

	@Override
	public final boolean addAll(final Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean remove(final Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean removeAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}
}
