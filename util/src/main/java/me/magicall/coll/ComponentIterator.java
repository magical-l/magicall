package me.magicall.coll;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ComponentIterator<E extends ComponentItem<E>> implements Iterator<E> {
	
	private Deque<Iterator<E>> stack = new ArrayDeque<>();

	public ComponentIterator(final Iterator<E> ite) {
		stack.push(ite);
	}
	
	@Override
	public boolean hasNext() {
		if(stack.isEmpty()) {
			return false;
		} else {
			final Iterator<E> iterator = stack.peek();
			if(iterator.hasNext()) {
				return true;
			} else {
				stack.pop();
				return hasNext();
			}
		}
	}

	@Override
	public E next() {
		if(hasNext()) {
			final Iterator<E> iterator = stack.peek();
			final E item = iterator.next();
			if(!item.leaf()) {
				stack.push(item.iterator());
			}
			return item;
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
