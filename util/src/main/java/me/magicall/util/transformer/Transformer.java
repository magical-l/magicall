/**
 * 
 */
package me.magicall.util.transformer;

/**
 * @author Administrator
 */
public interface Transformer<F, T> {

	public T transform(F from);
}
