package me.magicall.coll;

import java.io.Serializable;

import me.magicall.mark.HasIdGetter;
import me.magicall.mark.HasIdGetter.HasIntIdGetter;
import me.magicall.mark.HasIdGetter.HasLongIdGetter;
import me.magicall.mark.Renamable;

public class ElementTransformerUtil {

	@FunctionalInterface
	public interface SerializableElementTransformer<F, T> extends ElementTransformer<F, T>, Serializable {

	}

	public static final ElementTransformer<Object, Object> SELF = new SerializableElementTransformer<Object, Object>() {
		private static final long serialVersionUID = -6814144005561407560L;

		@Override
		public Object transform(final int index, final Object element) {
			return element;
		}
	};

	@SuppressWarnings("unchecked")
	public static <F, T> ElementTransformer<F, T> self() {
		return (ElementTransformer<F, T>) SELF;
	}

	public static final ElementTransformer<Object, String> TO_STRING = new SerializableElementTransformer<Object, String>() {

		private static final long serialVersionUID = -8048351977691294931L;

		@Override
		public String transform(final int index, final Object element) {
			return element.toString();
		}

		private Object readResolve() {
			return TO_STRING;
		}
	};
	/**
	 * 一个ElementTransformer，将元素转换为下标
	 * 
	 * @author MaGiCalL
	 */
	public static final ElementTransformer<Object, Integer> TO_INDEX = new SerializableElementTransformer<Object, Integer>() {

		private static final long serialVersionUID = -4662475181262741188L;

		private Object readResolve() {
			return TO_INDEX;
		}

		@Override
		public Integer transform(final int index, final Object element) {
			return index;
		}
	};

	public static final ElementTransformer<HasIntIdGetter, Integer> TO_INT_ID = new SerializableElementTransformer<HasIntIdGetter, Integer>() {

		private static final long serialVersionUID = -4662475181262741189L;

		@Override
		public Integer transform(final int index, final HasIntIdGetter element) {
			return element.getId();
		}

		private Object readResolve() {
			return TO_INT_ID;
		}
	};

	public static final ElementTransformer<Renamable, String> TO_NAME = new SerializableElementTransformer<Renamable, String>() {

		private static final long serialVersionUID = 3233228054147778539L;

		@Override
		public String transform(final int index, final Renamable element) {
			return element.getName();
		}

		private Object readResolve() {
			return TO_NAME;
		}
	};

	public static final ElementTransformer<HasLongIdGetter, Long> TO_LONG_ID = new SerializableElementTransformer<HasLongIdGetter, Long>() {

		private static final long serialVersionUID = -3294872925624418952L;

		@Override
		public Long transform(final int index, final HasLongIdGetter element) {
			return element.getId();
		}

		private Object readResolve() {
			return TO_LONG_ID;
		}
	};
	public static final ElementTransformer<HasIdGetter<?>, Object> TO_OBJECT_ID = new SerializableElementTransformer<HasIdGetter<?>, Object>() {

		private static final long serialVersionUID = -3294872925624418952L;

		@Override
		public Object transform(final int index, final HasIdGetter<?> element) {
			return element.getId();
		}

		private Object readResolve() {
			return TO_OBJECT_ID;
		}
	};

	public static final ElementTransformer<Object, Class<?>> TO_CLASS = new SerializableElementTransformer<Object, Class<?>>() {

		private static final long serialVersionUID = -2612728211087704724L;

		@Override
		public Class<?> transform(final int index, final Object element) {
			return element.getClass();
		}

		private Object readResolve() {
			return TO_CLASS;
		}
	};

	public static final ElementTransformer<Object, Integer> TO_HASH_CODE = new SerializableElementTransformer<Object, Integer>() {

		private static final long serialVersionUID = 142527583830602018L;

		@Override
		public Integer transform(final int index, final Object element) {
			return element.hashCode();
		}

		private Object readResolve() {
			return TO_HASH_CODE;
		}
	};
	public static final ElementTransformer<Enum<?>, String> ENUM_TO_NAME = (index, element) -> element.name();
}
