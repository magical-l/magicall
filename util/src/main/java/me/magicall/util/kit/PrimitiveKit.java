package me.magicall.util.kit;

import me.magicall.coll.ElementNotNull;
import me.magicall.coll.sorted.Sorted;
import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;
import me.magicall.util.ArrayUtil;
import me.magicall.util.ClassUtil;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.RandomAccess;
import java.util.Set;

import static me.magicall.consts.CommonConst.NOT_FOUND_INDEX;
import static me.magicall.consts.StrConst.EMPTY_STR_ARR;

public abstract class PrimitiveKit<P, A> extends Kit<P> {

	public static void main(final String... args) {
		System.out.println("@@@@@@" + (Kits.INT.primitiveArrayClass() == int[].class));
	}

	private static final long serialVersionUID = -4645186921643553001L;
	//------------------------------------------
	private final A emptyPrimitiveArray;
	private final Class<?> primitiveClass;
	private final String primitiveArrFlag;
	private final Class<A> primitiveArrayClass;

	//-----------------------------------------
	@SuppressWarnings("unchecked")
	PrimitiveKit(final Class<P> mainClass, final P emptyValue, final String[] shortNames, final Class<?> primitiveClass, final String primitiveArrFlag) {
		super(mainClass, emptyValue, shortNames, primitiveClass);
		final A emptyPrimitiveArray = (A) Array.newInstance(primitiveClass, 0);

		this.primitiveClass = primitiveClass;
		this.primitiveArrFlag = primitiveArrFlag;
		this.emptyPrimitiveArray = emptyPrimitiveArray;
        primitiveArrayClass = (Class<A>) primitiveArrayClass(1);
	}

	PrimitiveKit(final Class<P> mainClass, final P emptyValue, final Class<?> primitiveClass, final String primitiveArrFlag) {
		this(mainClass, emptyValue, EMPTY_STR_ARR, primitiveClass, primitiveArrFlag);
	}

	//------------------------------------------

	public int elementCount(final P target, final A array) {
		return Kits.COLL.elementCount(target, asList(array));
	}

	public Class<?> getPrimitiveClass() {
		return primitiveClass;
	}

	/**
	 * 返回基本类型数组的Class对象.
	 * 注意:低维数组并非高维数组的父类!
	 * 即:一个int[][]对象 并非 一个int[]对象 (new int[][] instanceof new int[])==false
	 * int[].class.isAssignableFrom(int[][].class)==false
	 * 
	 * @param dim
	 * @return
	 */
	public Class<?> primitiveArrayClass(final int dim) {
		return ClassUtil.primitiveArrClass(this, dim);
	}

	public String getPrimitiveArrFlag() {
		return primitiveArrFlag;
	}

	public A newPrimitiveArray(final int size) {
		return size < 1 ? emptyPrimitiveArray() : newPrimitiveArray0(size);
	}

	public A emptyPrimitiveArray() {
		return emptyPrimitiveArray;
	}

	/**
	 * 返回一个长度为size的数组,所有元素均为指定值
	 * 
	 * @param size
	 *            长度
	 * @param value
	 *            指定值
	 * @return
	 */
	public A nPrimitiveCopy(final int size, final P value) {
		final A rt = newPrimitiveArray(size);
		for (int i = size - 1; i > -1; --i) {
            setArrayElement(rt, i, value);
		}
		return rt;
	}

	/**
	 * 返回一个长度为size的数组,所有元素均为默认的"空"值
	 * tips:【用于创建稀疏矩阵很方便】
	 * 
	 * @param size
	 * @return
	 */
	public A emptyPrimitiveValueArray(final int size) {
		return nPrimitiveCopy(size, emptyValue());
	}

	public A primitiveArrayfromStrings(final String... source) {
		if (ArrayUtil.isEmpty(source)) {
			return emptyPrimitiveArray();
		}
		final int len = source.length;
		final A rt = newPrimitiveArray(len);
		for (int i = 0; i < len; ++i) {
			setArrayElement(rt, i, fromString(source[i]));
		}
		return rt;
	}

	public List<P> fromStrings(final String... source) {
		if (ArrayUtil.isEmpty(source)) {
			return Kits.LIST.emptyValue();
		}
		final int len = source.length;
		final List<P> rt = new ArrayList<>(len);
		for (final String s : source) {
			final P e = fromString(s);
			if (e != null) {
				rt.add(e);
			}
		}
		return rt;
	}

