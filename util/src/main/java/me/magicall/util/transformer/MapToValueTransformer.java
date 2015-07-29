package me.magicall.util.transformer;

import java.util.Map;

public class MapToValueTransformer<K, V> implements Transformer<Map<? super K, ? extends V>, V> {

	private final K key;

	public MapToValueTransformer(final K key) {
		super();
		this.key = key;
	}

	@Override
	public V transform(final Map<? super K, ? extends V> from) {
		return from.get(key);
	}

}
