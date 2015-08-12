package me.magicall.coll.wrap;

import me.magicall.coll.unmodifiable.UnmodifiableIteratorTemplate;
import me.magicall.mark.Wrapper;

import java.util.Iterator;

public class UnmodifiableWrapIterator<E> extends UnmodifiableIteratorTemplate<E>//
        implements Wrapper<Iterator<E>> {

    private static final long serialVersionUID = -2521809833880889447L;

    private final Iterator<E> i;

    public UnmodifiableWrapIterator(final Iterator<E> i) {
        super();
        this.i = i;
    }

    @Override
    public boolean hasNext() {
        return i.hasNext();
    }

    @Override
    public E next() {
        return i.next();
    }

    @Override
    public String toString() {
        return i.toString();
    }

    @Override
    public Iterator<E> unwrap() {
        return this;
    }
}
