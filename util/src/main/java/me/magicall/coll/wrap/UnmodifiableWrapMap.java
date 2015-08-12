package me.magicall.coll.wrap;

import me.magicall.coll.unmodifiable.UnmodifiableMapTemplate;
import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class UnmodifiableWrapMap<K, V> extends UnmodifiableMapTemplate<K, V>//
		implements Map<K, V>, Serializable, Wrapper<Map<K,V>>, Unmodifiable {
	private static final long serialVersionUID = -5691150999129985447L;

	private final Map<K, V> map;

	public UnmodifiableWrapMap(final Map<K, V> map) {
		super();
		this.map = map;
	}

	@Override
	public Set<Entry<K, V>> entrySet0() {
		return map.entrySet();
	}

	@Override
	public Set<K> keySet0() {
		return map.keySet();
	}

	@Override
	public Collection<V> values0() {
		return map.values();
	}

	@Override
	public boolean containsKey(final Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		return map.containsValue(value);
	}

	@Override
	public boolean equals(final Object o) {
		return map.equals(o);
	}

	@Override
	public V get(final Object key) {
		return map.get(key);
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
	public int size() {
		return map.size();
	}

	@Override
	public String toString() {
		return map.toString();
	}

	@Override
	public Map<K, V> unwrap() {
		return this;
	}
}
