package me.magicall.coll.transformed;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.map.CommonEntry;
import me.magicall.util.kit.Kits;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class KeyTransformedMap<F, T, V> extends AbstractMap<T, V> implements Map<T, V> {

	private final Map<F, V> map;
	private final Function<F, T> tf;
	private final Function<T, F> negativeFunction;
	private ElementTransformer<F, T> ef;
	private ElementTransformer<Entry<F, V>, Entry<T, V>> entryTransformer;

	public KeyTransformedMap(final Map<F, V> map, final Function<F, T> tf, final Function<T, F> negativeFunction) {
		super();
		this.map = map;
		this.tf = tf;
		this.negativeFunction = negativeFunction;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(final Object key) {
		return get(key) != null;
	}

	@Override
	public boolean containsValue(final Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<Entry<T, V>> entrySet() {
		return Kits.SET.transform(map.entrySet(), entryTransformer());
	}

	private ElementTransformer<Entry<F, V>, Entry<T, V>> entryTransformer() {
		if (entryTransformer == null) {
			entryTransformer = (index, element) -> new CommonEntry<>(tf.apply(element.getKey()), element.getValue());
		}
		return entryTransformer;
	}

	@Override
	public V get(final Object key) {
		try {
			final F f = keyTransform(key);
			if (f == null) {
				return null;
			}
			return map.get(f);
		} catch (final ClassCastException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void checkKey(final Object key) {
		if (key == null) {
			throw new NullPointerException();
		}
	}

	@SuppressWarnings("unchecked")
	private F keyTransform(final Object key) {
		checkKey(key);
		try {
			return negativeFunction.apply((T) key);
		} catch (final ClassCastException e) {
            e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<T> keySet() {
		return Kits.SET.transform(map.keySet(), ef());
	}

	private ElementTransformer<F, T> ef() {
		if (ef == null) {
			ef = (index, element) -> tf.apply(element);
		}
		return ef;
	}

	@Override
	public V put(final T key, final V value) {
		return map.put(negativeFunction.apply(key), value);
	}

	@Override
	public V remove(final Object key) {
		try {
			final F f = keyTransform(key);
			if (f == null) {
				return null;
			}
			return map.remove(f);
		} catch (final ClassCastException e) {
            e.printStackTrace();
			return null;
		}
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}
}
