package me.magicall.util.kit;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import me.magicall.coll.util.CollectionKit;
import me.magicall.coll.util.ListKit;
import me.magicall.coll.util.MapKit;
import me.magicall.coll.util.SetKit;
import me.magicall.util.time.DateKit;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Kits {

	public static final IntKit INT = IntKit.INSTANCE;
	public static final LongKit LONG = LongKit.INSTANCE;
	public static final ByteKit BYTE = ByteKit.INSTANCE;
	public static final ShortKit SHORT = ShortKit.INSTANCE;
	public static final FloatKit FLOAT = FloatKit.INSTANCE;
	public static final DoubleKit DOUBLE = DoubleKit.INSTANCE;
	public static final CharKit CHAR = CharKit.INSTANCE;
	public static final BoolKit BOOL = BoolKit.INSTANCE;

	public static final StrKit STR = StrKit.INSTANCE;
	public static final ObjKit OBJ = ObjKit.INSTANCE;
	public static final DateKit DATE = DateKit.INSTANCE;

	public static final CollectionKit COLL = CollectionKit.INSTANCE;
	public static final SetKit SET = SetKit.INSTANCE;
	public static final ListKit LIST = ListKit.INSTANCE;
	public static final MapKit MAP = MapKit.INSTANCE;

	@SuppressWarnings("unchecked")
	private static final Collection<Kit<?>> ALL = Collections.unmodifiableCollection(Arrays.asList(//
			//最常用
			INT, STR, BOOL, DATE, OBJ,
			//次常用
			COLL, LIST, SET, MAP,//
			FLOAT, LONG, DOUBLE,//
			//不常用
			BYTE, SHORT, CHAR));

	private static final List<Kit<?>> SUIT_LIST = LIST.unmodifiable(Arrays.asList(//
			//数字型
			INT, LONG, DOUBLE, FLOAT, CHAR, BYTE, SHORT,//
			//其他
			STR, BOOL, DATE,
			//集合型
			LIST, SET, MAP, COLL, //
			//垫底
			OBJ));

	@SuppressWarnings("unchecked")
	private static volatile Collection<PrimitiveKit<?, ?>> ALL_PRIMITIVE_CLASS_UTILS = Collections.<PrimitiveKit<?, ?>> unmodifiableCollection(Arrays.asList(//
			INT, BOOL, FLOAT, LONG, DOUBLE, BYTE, SHORT, CHAR));
	private static final BiMap<Class<?>, Class<?>> PRIMITIVE_CLASS_REF;

	static {
		final BiMap<Class<?>, Class<?>> tmp = HashBiMap.create(8);
		tmp.put(int.class, Integer.class);
		tmp.put(boolean.class, Boolean.class);
		tmp.put(float.class, Float.class);
		tmp.put(long.class, Long.class);
		tmp.put(double.class, Double.class);
		tmp.put(byte.class, Byte.class);
		tmp.put(short.class, Short.class);
		tmp.put(char.class, Character.class);
		PRIMITIVE_CLASS_REF = tmp;
	}

	public static Kit<?> suitableUtil(final Object obj) {
		final Collection<Kit<?>> all = SUIT_LIST;
		for (final Kit<?> u : all) {
			if (u.isSuitUtil(obj)) {
				return u;
			}
		}
		return null;//TODO 应该返回ObjUtil
	}

	/**
	 * 仿enum做的一个方法.获取所有的已知的ClassUtils
	 * 
	 * @return
	 */
	public static Collection<Kit<?>> getAll() {
		return ALL;
	}

	/**
	 * <pre>
	 * 仿enum做的一个方法,根据名字获取某个util.
	 * tips:
	 * 【可以析解类全名,类短名,基本类型关键字等】
	 * 【不区分大小写】
	 * </pre>
	 * 
	 * @param <T>
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Kit<T> valueOf(final String name) {
		final Collection<Kit<?>> all = getAll();
		for (final Kit<?> u : all) {
			final String[] ss = u.getNames();
			for (final String s : ss) {
				if (s.equalsIgnoreCase(name)) {
					return (Kit<T>) u;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> Kit<T> valueOf(final Class<?> clazz) {
		final Collection<Kit<?>> all = getAll();
		for (final Kit<?> u : all) {
			final Class<?>[] classes = u.getClasses();
			for (final Class<?> c : classes) {
				if (c == clazz) {
					return (Kit<T>) u;
				}
			}
		}
		return null;
	}

	public static Class<?> wrapClass(final Class<?> primitiveClass) {
		return PRIMITIVE_CLASS_REF.get(primitiveClass);
	}

	public static Class<?> primitiveClass(final Class<?> primitiveClass) {
		return PRIMITIVE_CLASS_REF.inverse().get(primitiveClass);
	}

	/**
	 * 仿enum做的一个方法,根据名字获取某个util. 可以析解类全名,类短名,基本类型关键字等 不区分大小写
	 * 
	 * @param <T>
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, A> PrimitiveKit<T, A> valueOfPrimitive(final String name) {
		final Collection<PrimitiveKit<?, ?>> all = getAllPrimitiveClassUtils();
		for (final Kit<?> u : all) {
			final String[] ss = u.getNames();
			for (final String s : ss) {
				if (s.equalsIgnoreCase(name)) {
					return (PrimitiveKit<T, A>) u;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T, A> PrimitiveKit<T, A> getPrimitiveKit(final Class<?> clazz) {
		final Collection<PrimitiveKit<?, ?>> all = getAllPrimitiveClassUtils();
		for (final PrimitiveKit<?, ?> u : all) {
			if (u.isSuit(clazz)) {
				return (PrimitiveKit<T, A>) u;
			}
		}
		return null;
	}

	/**
	 * 获取基本类型及其外包类的utils
	 * 
	 * @return
	 */
	public static Collection<PrimitiveKit<?, ?>> getAllPrimitiveClassUtils() {
		return ALL_PRIMITIVE_CLASS_UTILS;
	}

	/**
	 * 判断某class是不是基本类型或其外包类.
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isPrimitiveOrWrapper(final Class<?> clazz) {
		return PRIMITIVE_CLASS_REF.keySet().contains(clazz) || PRIMITIVE_CLASS_REF.values().contains(clazz);
	}

	public static Collection<Class<?>> allPrimitiveClasses() {
		return PRIMITIVE_CLASS_REF.keySet();
	}

	public static Collection<Class<?>> allWrapClasses() {
		return PRIMITIVE_CLASS_REF.values();
	}

	public static void main(final String... args) {
	}
}
