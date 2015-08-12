package me.magicall.lang.exception;

@FunctionalInterface
public interface ThrowableCreater<T extends Throwable> {
	T create();
}
