package me.magicall.sha;

import java.util.HashMap;
import java.util.Map;

public class ConfPool {

	private final Map<String, Object> confMap = new HashMap<>();

	public int size() {
		return confMap.size();
	}

	public boolean isEmpty() {
		return confMap.isEmpty();
	}

	public boolean containsKey(final Object key) {
		return confMap.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(final Object key) {
		return (T) confMap.get(key);
	}

	public void put(final String key, final Object value) {
		confMap.put(key, value);
	}

	@Override
	public String toString() {
		return confMap.toString();
	}
}
