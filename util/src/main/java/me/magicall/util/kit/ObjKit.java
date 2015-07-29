package me.magicall.util.kit;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import me.magicall.util.ObjectUtil;

public class ObjKit extends Kit<Object> {

	private static final Object EMPTY_VALUE = new Object();
	private static final String[] SHORT_NAMES = { "obj" };
	//------------------------------------------
	public static final ObjKit INSTANCE = new ObjKit();

	//------------------------------------------
	private ObjKit() {
		super(Object.class, EMPTY_VALUE, SHORT_NAMES);
	}

	//------------------------------------------
	@SuppressWarnings("unchecked")
	public <T> T forceSuit(final Object o) {
		return (T) o;
	}

	@Override
	public Object fromString(final String source) {
		return source;
	}

	@Override
	public boolean isEmpty(final Object source) {
		return source == null;
	}

	@Override
	public boolean inGreaterRange(final Object source, final Object target) {
		return false;
	}

	@Override
	public boolean inGreaterEqualsRange(final Object source, final Object target) {
		return source == target;
	}

	/**
	 * 判断一个对象是不是数组
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isArray(final Object obj) {
		return obj != null && obj.getClass().isArray();
	}

	/**
	 * 把一个身份不明的疑似数组的对象搞成一个数组.
	 * 
	 * @param source
	 * @return 如果参数为null,返回null
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] toObjectArray(final Object source) {
		if (source instanceof Object[]) {
			return (T[]) source;
		}
		if (source == null) {
			return null;
		}
		final Class<?> c = source.getClass();
		if (!c.isArray()) {
			throw new IllegalArgumentException("Source is not an array: " + source);
		}
		final int length = Array.getLength(source);
		final Class<?> cc = c.getComponentType();
		if (length == 0) {
			return (T[]) Array.newInstance(cc, length);
		}
		final Object[] newArray = (Object[]) Array.newInstance(cc, length);
		for (int i = 0; i < length; i++) {
			newArray[i] = Array.get(source, i);
		}
		return (T[]) newArray;
	}

	public String deepToString(final Object obj) {
		if (obj == null) {
			return "null";
		}
		return deepToString(obj, new StringBuilder(), new HashSet<>()).toString();
	}

	StringBuilder deepToString(final Object obj, final StringBuilder sb, final Set<Object> escape) {
		if (obj == null) {
			return sb.append("null");
		}
		if (escape.contains(obj)) {
			return sb.append("...");
		}

		final Class<?> c = obj.getClass();
		if (c.isArray()) {//需要对数组每一个元素进行toString
			escape.add(obj);
			sb.append('[');
			final int len = Array.getLength(obj);
			for (int i = 0; i < len; ++i) {
				if (i != 0) {
					sb.append(',');
				}
				deepToString(Array.get(obj, i), sb, escape);
			}
			sb.append(']');
		} else if (obj instanceof Iterable<?>) {//需要对可遍历类每一个元素进行toString
			escape.add(obj);
			final Iterable<?> iterable = (Iterable<?>) obj;
			for (final Object o : iterable) {
				deepToString(o, sb, escape);
			}
		} else if (obj instanceof Map<?, ?>) {
			escape.add(obj);
			final Map<?, ?> m = (Map<?, ?>) obj;
			sb.append('{');
			if (!m.isEmpty()) {
				for (final Entry<?, ?> e : m.entrySet()) {
					deepToString(e.getKey(), sb, escape).append(" = ");
					deepToString(e.getValue(), sb, escape).append(',');
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append('}');
		} else {
			sb.append(obj);
		}
		return sb;
	}

	/**
	 * <pre>
	 * 深度比较两个对象是否相等.
	 * tips:
	 * 【java.utils.Arrays.deepEquals没有解决两个数组互相引用的问题,该问题会引发一个死循环(java.lang.StackOverflowError)
	 * 此方法会对这种情况返回false
	 * 示例:	
	 * 	 	Object[] o1 = { null };	Object[] o2 = { null };
	 * 		o1[0] = o2;		o2[0] = o1;
	 * 		System.out.print(ObjUtil.OBJ.deepEquals(o1,o2);
	 * 		System.out.print(Arrays.deepEquals(o1, o2));】
	 * </pre>
	 * 
	 * @author lwj
	 * @param o1
	 * @param o2
	 * @return
	 */
	public boolean deepEquals(final Object o1, final Object o2) {
		return ObjectUtil.deepEquals(o1, o2);
	}

	//======================================================
	private Object readResolve() {
		return INSTANCE;
	}

	private static final long serialVersionUID = -4115942060138299541L;

	//=======================================================
	public static void main(final String... args) {
		final Object[] o1 = { null };
		final Object[] o2 = { null };
		final Object[] o3 = { null };
		o1[0] = o2;
		o2[0] = o3;
		o3[0] = o1;
		System.out.println(Arrays.deepToString(o1));
		System.out.println(Arrays.deepToString(o2));
		System.out.println(Arrays.deepToString(o3));
		System.out.println(INSTANCE.deepToString(o1));
		System.out.println(INSTANCE.deepToString(o2));
		System.out.println(INSTANCE.deepToString(o3));

		o1[0] = o1;
		System.out.println(Arrays.deepToString(o1));
		System.out.println(INSTANCE.deepToString(o1));

		final List<Object> list = new ArrayList<>(1);
		o1[0] = list;
		list.add(o1);
		System.out.println(Arrays.deepToString(o1));
		System.out.println(list);
		System.out.println(INSTANCE.deepToString(o1));
		System.out.println(INSTANCE.deepToString(list));

//		System.out.println(ArrayUtil.deepEquals(o1, o2));
//		System.out.print(Arrays.deepEquals(o1, o2));

//		final String[] ss = { "vip.biz", "music-search", "result" };
//		final String[] ss2 = { "vip.biz", "music-search", "result" };
//		System.out.println("@@@@@@" + ArrayUtil.deepEquals(ss, ss2));
//		System.out.println("@@@@@@" + INSTANCE.deepToString(ss));
//		final int[] is = { 1, 2, 3, 4 };
//		System.out.println("@@@@@@" + INSTANCE.deepToString(is));
	}

}
