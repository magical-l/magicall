/**
 * 
 */
package me.magicall.coll.util;

import static me.magicall.consts.CommonConst.NOT_FOUND_INDEX;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import me.magicall.tagInterfaces.Unmodifiable;
import me.magicall.util.comparator.Equivalent;
import me.magicall.util.comparator.EquivalentUtil;
import me.magicall.util.kit.Kits;
import me.magicall.util.kit.WithSubclassKit;

public abstract class AbsCollectionKit<C extends Collection<?>> //
		extends WithSubclassKit<C> {

	private static final long serialVersionUID = -6957766564775638664L;

	/**
	 * <code>java.util.Collections.UnmodifiableCollection</code>的Class对象，由于该类不可见，只能通过这个方法获得它的Class对象
	 */
	protected static final Class<?> JDK_UNMODIFIABLE_COLLECTION_CLASS//
	= Collections.unmodifiableCollection(Collections.emptyList()).getClass();

	//-------------------------------------
	AbsCollectionKit(final Class<C> mainClass, final C emptyValue, final String... shortNames) {
		super(mainClass, emptyValue, shortNames);
	}

	AbsCollectionKit(final Class<C> mainClass, final C emptyValue) {
		super(mainClass, emptyValue);
	}

	//--------------------------------------

	public <E> int elementCount(final E target, final Collection<E> source) {
		int count = 0;
		for (final E e : source) {
			if (Kits.OBJ.deepEquals(e, target)) {
				count++;
			}
		}
		return count;
	}

	public <E> void append(final Collection<E> source, final E... elements) {
		if (elements == null) {
			return;
		}
		for (final E e : elements) {
			source.add(e);
		}
	}

	public <E, T extends E> int findIndex(final Collection<E> source, final T target, final Comparator<? super T> comparator) {
		return findIndex(source, target, EquivalentUtil.comparatorEquivalent(comparator));
	}

	public <E, T extends E> int findIndex(final Collection<E> source, final T target, final Equivalent<? super T> equivalent) {
		final Class<?> targetClass = target.getClass();
		int index = 0;
		for (final E e : source) {
			if (targetClass.isAssignableFrom(e.getClass())) {
				@SuppressWarnings("unchecked")
				final T t = (T) e;
				if (equivalent.equals(t, target)) {
					return index;
				}
			}
			++index;
		}
		return NOT_FOUND_INDEX;
	}

	public <E, T extends E> int findInstanceIndex(final Collection<E> source, final T target) {
		return findIndex(source, target, EquivalentUtil.<T> instanceEquivalent());
	}

	public <E, T extends E> int findIndex(final Collection<E> source, final T target) {
		return findIndex(source, target, EquivalentUtil.<T> deepEqualsEquivalent());
	}

	public <E, T extends E> T find(final Collection<E> source, final T target, final Comparator<? super T> comparator) {
		return find(source, target, EquivalentUtil.<T> comparatorEquivalent(comparator));
	}

	public <E, T extends E> T find(final Collection<E> source, final T target, final Equivalent<? super T> equivalent) {
		final Class<?> targetClass = target.getClass();
		for (final E e : source) {
			if (targetClass.isAssignableFrom(e.getClass())) {
				@SuppressWarnings("unchecked")
				final T t = (T) e;
				if (equivalent.equals(t, target)) {
					return t;
				}
			}
		}
		return null;
	}

	public <E, T extends E> T find(final Collection<E> source, final T target) {
		return find(source, target, EquivalentUtil.<T> deepEqualsEquivalent());
	}

	public <E, T extends E> boolean contains(final Collection<E> source, final T target, final Comparator<? super T> comparator) {
		return findIndex(source, target, comparator) > NOT_FOUND_INDEX;
	}

	public <E, T extends E> boolean contains(final Collection<E> source, final T target, final Equivalent<? super T> equivalent) {
		return findIndex(source, target, equivalent) > NOT_FOUND_INDEX;
	}

	public <E, T extends E> boolean contains(final Collection<E> source, final T target) {
		return findIndex(source, target) > NOT_FOUND_INDEX;
	}

	/**
	 * 判断一个Collection是否为“空”。空的定义为null或没有元素
	 */
	@Override
	public boolean isEmpty(final C source) {
		return source == null || source.isEmpty();
	}

	/**
	 * 检测target是否source的真子集
	 */
	@Override
	public boolean inGreaterRange(final C source, final C target) {
		return source.size() > target.size() && source.containsAll(target);
	}

	/**
	 * 检测target是否source的子集
	 */
	@Override
	public boolean inGreaterEqualsRange(final C source, final C target) {
		return source.size() >= target.size() && source.containsAll(target);
	}

	/**
	 * <pre>
	 * 判断一个集合是否是"不可修改"的
	 * 不可修改的定义(以下中的任一个):
	 * 1,java.util.Collections.EMPTY_LIST/EMPTY_SET,或其对应的emptyList()/emptySet()方法返回的对象
	 * 2,java.util.Collections.unmodifiableCollection()/unmodifiableList()/unmodifiableSet()返回的对象
	 * 3,实现了com.xiaonei.vip.commons.util.collections.Unmodifiable接口的Collection子类
	 * </pre>
	 * 
	 * @param coll
	 * @return
	 */
	public boolean isUnmodifiable(final C coll) {
		return coll instanceof Unmodifiable//用我写的unmodifiable collection系列，都实现了这个接口
				|| coll == Collections.EMPTY_LIST//jdk原生的一个空的不可修改list
				|| coll == Collections.EMPTY_SET//jdk原生的一个空的不可修改set
				|| JDK_UNMODIFIABLE_COLLECTION_CLASS.isAssignableFrom(coll.getClass());
	}

	/**
	 * 将一个来历不明的集合的泛型强转成另一种类型的集合泛型.
	 * 注意:这是"非安全的"，适用场合：当使用者很清楚地确定source（是一个Collection<S>）中的S是T的父类，用此方法帮助做强制类型转换，可少写很多恶心代码
	 * 
	 * @param <T>
	 * @param source
	 * @return
	 */
	public <T> C forceSuit(final C source) {
		return source;
	}
}