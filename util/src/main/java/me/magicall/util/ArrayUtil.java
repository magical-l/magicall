/**
 * Magical Liang
 * 2009-3-7 下午02:36:18
 */
package me.magicall.util;

import me.magicall.coll.CollFactory.L;
import me.magicall.coll.ElementFilter;
import me.magicall.coll.ElementHandler;
import me.magicall.util.kit.Kits;
import me.magicall.util.kit.PrimitiveKit;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static me.magicall.consts.CommonConst.NOT_FOUND_INDEX;

/**
 * <pre>
 * </pre>
 * 
 * @author Magical Liang
 */
public class ArrayUtil {

	/**
	 * 返回某个类的指定维数的数组的Class对象(Class<T[]>对象)
	 * 注意:低维数组并非高维数组的父类!（String[]不是String[][]的父类）
	 * 
	 * @param componentType
	 * @param dim
	 * @return
	 */
	public static Class<?> arrClass(final Class<?> componentType, final int dim) {
		return ClassUtil.arrClass(componentType, dim);
	}

	public static Object nDimEmptyArr(final Class<?> componentType, final int dim) {
		return nDimArr(componentType, dim, 0);
	}

	public static Object nDimArr(final Class<?> componentType, final int dim, final int size) {
		if (dim < 1) {
			throw new IllegalArgumentException();
		}
		return Array.newInstance(dim == 1 ? componentType : arrClass(componentType, dim - 1), size);
	}

	/**
	 * 返回数组的元素的class(对多维数组适用)
	 * 
	 * @param arrClass
	 * @return
	 */
	public static Class<?> componentType(final Class<?> arrClass) {
		return ClassUtil.componentType(arrClass);
	}

	public static int dimOfArrayClass(final Class<?> arrClass) {
		return ClassUtil.dimOfArrayClass(arrClass);
	}

	public static List<Object> asList(final Object array) {
		final Class<?> c = array.getClass();
		if (c.isArray()) {
			final int len = Array.getLength(array);
			if (len == 0) {
				return Kits.LIST.emptyValue();
			} else {
				final List<Object> rt = new ArrayList<>(len);
				for (int i = 0; i < len; ++i) {
					rt.add(Array.get(array, i));
				}
				return rt;
			}
		} else {
			return L.asList(array);
		}
	}

	@SafeVarargs
	public static <T> int elementCount(final T target, final T... source) {
		return Kits.COLL.elementCount(target, Arrays.asList(source));
	}

	/**
	 * 使用==在数组中查找元素，如果找到，返回它的下标，否则返回-1
	 * 
	 * @param <T>
	 * @param target
	 * @param source
	 * @return
	 */
	@SafeVarargs
	public static <T> int findInstance(final T target, final T... source) {
		int index = 0;
		for (final T t : source) {
			if (t == target) {
				return index;
			}
			index++;
		}
		return NOT_FOUND_INDEX;
	}

	/**
	 * @param <T>
	 * @param target
	 * @param source
	 * @return
	 */
	@SafeVarargs
	public static <T> int find(final T target, final T... source) {
		int index = 0;
		if (target == null) {
			for (final T t : source) {
				if (t == null) {
					return index;
				}
				index++;
			}
		} else {
			for (final T t : source) {
				if (target.equals(t)) {
//				if(ObjectUtil.deepEquals(target, t)){
					return index;
				}
				index++;
			}
		}
		return NOT_FOUND_INDEX;
	}

	@SafeVarargs
	public static <T> void forEach(final ElementFilter<? super T> filter, final ElementHandler<? super T> handler,
			final T... array) {
		if (array == null || handler == null) {
			return;
		}
		if (filter == null) {
			final int i = 0;
			for (final T t : array) {
				handler.execute(i, t);
			}
		} else {
			final int i = 0;
			for (final T t : array) {
				if (filter.accept(i, t)) {
					handler.execute(i, t);
				}
			}
		}
	}

	/**
	 * 返回参数本身。本方法接受可变长度参数列表，因此可以快速将一堆单个元素组合成一个数组而无需显式地new一个T[]
	 * 
	 * @param <T>
	 * @param ts
	 * @return
	 */
	@SafeVarargs
	public static <T> T[] asArray(final T... ts) {
		return ts;
	}

	/**
	 * <pre>
	 * 深度比较两个数组是否相等.
	 * tips:
	 * 【java.utils.Arrays.deepEquals没有解决两个数组互相引用的问题,该问题会引发一个死循环(java.lang.StackOverflowError)
	 * 此方法会对这种情况返回false
	 * 示例:		Object[] o1 = { null };
	 * 		Object[] o2 = { null };
	 * 		o1[0] = o2;
	 * 		o2[0] = o1;
	 * 		System.out.print(ObjUtil.OBJ.deepEquals(o1,o2);
	 * 		System.out.print(Arrays.deepEquals(o1, o2));】
	 * </pre>
	 * 
	 * @return
	 */
	public static boolean deepEquals(final Object[] a1, final Object... a2) {
		if (a1 == a2) {
			return true;
		}
		if (a1 == null || a2 == null) {
			return false;
		}
		return deepEquals(a1, a2, null);
	}

