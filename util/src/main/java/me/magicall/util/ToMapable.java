package me.magicall.util;

import java.util.Map;

public interface ToMapable<K, V> {

	Map<K, V> toMap();
}
