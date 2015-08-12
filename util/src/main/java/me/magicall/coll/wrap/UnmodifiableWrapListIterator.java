package me.magicall.coll.wrap;

import me.magicall.coll.unmodifiable.UnmodifiableListIteratorTemplate;

import java.util.ListIterator;

public class UnmodifiableWrapListIterator<E> extends UnmodifiableListIteratorTemplate<E> {
    private static final long serialVersionUID = -3594061630789916581L;

    private final ListIterator<E> i;

    public UnmodifiableWrapListIterator(final ListIterator<E> i) {
        super();
        this.i = i;
    }

    @Override
    public boolean hasNext() {
        return i.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return i.hasPrevious();
    }

    @Override
    public E next() {
        return i.next();
    }

    @Override
    public int nextIndex() {
        return i.nextIndex();
    }

    @Override
    public E previous() {
        return i.previous();
    }

    @Override
    public int previousIndex() {
        return i.previousIndex();
    }

    @Override
    public String toString() {
        return i.toString();
    }
}
