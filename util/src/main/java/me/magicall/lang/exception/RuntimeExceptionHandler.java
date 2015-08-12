package me.magicall.lang.exception;

@FunctionalInterface
public interface RuntimeExceptionHandler<E extends RuntimeException> extends ExceptionHandler<E> {

}
