package me.magicall.coll;

import java.util.Iterator;

/**
 * 组合模式的节点
 * 
 * @param <T>
 */
public interface ComponentItem<T extends ComponentItem<T>> extends Iterable<T> {

	public boolean leaf();

	@Override
	public Iterator<T> iterator();

}
