package me.magicall.util.comparator;

import me.magicall.util.comparator.ComparatorAndEquivalentUsingComparbleFieldTemplate.SerializableComparatorAndEquivalentUsingFieldTemplate;

import java.util.Comparator;

public class ComparatorAndEquivalentUtil {

	protected ComparatorAndEquivalentUtil() {
	}

	private static final ComparatorWithEquivalent<Comparable<?>> COMPARABLE_COMPARATOR = new SerializableComparatorAndEquivalentUsingFieldTemplate<Comparable<?>>() {
		private static final long serialVersionUID = 7166587754869636337L;

		@Override
		protected Comparable<?> comparableField(final Comparable<?> o) {
			return o;
		}
	};

	/**
	 * 随机比较器，请慎用
	 */
	private static final ComparatorWithEquivalent<Object> RANDOM_COMPARATOR = new SerializableComparatorWithEquivalentTemplate<Object>() {
		private static final long serialVersionUID = 8036246760218424715L;

		@Override
		public int compare(final Object o1, final Object o2) {
			return (int) Math.random();
		}
	};
	/**
	 * 字符串长度比较器
	 */
	public static final ComparatorWithEquivalent<String> STR_LEN_COMPARATOR = new SerializableComparatorWithEquivalentTemplate<String>() {
		private static final long serialVersionUID = 8377599759041839826L;

		@Override
		public int compare(final String o1, final String o2) {
			return o1.length() - o2.length();
		}
	};

	@SuppressWarnings("unchecked")
	public static <T> Comparator<T> suite(final Comparator<? super T> c) {
		return (Comparator<T>) c;
	}

	/**
	 * 为一种已经实现了Comparable接口的类提供的Comparator,按照其compareTo方法所定进行比较.有的时候会用到.
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> ComparatorWithEquivalent<T> comparableComparator() {
		return (ComparatorWithEquivalent<T>) COMPARABLE_COMPARATOR;
	}

	/**
	 * 一个随机的比较器.慎用!建议即使要让前端展示给人以随机的感觉,也要控制其在后端是有序的,即给予前端一个伪随机的结果,而不是用这个comparator
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ComparatorWithEquivalent<T> randomComparator() {
		return (ComparatorWithEquivalent) RANDOM_COMPARATOR;
	}
}
