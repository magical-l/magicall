package me.magicall.coll.map;

import java.io.Serializable;
import java.util.Map.Entry;

public class CommonEntry<K, V> implements Entry<K, V>, Serializable {

	private static final long serialVersionUID = -2966866827953814961L;

	private final K k;
	private V v;

	public CommonEntry(final K key, final V value) {
		super();
		k = key;
		v = value;
	}

	@Override
	public K getKey() {
		return k;
	}

	@Override
	public V getValue() {
		return v;
	}

	@Override
	public V setValue(final V value) {
		final V rt = v;
		v = value;
		return rt;
	}

	@Override
	public String toString() {
		return k + "=" + v;
	}
}
