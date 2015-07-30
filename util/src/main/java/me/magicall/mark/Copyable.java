package me.magicall.mark;

public interface Copyable<T extends Copyable<T>> extends Cloneable {

	T clone();
}
