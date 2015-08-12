package me.magicall.coll.wrap;

import me.magicall.coll.tree.Tree;
import me.magicall.coll.tree.Tree.TreeNode;
import me.magicall.coll.unmodifiable.UnmodifiableTreeNodeTemplate;

import java.util.Collection;
import java.util.List;

public class UnmodifiableWrapTreeNode<E> extends UnmodifiableTreeNodeTemplate<E> {
    private static final long serialVersionUID = -1586519064225142397L;

    protected final TreeNode<E> node;

    public UnmodifiableWrapTreeNode(final TreeNode<E> node) {
        super();
        this.node = node;
    }

    @Override
    protected TreeNode<E> parent0() {
        return node.getParent();
    }

    @Override
    protected Collection<TreeNode<E>> derectChildren0() {
        return node.getDerectChildren();
    }

    @Override
    protected Collection<Tree<E>> subTrees0() {
        return node.getSubTrees();
    }

    @Override
    protected Tree<E> treeFromMe0() {
        return node.treeFromMe();
    }

    @Override
    protected Collection<TreeNode<E>> allLeavesNodes0() {
        return node.getLeavesChildren();
    }

    @Override
    protected Collection<TreeNode<E>> allChildren0() {
        return node.getAllChildren();
    }

    @Override
    protected Collection<TreeNode<E>> brothers0() {
        return node.brothers();
    }

    @Override
    protected List<TreeNode<E>> pathFromRoot0() {
        return node.pathFromRoot();
    }

    @Override
    protected List<TreeNode<E>> pathToRoot0() {
        return node.pathToRoot();
    }

    @Override
    public E getElement() {
        return node.getElement();
    }

    @Override
    public int getDerectChildrenCount() {
        return node.getDerectChildrenCount();
    }

    @Override
    public int getAllChildrenCount() {
        return node.getAllChildrenCount();
    }

    @Override
    public boolean isRoot() {
        return node.isRoot();
    }

    @Override
    public boolean isLeaf() {
        return node.isLeaf();
    }

    @Override
    public int getLayer() {
        return node.getLayer();
    }

    @Override
    public boolean isChildOf(final TreeNode<E> target) {
        return node.isChildOf(target);
    }

    @Override
    public TreeNode<E> unwrap() {
        return this;
    }
}