	static boolean deepEquals(final Object[] a1, final Object[] a2, final Set<Object> escape) {
		if (escape != null && (escape.contains(a1) || escape.contains(a2))) {
			return false;
		}
		//检查长度
		final int length = a1.length;
		if (a2.length != length) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			if (!ObjectUtil.deepEquals(a1[i], a2[i], escape)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 返回数组的一个子集，从fromIndex（包含）开始到最后。
	 * 
	 * @param <T>
	 * @param fromIndex
	 * @param source
	 * @return
	 */
	@SafeVarargs
	public static <T> T[] sub(final int fromIndex, final T... source) {
		if (source == null) {
			return null;
		}
		return sub(fromIndex, source.length, source);
	}

	/**
	 * 返回数组的一个子集，从fromIndex（包含）开始，到toIndex（不包含）结束。
	 * 能对fromIndex和toIndex进行纠错
	 * 
	 * @param <T>
	 * @param fromIndex
	 * @param toIndex
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] sub(final int fromIndex, final int toIndex, final T... source) {
		if (source == null) {
			return null;
		}
		final Class<?> type = source.getClass().getComponentType();
		if (fromIndex == toIndex) {
			return (T[]) Array.newInstance(type, 0);
		}
		//调整fromIndex和toIndex的大小关系
		final int s, e;
		if (fromIndex < toIndex) {
			s = fromIndex;
			e = toIndex;
		} else {
			s = toIndex;
			e = fromIndex;
		}
		final int len = e - s;
		final T[] subarray = (T[]) Array.newInstance(type, len);
		System.arraycopy(source, s, subarray, 0, len);
		return subarray;
	}

	public static boolean primitivesArrEquals(final Object e1, final Object e2) {
		//由调用者保证e1是一个原始类型数组
		final PrimitiveKit<?, Object> ps = Kits.valueOfPrimitive(e1.getClass().getComponentType().getName());
		return ps.arrEquals(e1, e2);
	}

	/**
	 * 在数组的指定区间中查找指定的实例(即采用==判断是否是同一实例)
	 * 
	 * @param target
	 *            要查找的实例
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param toIndex
	 *            到哪里结束(不包含)
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> int instanceIndexOf(final Object target, final int fromIndex, final int toIndex,
			final T... source) {
		if (source == null) {
			return NOT_FOUND_INDEX;
		}

		final int s, e;
		if (fromIndex <= toIndex) {
			s = fromIndex;
			e = toIndex;
		} else {
			s = toIndex;
			e = fromIndex;
		}
		final int f = Math.max(s, 0);
		final int t = Math.min(e, source.length);
		for (int i = f; i < t; ++i) {
			if (source[i] == target) {
				return i;
			}
		}
		return NOT_FOUND_INDEX;
	}

	/**
	 * 在数组的指定区间中查找指定的实例(即采用==判断是否是同一实例)
	 * 
	 * @param target
	 *            要查找的实例
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> int instanceIndexOf(final Object target, final int fromIndex, final T... source) {
		if (source == null) {
			return NOT_FOUND_INDEX;
		}
		return instanceIndexOf(target, fromIndex, source.length, source);
	}

	/**
	 * 在数组中查找指定的实例(即采用==判断是否是同一实例)
	 * 
	 * @param target
	 *            要查找的实例
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> int instanceIndexOf(final Object target, final T... source) {
		return instanceIndexOf(target, 0, source);
	}

	/**
	 * 在数组的指定区间查找指定的对象(使用deepEquals方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param toIndex
	 *            到哪里结束(不包含)
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> int indexOf(final Object target, final int fromIndex, final int toIndex, final T... source) {
		if (isEmpty(source)) {
			return NOT_FOUND_INDEX;
		}
		final int s, e;
		if (fromIndex <= toIndex) {
			s = fromIndex;
			e = toIndex;
		} else {
			s = toIndex;
			e = fromIndex;
		}
		final int f = Math.max(s, 0);
		final int t = Math.min(e, source.length);
		if (target == null) {
			for (int i = f; i < t; ++i) {
				if (source[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = f; i < t; ++i) {
				if (Kits.OBJ.deepEquals(target, source[i])) {
					return i;
				}
			}
		}
		return NOT_FOUND_INDEX;
	}

	/**
	 * 在数组的指定区间查找指定的对象(使用deepEquals方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> int indexOf(final Object target, final int fromIndex, final T... source) {
		if (source == null) {
			return NOT_FOUND_INDEX;
		}
		return indexOf(target, fromIndex, source.length, source);
	}

	/**
	 * 在数组中查找指定的对象(使用deepEquals方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> int indexOf(final Object target, final T... source) {
		return indexOf(target, 0, source);
	}

	/**
	 * 数组指定区间中是否包含指定对象(使用equals方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param toIndex
	 *            到哪里结束(不包含)
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean contains(final Object target, final int fromIndex, final int toIndex, final T... source) {
		return indexOf(target, fromIndex, toIndex, source) > NOT_FOUND_INDEX;
	}

	/**
	 * 数组指定区间中是否包含指定对象(使用equals方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean contains(final Object target, final int fromIndex, final T... source) {
		return indexOf(target, fromIndex, source) > NOT_FOUND_INDEX;
	}

	/**
	 * 数组中是否包含指定对象(使用deepEquals方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean contains(final Object target, final T... source) {
		return indexOf(target, source) > NOT_FOUND_INDEX;
	}

	/**
	 * 数组指定区间中是否包含指定对象(使用==方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param toIndex
	 *            到哪里结束(不包含)
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean containsInstance(final Object target, final int fromIndex, final int toIndex,
			final T... source) {
		return instanceIndexOf(target, fromIndex, toIndex, source) > NOT_FOUND_INDEX;
	}

	/**
	 * 数组指定区间中是否包含指定对象(使用==方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean containsInstance(final Object target, final int fromIndex, final T... source) {
		return instanceIndexOf(target, fromIndex, source) > NOT_FOUND_INDEX;
	}

	/**
	 * 数组中是否包含指定对象(使用==方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param source
	 *            源
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean containsInstance(final Object target, final T... source) {
		return instanceIndexOf(target, source) > NOT_FOUND_INDEX;
	}

	/**
	 * 反转数组中指定区间的元素顺序
	 * 
	 * @param source
	 *            源数组
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param toIndex
	 *            到哪里结束(不包含)
	 */
	public static <T> void reverse(final T[] source, final int fromIndex, final int toIndex) {
		if (source == null) {
			return;
		}
		final int s, e;
		if (fromIndex <= toIndex) {
			s = fromIndex;
			e = toIndex;
		} else {
			s = toIndex;
			e = fromIndex;
		}
		for (int i = s, j = e - 1; i < j; ++i, --j) {
			swap(source, i, j);
		}
	}

	/**
	 * 反转数组中的元素
	 * 
	 * @param source
	 *            源数组
	 * @param fromIndex
	 *            从哪里开始(包含)
	 */
	public static <T> void reverse(final T[] source, final int fromIndex) {
		if (source == null) {
			return;
		}
		reverse(source, fromIndex, source.length);
	}

	/**
	 * 反转数组中的元素
	 * 
	 * @param source
	 *            源数组
	 */
	@SafeVarargs
	public static <T> void reverse(final T... source) {
		reverse(source, 0);
	}

	/**
	 * 交换数组中两个元素的位置
	 * 
	 * @param <T>
	 * @param a
	 * @param index1
	 * @param index2
	 */
	private static <T> void swap(final T[] a, final int index1, final int index2) {
		final T o = a[index1];
		a[index1] = a[index2];
		a[index2] = o;
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean isEmpty(final T... array) {
		return array == null || array.length == 0;
	}

	/**
	 * 把一些元素组成一个list(可扩展的.Arrays.asList()是不可扩展的,不可add()的)
	 * 
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> asAddableList(final T... array) {
		if (array == null) {
			return new ArrayList<>();
		}
		final List<T> list = new ArrayList<>(array.length);
		Collections.addAll(list, array);
		return list;
	}

	/**
	 * 把一些元素包装成一个不可变的list
	 * 
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> asUnmodifiableList(final T... array) {
		return Collections.unmodifiableList(Arrays.asList(array));
	}

	/**
	 * 返回某个类的一维数组的Class对象
	 * 
	 * @param <T>
	 * @param componentType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T[]> arrClass(final Class<?> componentType) {
		return (Class<T[]>) arrClass(componentType, 1);
	}

	/**
	 * 返回某个类的一维数组（空数组）
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] emptyArray(final Class<?> clazz) {
		return (T[]) Array.newInstance(clazz, 0);
	}

	/**
	 * 从数组中找出最大值。比较方式由comparator指定
	 * 
	 * @param comparator
	 * @param source
	 * @return
	 */
	@SafeVarargs
	public static <T extends Comparable<T>> T max(final Comparator<? super T> comparator, final T... source) {
        return Collections.max(Arrays.asList(source), comparator);
    }

	/**
	 * 从数组中找出最大值，按照元素的自然顺序
	 * 
	 * @param <T>
	 * @param source
	 * @return
	 */
	@SafeVarargs
	public static <T extends Comparable<T>> T max(final T... source) {
		return Collections.max(Arrays.asList(source));
	}

	/**
	 * 从数组中找出最小值。比较方式由comparator指定
	 * 
	 * @param <T>
	 * @param comparator
	 * @param source
	 * @return
	 */
	@SafeVarargs
	public static <T> T min(final Comparator<? super T> comparator, final T... source) {
        return Collections.min(Arrays.asList(source), comparator);
    }

	/**
	 * 从数组中找出最小值，按元素的自然顺序
	 * 
	 * @param <T>
	 * @param source
	 * @return
	 */
	@SafeVarargs
	public static <T extends Comparable<T>> T min(final T... source) {
		return Collections.min(Arrays.asList(source));
	}
}
