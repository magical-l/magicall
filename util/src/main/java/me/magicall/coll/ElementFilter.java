/**
 * 
 */
package me.magicall.coll;

/**
 * 集合元素的过滤器
 * @author Administrator
 */
@FunctionalInterface
public interface ElementFilter<E> {
	/**
	 * 只有符合条件的元素才会被循环方法处理
	 * @param index 此元素的下标
	 * @param element 此元素本身
	 * @return
	 */
    boolean accept(int index, E element);
}
