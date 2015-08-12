package me.magicall.coll.transformed;

import me.magicall.coll.CollFactory.M;
import me.magicall.coll.ElementTransformer;
import me.magicall.coll.map.CommonEntry;
import me.magicall.coll.unmodifiable.UnmodifiableMapTemplate;
import me.magicall.mark.Unmodifiable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class ValueTransformedMap<K, T, V> extends UnmodifiableMapTemplate<K, V>//
		implements Map<K, V>, Serializable, Unmodifiable {
	private static final long serialVersionUID = 5133391467749204907L;

	private final Map<K, T> map;
	private final Function<T, V> function;
	private final ElementTransformer<T, V> et;
	private final ElementTransformer<Entry<K, T>, Entry<K, V>> entryTransformer;

	public ValueTransformedMap(final Map<K, T> map, final Function<T, V> function) {
		super();
		this.map = map;
		this.function = function;
		et = (index, element) -> tf(element);
		entryTransformer = (index,
							element) -> M.unmodifiable(new CommonEntry<>(element.getKey(), tf(element.getValue())));
	}

	private V tf(final T t) {
		return function.apply(t);
	}

	@Override
	public boolean containsKey(final Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		if (value == null) {
			for (final T t : map.values()) {
				if (tf(t) == null) {
					return true;
				}
			}
		} else {
			for (final T t : map.values()) {
				if (value.equals(function.apply(t))) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Set<Entry<K, V>> entrySet0() {
		return new TransformedSet<>(map.entrySet(), entryTransformer);
	}

	@Override
	public V get(final Object key) {
		return function.apply(map.get(key));
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet0() {
		return map.keySet();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values0() {
		return new TransformedCollection<>(map.values(), et);
	}

}
