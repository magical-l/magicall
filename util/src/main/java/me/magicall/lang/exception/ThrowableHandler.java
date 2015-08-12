package me.magicall.lang.exception;

@FunctionalInterface
public interface ThrowableHandler<T extends Throwable> {

	void handle(T e) throws RuntimeException;
}
