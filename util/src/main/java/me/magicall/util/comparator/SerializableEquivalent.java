/**
 * 
 */
package me.magicall.util.comparator;

import java.io.Serializable;

@FunctionalInterface
public interface SerializableEquivalent<T> extends Equivalent<T>, Serializable {

}