package me.magicall.lang.exception;

public interface ThrowableHandler<T extends Throwable> {

	void handle(T e) throws RuntimeException;
}
