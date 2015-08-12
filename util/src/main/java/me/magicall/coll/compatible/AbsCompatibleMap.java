package me.magicall.coll.compatible;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.ElementTransformerUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbsCompatibleMap<K, K2, V> implements Map<K, V> {

	protected Map<K, V> map;
	protected Map<K2, V> keyTransformedMap;

	protected AbsCompatibleMap() {
		map = new HashMap<>();
		keyTransformedMap = new HashMap<>();
	}

	protected AbsCompatibleMap(final Map<? extends K, ? extends V> map) {
		this();
		putAll(map);
	}

	protected K2 keyTransform(final K key) {
		return keyTransformer().transform(0, key);
	}

	protected ElementTransformer<? super K, ? extends K2> keyTransformer() {
		return ElementTransformerUtil.self();
	}

	@Override
	@SuppressWarnings("unchecked")
	public V get(final Object key) {
		final V v = map.get(key);
		return v == null ? keyTransformedMap.get(keyTransform((K) key)) : v;
	}

	@Override
	public V put(final K key, final V value) {
		final K2 key2 = keyTransform(key);
		if (keyTransformedMap.containsKey(key2) && !map.containsKey(key)) {
			throw new IllegalStateException("containing another object in other key form");
		}
		map.put(key, value);
		return keyTransformedMap.put(key2, value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public V remove(final Object key) {
		map.remove(key);
		return keyTransformedMap.remove(keyTransform((K) key));
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean containsKey(final Object key) {
		return map.containsKey(key) || keyTransformedMap.containsKey(keyTransform((K) key));
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean containsValue(final Object value) {
		return map.containsValue(value);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Map<?, ?>)) {
			return false;
		}
		final Map<?, ?> other = (Map<?, ?>) o;
		if (other.size() != size()) {
			return false;
		}
		for (final Entry<?, ?> e : other.entrySet()) {
			final Object otherV = e.getValue();
			final V v = get(e.getKey());
			if (v == null) {
				if (otherV != null) {
					return false;
				}
			} else {
				if (otherV == null) {
					return false;
				}
				if (!v.equals(otherV)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return map.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> m) {
		for (final Entry<? extends K, ? extends V> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
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

	@Override
	public void clear() {
		map.clear();
		keyTransformedMap.clear();
	}
}
