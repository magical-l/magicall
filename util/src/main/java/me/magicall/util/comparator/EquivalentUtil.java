package me.magicall.util.comparator;

import java.util.Comparator;

import me.magicall.util.kit.Kits;

/**
 * @author MaGiCalL
 */
public class EquivalentUtil extends ComparatorAndEquivalentUtil {

	protected EquivalentUtil() {
	}

	private static final Equivalent<Object> INSTANCE_EQUIVALENT = new SerializableEquivalent<Object>() {
		private static final long serialVersionUID = 6329158523410355988L;

		@Override
		public boolean equals(final Object o1, final Object o2) {
			return o1 == o2;
		}
	};

	/**
	 * 强制进行实例比较的比较器
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Equivalent<T> instanceEquivalent() {
		return (Equivalent<T>) INSTANCE_EQUIVALENT;
	}

	private static final Equivalent<Object> EQUALS_EQUIVALENT = new SerializableEquivalent<Object>() {
		private static final long serialVersionUID = 8644345430045702749L;

		@Override
		public boolean equals(final Object o1, final Object o2) {
			return o1 == null ? o2 == null : o1.equals(o2);
		}
	};

	/**
	 * 使用对象本身的equals方法比较的比较器。适用于某些必须使用一个Equivalent的场合
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Equivalent<T> equalsEquivalent() {
		return (Equivalent<T>) EQUALS_EQUIVALENT;
	}

	private static final Equivalent<Object> DEEP_EQUALS_EQUIVALENT = new SerializableEquivalent<Object>() {
		private static final long serialVersionUID = 6139441683421094936L;

		@Override
		public boolean equals(final Object o1, final Object o2) {
			return Kits.OBJ.deepEquals(o1, o2);
		}
	};

	/**
	 * 对对象进行深度比较的比较器,对数组很有用
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Equivalent<T> deepEqualsEquivalent() {
		return (Equivalent<T>) DEEP_EQUALS_EQUIVALENT;
	}

	/**
	 * 将Comparator包装起来的比较器
	 * 
	 * @param <T>
	 * @param comparator
	 * @return
	 */
	public static <T> Equivalent<T> comparatorEquivalent(final Comparator<? super T> comparator) {
		return new WrappingComparatorCwE<>(comparator);
	}
}
