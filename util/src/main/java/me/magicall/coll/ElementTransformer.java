/**
 * 
 */
package me.magicall.coll;

/**
 * @author Administrator
 */
@FunctionalInterface
public interface ElementTransformer<F, T> {

    T transform(int index, F element);
}
