/**
 * 
 */
package me.magicall.coll.util;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.empty.EmptyColl;
import me.magicall.coll.transformed.TransformedCollection;
import me.magicall.coll.wrap.UnmodifiableWrapCollection;
import me.magicall.util.kit.Kits;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class CollectionKit extends AbsCollectionKit<Collection<?>> {

	private static final Class<?> MAIN_CLASS = Collection.class;
	private static final String[] SHORT_NAMES = { "coll" };

	//-----------------------------------------------------

	public static final CollectionKit INSTANCE = new CollectionKit();

	//-----------------------------------------------------

	@SuppressWarnings("unchecked")
	private CollectionKit() {
		super((Class<Collection<?>>) MAIN_CLASS, EmptyColl.INSTANCE, SHORT_NAMES);
	}

	//-----------------------------------------------------

	//这些方法覆盖父类方法,是为了返回值能带上泛型
	@SuppressWarnings("unchecked")
	@Override
	public <E, E1> Collection<E> emptyValue() {
		return (Collection) EmptyColl.INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> forceSuit(final Collection<?> source) {
		return (Collection<T>) source;
	}

	//-----------------------------------------------------

	/**
	 * 从集合中随机取出一个元素.
	 * 
	 * @param <T>
	 * @param source
	 * @return 随机取出来的一个元素.如果集合为空,则返回null
	 */
	public <T> T randomOne(final Collection<T> source) {
		if (source instanceof List<?>) {
			return Kits.LIST.randomOne((List<T>) source);
		}
		if (isEmpty(source)) {
			return null;
		}
		final int index = RANDOM.nextInt(source.size());
		final Iterator<T> it = source.iterator();
		for (int i = 0; i < index; i++) {
			it.next();
		}
		return it.next();
	}

	/**
	 * 从源集合中随机选取size个元素组成一个新的集合。如果size大于或等于source的元素个数，则直接返回source，此时要注意对返回集合的修改操作会影响源集合
	 * TODO：尚未实现
	 * 
	 * @param <T>
	 * @param source
	 * @param size
	 * @return
	 */
	public <T> Collection<T> randomSub(final Collection<T> source, final int size) {
		if (source.size() <= size) {
			return source;
		}

		return null;
	}

	/**
	 * 将一个子类元素的集合强转成一个父类元素的集合.这是安全的,但jvm不认识.用本方法可以少写很多强转的恶心代码
	 * 
	 * @param <T>
	 * @param <T1>
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T, T1 extends T> Collection<T> suit(final Collection<T1> source) {
		return (Collection<T>) source;
	}

	/**
	 * 通过一个ElementTransformer，将一个源集合转变成另一个集合。结果集的元素与源集合的元素存在一一对应关系，因此元素个数是相等的。结果集每一个元素都是源集合对应位置上元素经过一种函数变换得到的，这种函数变换即是由transformer参数所指定。
	 * 注：返回的是一种视图集合，是不可修改的。
	 * 
	 * @param <F>
	 * @param <T>
	 * @param source
	 * @param transformer
	 * @return
	 */
	public <F, T> Collection<T> transform(final Collection<? extends F> source, final ElementTransformer<? super F, ? extends T> transformer) {
		return new TransformedCollection<>(source, transformer);
	}

	/**
	 * 从集合中取出"最大"的元素.比较大小的方式由comparator参数定义
	 * 
	 * @param <E>
	 * @param source
	 * @param comparator
	 * @return
	 */
	public <E> E maxElement(final Collection<E> source, final Comparator<? super E> comparator) {
		if (isEmpty(source)) {
			return null;
		}
		final Iterator<E> i = source.iterator();
		E rt = i.next();
		while (i.hasNext()) {
			final E e = i.next();
			if (comparator.compare(rt, e) < 0) {
				rt = e;
			}
		}
		return rt;
	}

	/**
	 * 从集合中取出"最小"的元素.比较大小的方式由comparator参数定义
	 * 
	 * @param <E>
	 * @param source
	 * @param comparator
	 * @return
	 */
	public <E> E minElement(final Collection<E> source, final Comparator<? super E> comparator) {
		return maxElement(source, Collections.reverseOrder(comparator));
	}

	/**
	 * 将源集合包装为一个不可修改的集合对象
	 * 
	 * @param <E>
	 * @param coll
	 * @return
	 */
	public <E> Collection<E> unmodifiable(final Collection<E> coll) {
		if (isUnmodifiable(coll)) {
			return coll;
		}
		return new UnmodifiableWrapCollection<>(coll);
	}

	//====================================================

	private static final long serialVersionUID = -8028661636852704700L;

	private Object readResolve() {
		return INSTANCE;
	}

}