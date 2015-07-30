/**
 * 
 */
package me.magicall.coll;

/**
 * @author Administrator
 */
public interface ElementTransformer<F, T> {

    T transform(int index, F element);
}
