package me.magicall.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

@FunctionalInterface
public interface SerializableComparator<T> extends Comparator<T>, Serializable {

}
