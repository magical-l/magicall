package me.magicall.lang.exception;

@FunctionalInterface
public interface ExceptionCreater<E extends Exception> extends ThrowableCreater<E> {

}
