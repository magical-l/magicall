/**
 * 
 */
package me.magicall.coll;

/**
 * @author Administrator
 */
public interface ElementTransformer<F, T> {

    public T transform(int index, F element);
}