	/**
	 * 数组指定区间中是否包含指定对象
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
	public boolean contains(final Object target, final int fromIndex, final int toIndex, final A source) {
		return indexOf(target, fromIndex, toIndex, source) > NOT_FOUND_INDEX;
	}

	/**
	 * 数组指定区间中是否包含指定对象
	 * 
	 * @param target
	 *            指定的对象
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param source
	 *            源
	 * @return
	 */
	public boolean contains(final Object target, final int fromIndex, final A source) {
		return indexOf(target, fromIndex, source) > NOT_FOUND_INDEX;
	}

	/**
	 * 数组中是否包含指定对象
	 * 
	 * @param target
	 *            指定的对象
	 * @param source
	 *            源
	 * @return
	 */
	public boolean contains(final Object target, final A source) {
		return indexOf(target, source) > NOT_FOUND_INDEX;
	}

	/**
	 * 在数组的指定区间查找指定的对象(使用equals方法对比)
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
	public int indexOf(final Object target, final int fromIndex, final int toIndex, final A source) {
		if (source == null || target == null) {
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
		final int t = Math.min(e, arrayLen0(source));
		for (int i = f; i < t; ++i) {
			if (target.equals(arrayElement(source, i))) {
				return i;
			}
		}
		return NOT_FOUND_INDEX;
	}

	/**
	 * 在数组的指定区间查找指定的对象(使用equals方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param source
	 *            源
	 * @return
	 */
	public int indexOf(final Object target, final int fromIndex, final A source) {
		return indexOf(target, fromIndex, arrayLen(source), source);
	}

	/**
	 * 在数组中查找指定的对象(使用equals方法对比)
	 * 
	 * @param target
	 *            指定的对象
	 * @param source
	 *            源
	 * @return
	 */
	public int indexOf(final Object target, final A source) {
		return indexOf(target, 0, source);
	}

	/**
	 * 判断一个原始类型数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public boolean isEmptyArray(final A array) {
		return arrayLen(array) == 0;
	}

	/**
	 * 将一个原始类型数组封装为一个不可修改的List
	 * 
	 * @param array
	 * @return
	 */
	public List<P> asUnmodifiableList(final A array) {
		return new UnmodifiablePrimitiveArrayList(array);
	}

	private class UnmodifiablePrimitiveArrayList extends UnmodifiableListTemplate<P>//
			implements List<P>, ElementNotNull, Serializable, Sorted, RandomAccess {

		private static final long serialVersionUID = -8109036474085412810L;

		private final A array;

		public UnmodifiablePrimitiveArrayList(final A array) {
			super();
			this.array = array;
		}

		@Override
		protected P get0(final int index) {
			return arrayElement(array, index);
		}

		@Override
		public int size() {
			return arrayLen(array);
		}

		@Override
		public Object[] toArray() {
			return wrap(array);
		}
	}

	/**
	 * 将一个原始类型数组复制为一个List,该List可以被修改.
	 * tips:【Arrays.asList返回的List是可修改的】
	 * 
	 * @param array
	 * @return
	 */
	public List<P> asList(final A array) {
		return new UnmodifiableListTemplate<P>() {

			private static final long serialVersionUID = 3525313484415223533L;

			@Override
			protected P get0(final int index) {
				return arrayElement(array, index);
			}

			@Override
			public int size() {
				return arrayLen(array);
			}
		};
	}

	/**
	 * 将一个原始类型数组复制为一个Set,该Set可以被修改.
	 * 注意:由于数组可以有重复元素而set没有,所以set.size()<=array.length
	 * 
	 * @param array
	 * @return
	 */
	public Set<P> asSet(final A array) {
		final int len = arrayLen(array);
		final Set<P> set = new HashSet<>(len);
		for (int i = 0; i < len; ++i) {
			set.add(arrayElement(array, i));
		}
		return set;
	}

	/**
	 * 将一个原始类型数组复制为一个包装类型的数组.
	 * 
	 * @param array
	 * @return
	 */
	public P[] wrap(final A array) {
		if (array == null) {
			return emptyArray();
		}
		final int len = arrayLen0(array);
		final P[] rt = newArray(len);
		for (int i = 0; i < len; ++i) {
			setArrayElementValue(array, i, rt, i);
		}
		return rt;
	}

	/**
	 * 将一个包装类型的数组复制为一个原始类型的数组
	 * 
	 * @param array
	 * @return
	 */
	@SafeVarargs
    public final A unwrap(final P... array) {
		if (array == null) {
			return emptyPrimitiveArray();
		}
		final int len = array.length;
		if (len == 0) {
			return emptyPrimitiveArray();
		}
		final A rt = newPrimitiveArray0(len);
		for (int i = 0; i < len; ++i) {
			setArrayElement(rt, i, array[i]);
		}
		return rt;
	}

