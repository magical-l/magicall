/**
 * 
 */
package me.magicall.coll;

/**
 * 本接口定义了对集合元素的操作.
 * @author Administrator
 */
@FunctionalInterface
public interface ElementHandler<E> {

	/**
	 * 对集合中的元素进行的操作
	 * @param index 此元素在集合中的下标
	 * @param element 此元素本身
	 */
	boolean execute(int index, E element);
}
