package me.magicall.util.comparator;

/**
 * 比较两个对象，判断它们是否等价。类似于Comparator的接口，但并不关心两者谁大谁小，仅关心是否等价。
 * 
 * @author MaGiCalL
 * @param <T>
 */
@FunctionalInterface
public interface Equivalent<T> {

	/**
	 * 比较两个对象，判断它们是否等价。
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	boolean equals(T o1, T o2);
}
