package me.magicall.lang.exception;

@FunctionalInterface
public interface ExceptionHandler<E extends Exception> extends ThrowableHandler<E> {

}
