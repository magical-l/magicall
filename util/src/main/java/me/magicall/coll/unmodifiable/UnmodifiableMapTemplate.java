package me.magicall.coll.unmodifiable;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import me.magicall.coll.CollFactory.C;
import me.magicall.coll.CollFactory.S;
import me.magicall.mark.Unmodifiable;


public abstract class UnmodifiableMapTemplate<K, V> extends AbstractMap<K, V>//
		implements Map<K, V>, Serializable, Unmodifiable {

	private static final long serialVersionUID = 3878213948336484127L;

	public UnmodifiableMapTemplate() {
		super();
	}

	//-------------------------------------------------
	@Override
	public final Set<Entry<K, V>> entrySet() {
		return S.unmodifiable(entrySet0());
	}

	protected abstract Set<Entry<K, V>> entrySet0();

	@Override
	public final Set<K> keySet() {
		return S.unmodifiable(keySet0());
	}

	protected Set<K> keySet0() {
		return super.keySet();
	}

	@Override
	public final Collection<V> values() {
		return C.unmodifiable(values0());
	}

	protected Collection<V> values0() {
		return super.values();
	}

	//-------------------------------------------------
	@Override
	public boolean isEmpty() {
		return size() < 1;
	}

	//-------------------------------------------------
	@Override
	public final void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public final V put(final K key, final V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void putAll(final Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final V remove(final Object key) {
		throw new UnsupportedOperationException();
	}

}
