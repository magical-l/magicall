package me.magicall.util;

import java.util.Map;

@FunctionalInterface
public interface ToMapable<K, V> {

	Map<K, V> toMap();
}
