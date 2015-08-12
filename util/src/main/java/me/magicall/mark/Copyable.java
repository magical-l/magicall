package me.magicall.mark;

@FunctionalInterface
public interface Copyable<T extends Copyable<T>> extends Cloneable {

	T clone();
}
