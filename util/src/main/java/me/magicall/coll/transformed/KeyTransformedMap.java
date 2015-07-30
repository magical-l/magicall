package me.magicall.coll.transformed;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.map.CommonEntry;
import me.magicall.util.kit.Kits;
import me.magicall.util.transformer.Transformer;

public class KeyTransformedMap<F, T, V> extends AbstractMap<T, V> implements Map<T, V> {

	private final Map<F, V> map;
	private final Transformer<F, T> tf;
	private final Transformer<T, F> negativeTf;
	private ElementTransformer<F, T> ef;
	private ElementTransformer<Entry<F, V>, Entry<T, V>> eef;

	public KeyTransformedMap(final Map<F, V> map, final Transformer<F, T> tf, final Transformer<T, F> negativeTf) {
		super();
		this.map = map;
		this.tf = tf;
		this.negativeTf = negativeTf;
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
		return Kits.SET.transform(map.entrySet(), eef());
	}

	private ElementTransformer<Entry<F, V>, Entry<T, V>> eef() {
		if (eef == null) {
			eef = (index, element) -> new CommonEntry<>(tf.transform(element.getKey()), element.getValue());
		}
		return eef;
	}

	@Override
	public V get(final Object key) {
		try {
			final F f = objToF(key);
			if (f == null) {
				return null;
			}
			return map.get(f);
		} catch (final ClassCastException e) {
			return null;
		}
	}

	private static void checkKey(final Object key) {
		if (key == null) {
			throw new NullPointerException();
		}
	}

	@SuppressWarnings("unchecked")
	private F objToF(final Object key) {
		checkKey(key);
		try {
			return negativeTf.transform((T) key);
		} catch (final ClassCastException e) {
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
			ef = (index, element) -> tf.transform(element);
		}
		return ef;
	}

	@Override
	public V put(final T key, final V value) {
		return map.put(negativeTf.transform(key), value);
	}

	@Override
	public V remove(final Object key) {
		try {
			final F f = objToF(key);
			if (f == null) {
				return null;
			}
			return map.remove(f);
		} catch (final ClassCastException e) {
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
