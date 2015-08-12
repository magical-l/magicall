/**
 * 类层次
 * Object
 * |-Util<T>
 * |-CharSequenceUtil<S>
 * | |-StrUtil<S=String>
 * |-ObjUtil
 * |-PrimitiveUtil<T,A>
 * | |-BoolUtil<T=Boolean,A=boolean[]>
 * | |-CharUtil<T=Character,A=char[]>
 * | |-NumUtil<T,A>
 * | |-DoubleUtil<T=Double,A=double[]>
 * | |-FloatUtil<T=Float,A=float[]>
 * | |-NotDoubleOrFloatUtil<T,A>
 * | |-IntUtil<T=Integer,A=int[]>
 * | |-LongUtil<T=Long,A=long[]>
 * | |-ByteUtil<T=Byte,A=byte[]>
 * | |-ShortUtil<T=Short,A=short[]>
 * |-WithSubClassUtil<T>
 * |-DateUtil<T=Date>
 * |-MapUtil
 * |-AbsCollectionUtil<C>
 * |-CollectionUtil
 * |-ListUtil
 * |-SetUtil
 */
package me.magicall.util.kit;

import me.magicall.consts.StrConst;
import me.magicall.util.ArrayUtil;
import me.magicall.util.ClassUtil;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 这个类的子类表示针对某一类型T的工具类，应该包含许多常用的工具方法，如返回一个空的一维数组，它是单例的，不需要到处去new它。
 * 本类则是这些子类一些公有方法的实现。
 * 本类子类建议单例
 * 
 * @author MaGiCalL
 * @param <T>
 */
public abstract class Kit<T> implements Comparator<T>, Serializable {

	protected static final Random RANDOM = new Random();
	//-----------------------------------------
	protected final Class<T> mainClass;
	private final Class<?>[] suitClasses;
	private final T emptyValue;
	private final String[] names;
	private final T[] emptyArray;

	//=========================================
	protected Kit(final Class<T> mainClass, final T emptyValue) {
		this(mainClass, emptyValue, StrConst.EMPTY_STR_ARR);
	}

	protected Kit(final Class<T> mainClass, final T emptyValue, final String... shortNames) {
		this(mainClass, emptyValue, shortNames, (Class<?>[]) null);
	}

	protected Kit(final Class<T> mainClass, final T notNullEmptyValue, final String[] shortNames, final Class<?>... otherClasses) {
		super();
		//class
		final Class<?>[] classes;
		if (ArrayUtil.isEmpty(otherClasses)) {
			classes = new Class[] { mainClass };
		} else {
			final int length = otherClasses.length;
			classes = new Class[1 + length];
			classes[0] = mainClass;
			for (int i = length; i > 0;) {
				classes[i--] = otherClasses[i];
			}
		}
		//name
		final Set<String> names = new HashSet<>();
		final Class<?>[] thesaurusClass = classes;
		for (final Class<?> c : thesaurusClass) {
			names.add(c.getName());
			names.add(c.getSimpleName());
		}
		if (!ArrayUtil.isEmpty(shortNames)) {
			for (final String s : shortNames) {
				names.add(s);
			}
		}
		String[] rt = new String[names.size()];
		rt = names.toArray(rt);
		//array
		final T[] emptyArray = ArrayUtil.emptyArray(mainClass);

		this.mainClass = mainClass;
		suitClasses = classes;
		emptyValue = notNullEmptyValue;
		this.emptyArray = emptyArray;
		this.names = rt;
	}

	//=========================================