	/**
	 * 反转数组中的元素
	 * 
	 * @param source
	 *            源数组
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param toIndex
	 *            到哪里结束(不包含)
	 */
	public void reverseArray(final A source, final int fromIndex, final int toIndex) {
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
		final int imax = (e - s) / 2 - s + 1;
		final int maxIndex = e - 1;
		for (int i = 0; i <= imax; ++i) {
			swap(source, i + s, maxIndex - i);
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
	public void reverseArray(final A source, final int fromIndex) {
		if (source == null) {
			return;
		}
		reverseArray(source, fromIndex, arrayLen(source));
	}

	/**
	 * 反转数组中的元素
	 * 
	 * @param source
	 *            源数组
	 */
	public void reverseArray(final A source) {
		reverseArray(source, 0);
	}

	/**
	 * 从数组中找出最大值
	 * 
	 * @param source
	 * @return
	 */
	public P max(final A source) {
		if (source == null) {
			return null;
		}
		P rt = arrayElement(source, 0);
		final int len = arrayLen(source);
		for (int i = len - 1; i > 0; --i) {
			final P o = arrayElement(source, i);
			if (inGreaterRange(o, rt)) {
				rt = o;
			}
		}
		return rt;
	}

	/**
	 * 从数组中找出最小值
	 * 
	 * @param source
	 * @return
	 */
	public P min(final A source) {
		if (source == null) {
			return null;
		}
		P rt = arrayElement(source, 0);
		final int len = arrayLen(source);
		for (int i = len - 1; i > 0; --i) {
			final P o = arrayElement(source, i);
			if (inSmallRange(o, rt)) {
				rt = o;
			}
		}
		return rt;
	}

	/**
	 * 返回数组指定区间
	 * 
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param toIndex
	 *            到哪里结束(不包含)
	 * @param source
	 *            源
	 * @return
	 */
	public A subArray(final int fromIndex, final int toIndex, final A source) {
		if (source == null || fromIndex == toIndex) {
			return emptyPrimitiveArray();
		}
		final int s, e;
		if (fromIndex < toIndex) {
			s = fromIndex;
			e = toIndex;
		} else {
			s = toIndex;
			e = fromIndex;
		}
		final int len = e - s;
		final A rt = newPrimitiveArray(len);
		for (int i = 0; i < len; ++i) {
			setArrayElement(rt, i, arrayElement(source, i + s));
		}
		return rt;
	}

	/**
	 * 返回数组指定区间
	 * 
	 * @param fromIndex
	 *            从哪里开始(包含)
	 * @param source
	 *            源
	 * @return
	 */
	public A subArray(final int fromIndex, final A source) {
		if (source == null) {
			return emptyPrimitiveArray();
		}
		return subArray(fromIndex, arrayLen(source), source);
	}

	/**
	 * <pre>
	 * 比较一个原始类型数组与另一个对象是否相等。
	 * 可以比较一个原始类型数组和其外包类型数组，长度相等且每个元素都相等则认为是相等的。
	 * </pre>
	 * 
	 * @param array
	 * @param other
	 * @return
	 */
	public boolean arrEquals(final A array, final Object other) {
		if (array == other) {
			return true;
		}
		if (array == null || other == null) {
			return false;
		}
        final Class<?> c = other.getClass();
		if (!c.isArray()) {
			return false;
		}
		if (!isThesaurusesClass(c.getComponentType())) {
			return false;
		}
		final int len = arrayLen(array);
		if (Array.getLength(other) != len) {
			return false;
		}
		for (int i = 0; i < len; ++i) {
			final P arrayElement = arrayElement(array, i);
			final Object obj = Array.get(other, i);
			if (arrayElement == null && obj != null || obj == null) {
				return false;
			}
            if (!arrayElement.equals(obj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 返回原始类型数组的Class对象
	 * 
	 * @return
	 */
	public Class<A> primitiveArrayClass() {
		return primitiveArrayClass;
	}

	//----------------------------------------------
	protected void swap(final A source, final int index1, final int index2) {
		final P o = arrayElement(source, index1);
		setArrayElement(source, index1, arrayElement(source, index2));
		setArrayElement(source, index2, o);
	}

	protected int arrayLen(final A array) {
		return array == null ? 0 : arrayLen0(array);
	}

	protected Class<?> primitiveClass() {
		return getClasses()[1];
	}

	//----------------------------------------------
	protected abstract A newPrimitiveArray0(int size);

	/**
	 * 返回一个原始类型数组的长度
	 * 
	 * @param array
	 * @return
	 */
	protected abstract int arrayLen0(final A array);

	protected abstract P arrayElement(A array, int index);

	protected abstract void setArrayElement(A array, int index, P value);

	protected abstract void setArrayElementValue(A array, int index, P[] toArray, int toArrayIndex);
}
