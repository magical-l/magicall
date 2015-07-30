package me.magicall.coll.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IgnoreCaseStringKeyMap<V> implements Map<String, V> {
	private final Map<String, V> map;

	public IgnoreCaseStringKeyMap() {
		map = new HashMap<>();
	}

	public IgnoreCaseStringKeyMap(final Map<String, V> map) {
		super();
		this.map = map;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(final Object key) {
		if (!(key instanceof String)) {
			return false;
		}
		return map.containsKey(transformKey((String) key));
	}

	@Override
	public boolean containsValue(final Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(final Object key) {
		if (!(key instanceof String)) {
			return null;
		}
		return map.get(transformKey((String) key));
	}

	@Override
	public V put(final String key, final V value) {
		return map.put(key, value);
	}

	@Override
	public V remove(final Object key) {
		if (!(key instanceof String)) {
			return null;
		}
		return map.remove(transformKey((String) key));
	}

	@Override
	public void putAll(final Map<? extends String, ? extends V> m) {
		for (final Entry<? extends String, ? extends V> e : m.entrySet()) {
			map.put(transformKey(e.getKey()), e.getValue());
		}
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Entry<String, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean equals(final Object o) {
		return map.equals(o);
	}

	@Override
	public int hashCode() {
		return map.hashCode();
	}

	private static String transformKey(final String key) {
		return key.toLowerCase();
	}
}