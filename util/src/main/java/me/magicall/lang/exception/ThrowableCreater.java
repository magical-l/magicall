package me.magicall.lang.exception;

public interface ThrowableCreater<T extends Throwable> {
	T create();
}
