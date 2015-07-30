package me.magicall.lang;

/**
 * 本类对象用来在finally中作为回调
 * 
 * @author MaGiCalL
 */
@FunctionalInterface
public interface FinallyCallback {

	/**
	 * 将在finally块中调用的方法.
	 */
	void finallyExecute();

	FinallyCallback DO_NOTHING = () -> {
    };
}
