/*
 * Copyright 2002-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.magicall.coll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;

import me.magicall.util.kit.Kits;

/**
 * Miscellaneous collection utility methods. Mainly for internal use within the
 * framework.
 * 
 * @version 0925
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @since 1.1.3 整合文剑 原来的<code>CollectionUtil</code>类。
 * @author mingshi.pang
 * @since 1.1.4
 */
public class CollectionUtil {

	@SafeVarargs
	public static <T> int addAll(final Collection<T> coll, final T... objs) {
		int i = 0;
		for (final T e : objs) {
			if (coll.add(e)) {
				i++;
			}
		}
		return i;
	}

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> subCollection(final Collection<T> collection, final int offset, final int limit) {
		if (collection == null || collection.isEmpty()) {
			return collection;
		}

		int start = offset;
		if (start > collection.size()) {
			start = collection.size();
		}

		int end = start + limit;
		if (end > collection.size()) {
			end = collection.size();
		}
		final Collection<T> returnCollection = new ArrayList<>();
		final T[] srcArray = (T[]) collection.toArray();

		for (int i = start; i < end; i++) {
			returnCollection.add(srcArray[i]);
		}
		return returnCollection;
	}

	/**
	 * 检验一个集合引用是不是null或没有元素
	 * 
	 * @author apache
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(final Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * 检验一个map是不是null或没有元素
	 * 
	 * @author apache
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(final Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 判断一个对象是否存在一个可迭代里面.
	 * <p>
	 * 这个方法与Collection.contains(Object)不同,若元素为数组,本方法将对比数组的每一个元素.
	 * </p>
	 * (元素完全相同的两个数组的eqauls方法返回false)
	 * 
	 * @author apache,lwj
	 * @param iterable
	 * @param element
	 * @return
	 */
	public static boolean contains(final Iterable<?> iterable, final Object element) {
		final Iterator<?> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			final Object candidate = iterator.next();
			if (Kits.OBJ.deepEquals(candidate, element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 看看该Object是否存在于Collection里.比较时用==而非equals
	 * 
	 * @author apache
	 * @param collection
	 * @param element
	 * @return
	 */
	public static boolean containsInstance(final Collection<?> collection, final Object element) {
		if (collection != null) {
			for (final Object candidate : collection) {
				if (candidate == element) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 只要参数1的Collection包含任意一个参数2Collection的元素,即返回true
	 * 
	 * @author apache
	 * @param source
	 * @param candidates
	 * @return
	 */
	public static boolean containsAny(final Collection<?> source, final Collection<?> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) {
			return false;
		}
		for (final Object candidate : candidates) {
			if (source.contains(candidate)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回参数2Collection里第一个也存在于参数1Collection的元素
	 * 
	 * @author apache
	 * @param source
	 * @param candidates
	 * @return
	 */
	public static Object findFirstMatch(final Collection<?> source, final Collection<?> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) {
			return null;
		}
		for (final Object candidate : candidates) {
			if (source.contains(candidate)) {
				return candidate;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static final ElementTransformer NO_TRANS = (index, from) -> from;

	/**
	 * 不变形的变形器.调用它的transform将返回参数对象本身
	 * 
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> ElementTransformer<T, T> noTrans() {
		return NO_TRANS;
	}

//	/**
//	 * 从一个类型为F的源集合中根据filter筛选合适的元素,并将其按transer转化成合适的目标元素的集合
//	 * 
//	 * @param <F>源集合的元素类型
//	 * @param <T>返回集合的元素类型
//	 * @param source
//	 *            源集合
//	 * @param filter
//	 *            源集合元素过滤器.符合过滤器条件的元素才会被处理.如果不需要过滤,可传null
//	 * @param transformer
//	 *            对象转换器.
//	 * @param sorter
//	 *            排序器
//	 * @param offset
//	 *            从源集合第几个符合条件的元素开始(即"本页"之前的元素们)
//	 * @param size
//	 *            每页大小
//	 * @return 一个Subcollection对象.包括"该页"的元素集合,所有满足条件的元素的数量(用于分页),以及本页的开始index
//	 *         ,结束的index
//	 */
//	public static <F, T> Subcollection<T> getAcceptSubcollectionAndCount(final Collection<F> source,//
//			final ElementFilter<? super F> filter, final ElementTransformer<? super F, ? extends T> transformer,//
//			final Comparator<? super T> sorter, int offset, int size) {
//		if (source == null) {
//			return new ResultView<T>(null);
//		}
//		if (offset < 0) {
//			offset = 0;
//		}
//		if (size < 0) {
//			size = source.size();
//		}
//
//		int escaped = 0;
//		int allCount = 0;
//		Collection<T> c = null;
//		int index = 0;
//		final boolean sort = sorter != null;
//		for (final F e : source) {
//			if (filter == null || filter.accept(index, e)) {
//				if (sort || escaped >= offset && allCount - escaped < size) {
//					if (c == null) {
//						if (sort) {
//							c = new TreeSet<T>(sorter);
//						} else {
//							c = new ArrayList<T>(size);
//						}
//					}
//					c.add(transformer.apply(index, e));
//				} else if (escaped < offset) {
//					escaped++;
//				}
//				allCount++;
//			}
//			index++;
//		}
//		if (c == null) {
//			c = Collections.emptyList();
//		} else if (sort) {
//			final List<T> l = new ArrayList<T>(c);
//			int to = offset + size;
//			final int s = l.size();
//			if (to > s) {
//				to = s;
//			}
//			c = Collections.unmodifiableCollection(Utils.LIST.subList(l, offset, to));
//		}
//		final Subcollection<T> rt = new ResultView<T>(c);
//		rt.setTheNumbers(allCount, offset, size);
//		return rt;
//	}

//	/**
//	 * 从一个类型为F的源集合中根据filter筛选合适的元素,并将其按transer转化成合适的目标元素的集合 ?
//	 * 改名字叫collectionTransform吧
//	 * 
//	 * @param <F>源集合的元素类型
//	 * @param <T>返回集合的元素类型
//	 * @param source
//	 *            源集合
//	 * @param transformer
//	 *            对象转换器.
//	 * @param offset
//	 *            从源集合第几个符合条件的元素开始(即"本页"之前的元素们)
//	 * @param size
//	 *            每页大小
//	 * @return 一个Subcollection对象.包括"该页"的元素集合,所有满足条件的元素的数量(用于分页),以及本页的开始index
//	 *         ,结束的index
//	 */
//	public static <F, T> Subcollection<T> getAcceptSubcollectionAndCount(final Collection<F> source, final ElementTransformer<F, T> transformer,
//			final int offset, final int size) {
//		return getAcceptSubcollectionAndCount(source, null, transformer, null, offset, size);
//	}
//
//	/**
//	 * 从源集合中根据filter筛选合适的元素,并将其按transer转化成合适的目标元素的集合,将其追加到目标集合内
//	 * 
//	 * @param <F>
//	 *            源集合的元素类型
//	 * @param <T>
//	 *            目标集合的元素类型
//	 * @param <R>
//	 *            (目标的集合)的类型
//	 * @param source
//	 *            源集合
//	 * @param target
//	 *            目标集合
//	 * @param filter
//	 *            源集合元素过滤器.符合过滤器条件的元素才会被处理.如果不需要过滤,可传null
//	 * @param transformer
//	 *            对象转换器.
//	 * @param offset
//	 *            从源集合第几个符合条件的元素开始(即"本页"之前的元素们)
//	 * @param size
//	 *            每页大小
//	 * @return
//	 */
//	public static <F, T> void getAcceptSubcollection(final Collection<? extends F> source,//
//			final Collection<? super T> target, final ElementFilter<? super F> filter,//
//			final ElementTransformer<? super F, ? extends T> transformer, final int offset, final int size) {
//		handleCollectionElement(source, filter, new CollectionTransformCopy<F, T>(target, transformer), offset, size);
//	}

//	/**
//	 * 从源集合中根据filter筛选合适的元素,将其追加到目标集合内
//	 * 
//	 * @param <T>
//	 *            目标集合的元素类型
//	 * @param source
//	 *            源集合
//	 * @param target
//	 *            目标集合
//	 * @param filter
//	 *            源集合元素过滤器.符合过滤器条件的元素才会被处理.如果不需要过滤,可传null
//	 * @param offset
//	 *            开始处理的元素的下标.(即"本页"之前的元素们)
//	 * @param size
//	 *            处理元素的个数.
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static <T, F extends T> void getAcceptSubcollection(final Collection<F> source,//
//			final Collection<F> target, final ElementFilter<? super T> filter, final int offset, final int size) {
//		getAcceptSubcollection(source, target, filter, NO_TRANS, offset, size);
//	}

	/**
	 * 处理一个集合中的每个元素.本方法是框架方法,仅提供比较简单的遍历,不过很多情况下够用了.
	 * 
	 * @param <E>
	 * @param source
	 *            源集合.
	 * @param filter
	 *            过滤器.只有符合过滤器的元素才会被处理.若过滤器为null,则处理所有元素.
	 * @param excutor
	 *            元素操作者.详参此接口本身.
	 * @param offset
	 *            开始处理的元素的下标.(即"本页"之前的元素们)
	 * @param size
	 *            处理元素的个数.
	 */
	public static <E> void handleCollectionElement(final Collection<? extends E> source,//
			final ElementFilter<? super E> filter, final ElementHandler<E> excutor, int offset, int size) {
		if (source == null) {
			return;
		}
		if (offset < 0) {
			offset = 0;
		}
		if (size < 0) {
			size = source.size();
		}
		int escaped = 0;
		int allCount = 0;
		int index = 0;
		for (final E element : source) {
			if (filter == null || filter.accept(index, element)) {
				if (escaped < offset) {
					escaped++;
				} else if (allCount - escaped < size) {
					excutor.execute(index, element);
				} else {
					break;
				}
				allCount++;
			}
			index++;
		}
	}

	// /**
	// * @author 天舟
	// * @param <K>
	// * @param <V>
	// * @param <K1>
	// * @param <V1>
	// * @param source
	// * @return
	// * @deprecated Use {@link MapUtil#mapExtends(Map<K1, Set<V1>>)} instead
	// */
	// @Deprecated
	// public static <K, V, K1 extends K, V1 extends V> Map<K, Set<V>>
	// mapExtends(Map<K1, Set<V1>> source) {
	// return MapUtil.<K, V, K1, V1> mapExtends(source);
	// }

	/**
	 * @author 天舟
	 * @param <T>
	 * @param <T1>
	 * @param source
	 * @return
	 */
	public static <T, T1 extends T> Collection<T> collectionExtends(final Collection<T1> source) {
		return new ArrayList<>(source);
	}

	@SuppressWarnings("unchecked")
	public static <T, T1 extends T> Collection<T1> collectionSuper(final Collection<T> source) {
		return new ArrayList(source);
	}

//	/**
//	 * @author 天舟
//	 * @param <T>
//	 * @param <T1>
//	 * @param source
//	 * @return
//	 */
//	public static <T, T1 extends T> ResultView<T> resultViewExtends(final ResultView<T1> source) {
//		final ResultView<T> rv = new ResultView<T>();
//		rv.setList(CollectionUtil.<T, T1> collectionExtends(source.getList()));
//		rv.setTheNumbers(source.getCount(), source.getStart() - 1, source.getPagesize());
//		return rv;
//	}
//
//	public static <T, T1 extends T> ResultView<T1> resultViewSuper(final ResultView<T> source) {
//		final ResultView<T1> rv = new ResultView<T1>();
//		rv.setList(CollectionUtil.<T, T1> collectionSuper(source.getList()));
//		rv.setTheNumbers(source.getCount(), source.getStart() - 1, source.getPagesize());
//		return rv;
//	}

	/**
	 * 将一个枚举的所有值放到一个Collection里,该Collection不可变
	 * 
	 * @param <E>
	 * @param clazz
	 * @return
	 */
	public static <E extends Enum<E>> Collection<E> allEnumView(final Class<E> clazz) {
		return Collections.unmodifiableCollection(EnumSet.allOf(clazz));
	}

	/**
	 * 将一个枚举里的某些值放到Collection里,该Collection不可变
	 * 
	 * @param <E>
	 * @param first
	 * @param enums
	 * @return
	 */
	@SafeVarargs
	public static <E extends Enum<E>> Collection<E> enumView(final E first, final E... enums) {
		return Collections.unmodifiableCollection(EnumSet.of(first, enums));
	}

	/**
	 * 与Collection本身的equals不同:对于元素中有数组的,可以拆开数组判断每个元素是否相等
	 * 
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static boolean equals(final Iterable<?> i1, final Iterable<?> i2) {
		if (i1 == i2) {
			return true;
		}
		if (i1.equals(i2)) {
			return true;
		}
		if (i1 instanceof Collection<?> && i2 instanceof Collection<?>) {
			final int s1 = ((Collection<?>) i1).size();
			final int s2 = ((Collection<?>) i2).size();
			if (s1 != s2) {
				return false;
			}
		}
		final Iterator<?> it1 = i1.iterator();
		final Iterator<?> it2 = i2.iterator();
		while (it1.hasNext() && it2.hasNext()) {
			final Object o1 = it1.next();
			final Object o2 = it2.next();
			if (!Kits.OBJ.deepEquals(o1, o2)) {
				return false;
			}
		}
		return !it1.hasNext() && !it2.hasNext();
	}

}
