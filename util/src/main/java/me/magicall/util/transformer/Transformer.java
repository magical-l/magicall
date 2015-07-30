/**
 * 
 */
package me.magicall.util.transformer;

/**
 * @author Administrator
 */
public interface Transformer<F, T> {

	T transform(F from);
}