	/**
	 * 判断本工具类是否适合这个参数
	 * 
	 * @param o
	 * @return
	 */
	public boolean isSuitUtil(final Object o) {
		Class<?> c = o.getClass();
		if (c.isArray()) {
			c = ClassUtil.componentType(c);
		}
		for (final String name : getNames()) {
			if (name.equalsIgnoreCase(c.getSimpleName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return Arrays.deepToString(getNames());
	}

	/**
	 * 判断两个对象是否相等
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public boolean equals(final T o1, final T o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}

	/**
	 * 注：如果子类的泛型T不是Comparable的实现类，调用本方法会抛异常
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int compare(final T o1, final T o2) {
		return ((Comparable) o1).compareTo(o2);
	}

	/**
	 * <pre>
	 * 判断名字代表的类是否是本util代表的类的"同义词"
	 * tips:
	 * 【可以析解类全名,类短名,基本类型关键字等】
	 * 【不区分大小写】
	 * </pre>
	 * 
	 * @param className
	 * @return
	 */
	public boolean isThesaurus(final String className) {
		final String[] name = getNames();
		for (final String n : name) {
			if (n.equalsIgnoreCase(className)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * 返回一个字符串数组,表示所代表类型的同义词类型名或近义词类型名.
	 * 如:Int会返回[int,Integer,java.lang.Integer]
	 * </pre>
	 * 
	 * @return
	 */
	public String[] getNames() {
		return names;
	}

	/**
	 * <pre>
	 * 返回一个Class对象的数组,表示所代表类型的同义词类型或近义词类型.
	 * 如:Int会返回[int.class,Class&lt;Integer&gt;]
	 * </pre>
	 * 
	 * @return
	 */
	public Class<?>[] getClasses() {
		return suitClasses;
	}

	public boolean isSuit(final Class<?> clazz) {
		for (final Class<?> c : getClasses()) {
			if (c.isAssignableFrom(clazz)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个类是否是本util代表的类或其外包类
	 * 
	 * @param clazz
	 * @return
	 */
	public boolean isThesaurusesClass(final Class<?> clazz) {
		final Object[] cs = getClasses();
		return ArrayUtil.contains(clazz, cs);
	}

	/**
	 * <pre>
	 * 判断前一个参数(source)所表示的范围是否真包含后一个参数(target)所表示的范围(source > target).
	 * 注:null与null相等,小于其他任何值.
	 * 即:
	 * 若source为null,则无论target是什么,有source <= target,返回false;
	 * 若target为null,source不为null,有source > target,返回true;
	 * </pre>
	 * 
	 * @param source
	 *            null等于null,小于任何其他值
	 * @param target
	 *            null等于null,小于任何其他值
	 * @return source > target
	 */
	public boolean inGreaterRange(final T source, final T target) {
		return compare(source, target) > 0;
	}

	/**
	 * <pre>
	 * 判断前一个参数(source)所表示的范围是否包含后一个参数(target)所表示的范围(source >= target).
	 * 注:null与null相等,小于其他任何值.
	 * 即:
	 * 若target为null,则无论source为何值,有source >= target,返回true;
	 * 若target不为null,source为null,有source < target,返回false;
	 * </pre>
	 * 
	 * @param source
	 *            null等于null,小于任何其他值
	 * @param target
	 *            null等于null,小于任何其他值
	 * @return source > target
	 */
	public boolean inGreaterEqualsRange(final T source, final T target) {
		return compare(source, target) >= 0;
	}

	/**
	 * <pre>
	 * 判断前一个参数(source)所表示的范围是否真包含于后一个参数(target)所表示的范围(source < target).
	 * 注:null与null相等,小于其他任何值.
	 * 即:
	 * 若target为null,则无论source是什么,有source >= target,返回false;
	 * 若target不为null,source为null,有source < target,返回true;
	 * </pre>
	 * 
	 * @param source
	 *            null等于null,小于任何其他值
	 * @param target
	 *            null等于null,小于任何其他值
	 * @return source > target
	 */
	public boolean inSmallRange(final T source, final T target) {
		return inGreaterRange(target, source);
	}

	/**
	 * <pre>
	 * 判断前一个参数(source)所表示的范围是否包含于后一个参数(target)所表示的范围(source <= target).
	 * 注:null与null相等,小于其他任何值.
	 * 即:
	 * 若source为null,则无论target是什么,有source <= target,返回false;
	 * 若source不为null,target为null,有source > target,返回true;
	 * </pre>
	 * 
	 * @param source
	 *            null等于null,小于任何其他值
	 * @param target
	 *            null等于null,小于任何其他值
	 * @return source > target
	 */
	public boolean inSmallEqualsRange(final T source, final T target) {
		return inGreaterEqualsRange(target, source);
	}

	/**
	 * 如果参数为"空",则返回默认的"空"值,否则返回参数本身.
	 * 用于去除讨厌的空指针异常很管用
	 * 
	 * @param source
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T1 extends T> T1 checkToEmptyValue(final T1 source) {
		return checkToDefaultValue(source, (T1) emptyValue());
	}

	/**
	 * 检查source是否为null，如果是，则返回defaultValue，否则返回source本身
	 * 
	 * @param <T1>
	 * @param source
	 * @param defaultValue
	 * @return
	 */
	public <T1 extends T> T1 checkToDefaultValue(final T1 source, final T1 defaultValue) {
		return source == null ? defaultValue : source;
	}

	/**
	 * <pre>
	 * 判断参数是否为"空"(包括null,默认的"空"值,"原点"值)
	 * 注意:此方法返回true并不表示参数即为null或defaultValue()方法所返回的值.
	 * </pre>
	 * 
	 * @param source
	 * @return
	 */
	public boolean isEmpty(final T source) {
		return emptyValue().equals(checkToEmptyValue(source));
	}

	/**
	 * 新建一个所表示类型的对象的数组
	 * 
	 * @param size
	 * @return
	 */
	public T[] newArray(final int size) {
		return size < 1 ? emptyArray() : newArray0(size);
	}

	/**
	 * 返回所表示类型的对象的空数组(单例)
	 * 
	 * @return
	 */
	public T[] emptyArray() {
		return emptyArray;
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
	public T[] nCopy(final int size, final T value) {
		final T[] rt = newArray(size);
		for (int i = 0; i < size; ++i) {
			rt[i] = value;
		}
		return rt;
	}

	/**
	 * <pre>
	 * 返回一个长度为size的数组,所有元素均为默认的"空"值
	 * tips:【用于创建稀疏矩阵很方便】
	 * </pre>
	 * 
	 * @param size
	 * @return
	 */
	public T[] emptyValueArray(final int size) {
		return nCopy(size, emptyValue());
	}

	/**
	 * 从数组中找出最大值
	 * 
	 * @param source
	 * @return
	 */
	@SafeVarargs
	public final T max(final T... source) {
		if (source == null) {
			return null;
		}
		T rt = source[0];
		for (int i = source.length - 1; i > 0; --i) {
			if (inGreaterRange(source[i], rt)) {
				rt = source[i];
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
	@SafeVarargs
	public final T min(final T... source) {
		if (source == null) {
			return null;
		}
		T rt = source[0];
		for (int i = source.length - 1; i > 0; --i) {
			if (inSmallRange(source[i], rt)) {
				rt = source[i];
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
	@SafeVarargs
	public final T[] subArray(final int fromIndex, final int toIndex, final T... source) {
		return ArrayUtil.sub(fromIndex, toIndex, source);
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
	@SafeVarargs
	public final T[] subArray(final int fromIndex, final T... source) {
		if (source == null) {
			return emptyArray();
		}
		return subArray(fromIndex, source.length, source);
	}

	/**
	 * 返回指定维数的T的数组的Class对象(Class<T[]>对象)
	 * 注意:低维数组并非高维数组的父类!（String[]不是String[][]的父类）
	 * 
	 * @param dim
	 *            维数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<? extends Object[]> arrayClass(final int dim) {
		return (Class<? extends Object[]>) ArrayUtil.arrClass(mainClass, dim);
	}

	/**
	 * <pre>
	 * 返回一个非null的代表"空"或"原点"的值.
	 * 注意:有些类(比如Collection)的"空"值不止一个,此方法仅返回其中一个
	 * </pre>
	 * 
	 * @return
	 */
	//这里有两个泛型参数,是为了MapUtil覆盖这个方法需要两个泛型参数
	public <T1, T2> T emptyValue() {
		return emptyValue;
	}

	/**
	 * <pre>
	 * 将一个String对象解析为一个所代表类型的对象.通常情况下是该对象toString方法的反解
	 * 注:鉴于各个外包类所提供的解析字符串方法名字不尽相同,本方法实际上将它们代理为相同名字的方法.
	 * </pre>
	 * 
	 * @param source
	 * @return
	 */
	public abstract T fromString(final String source);

	/**
	 * 请直接返回一个new出来的,长度为size的数组
	 * 
	 * @param size
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T1, T2> T[] newArray0(final int size) {
		return (T[]) Array.newInstance(mainClass, size);
	}

	//------------------------------------------------
	private static final long serialVersionUID = -6759189948300870516L;

	//=============================================
	public static void main(final String... args) {
//		final List<Integer> list = new ArrayList<Integer>();
//		list.add(1);
//		list.add(2);
//		list.add(3);
//		list.add(4);
//		list.add(5);
//
//		System.out.println("4,5tail@@@@@@" + Utils.LIST.tail(list, 3));
//		System.out.println("1,2,3head@@@@@@" + Utils.LIST.head(list, 3));

	}
}
