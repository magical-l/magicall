package me.magicall.lang.bean;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

import me.magicall.util.MethodUtil;
import me.magicall.util.touple.Tuple;
import me.magicall.util.touple.TwoTuple;

/**
 * 从某一类的实例对象中取出或设置值的工具.
 * 
 * @author MaGiCalL
 * @param <E>
 */
public interface FieldValueAccessor<E> {

	Object getValue(E obj, String fieldName);

	void setValue(E obj, String fieldName, Object value);

	FieldValueAccessor<Map<String, Object>> MAP_VALUE_ACCESSOR = new FieldValueAccessor<Map<String, Object>>() {

		@Override
		public Object getValue(final Map<String, Object> obj, final String fieldName) {
			return obj.get(fieldName);
		}

		@Override
		public void setValue(final Map<String, Object> obj, final String fieldName, final Object value) {
			obj.put(fieldName, value);
		}
	};

	FieldValueAccessor<Object> BEAN_VALUE_ACCESSOR = new FieldValueAccessor<Object>() {

		private final WeakHashMap<TwoTuple<Class<?>, String>, Method> getters = new WeakHashMap<>();
		private final WeakHashMap<TwoTuple<Class<?>, String>, Method> setters = new WeakHashMap<>();

		@Override
		public Object getValue(final Object obj, final String fieldName) {
			final Class<?> c = obj.getClass();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TwoTuple<Class<?>, String> key = (TwoTuple) Tuple.of(c, fieldName);
			Method method = getters.get(key);
			if (method == null) {
				method = MethodUtil.getGetter(fieldName, c);
				getters.put(key, method);
			}
			return MethodUtil.invokeMethod(obj, method);
		}

		@Override
		public void setValue(final Object obj, final String fieldName, final Object value) {
			if (value == null) {
				return;
			}
			final Class<?> c = obj.getClass();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final TwoTuple<Class<?>, String> key = (TwoTuple) Tuple.of(c, fieldName);
			Method method = setters.get(key);
			if (method == null) {
				method = MethodUtil.getSetterIgnoreNameCaseAndTypeAssigned(c, value.getClass());
				setters.put(key, method);
			}
			MethodUtil.invokeMethod(obj, method, value);
		}
	};
}
