/**
 * 
 */
package me.magicall.coll.empty;

import me.magicall.coll.sorted.Sorted;
import me.magicall.mark.Unmodifiable;
import me.magicall.util.kit.Kits;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public final class EmptyMap //
		implements Map<Object, Object>, SortedMap<Object, Object>,//
		Sorted, Serializable, Unmodifiable {

	public static final EmptyMap INSTANCE = new EmptyMap();

	private EmptyMap() {

	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(final Object key) {
		return false;
	}

	@Override
	public boolean containsValue(final Object value) {
		return false;
	}

	@Override
	public Set<Entry<Object, Object>> entrySet() {
		return Kits.SET.emptyValue();
	}

	@Override
	public Object get(final Object key) {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public Set<Object> keySet() {
		return Kits.SET.emptyValue();
	}

	@Override
	public Object put(final Object key, final Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(final Map<?, ?> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object remove(final Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Collection<Object> values() {
		return Kits.COLL.emptyValue();
	}

	@Override
	public Comparator<? super Object> comparator() {
		return null;
	}

	@Override
	public Object firstKey() {
		return null;
	}

	@Override
	public SortedMap<Object, Object> headMap(final Object toKey) {
		return this;
	}

	@Override
	public Object lastKey() {
		return null;
	}

	@Override
	public SortedMap<Object, Object> subMap(final Object fromKey, final Object toKey) {
		return this;
	}

	@Override
	public SortedMap<Object, Object> tailMap(final Object fromKey) {
		return this;
	}

	@Override
	public String toString() {
		return "{}";
	}

	private static final long serialVersionUID = -7783885209064760125L;
}