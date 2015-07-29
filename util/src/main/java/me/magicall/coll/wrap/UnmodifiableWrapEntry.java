package me.magicall.coll.wrap;

import java.io.Serializable;
import java.util.Map.Entry;

import me.magicall.coll.unmodifiable.UnmodifiableEntryTemplate;
import me.magicall.tagInterfaces.Unmodifiable;
import me.magicall.tagInterfaces.Wrapper;

public class UnmodifiableWrapEntry<K, V> extends UnmodifiableEntryTemplate<K, V>//
		implements Wrapper, Serializable, Entry<K, V>, Unmodifiable {
	private static final long serialVersionUID = -7266929317579948583L;

	private final Entry<K, V> e;

	public UnmodifiableWrapEntry(final Entry<K, V> e) {
		super();
		this.e = e;
	}

	@Override
	public boolean equals(final Object o) {
		return e.equals(o);
	}

	@Override
	public K getKey() {
		return e.getKey();
	}

	@Override
	public V getValue() {
		return e.getValue();
	}

	@Override
	public int hashCode() {
		return e.hashCode();
	}

	@Override
	public String toString() {
		return e.toString();
	}
}
