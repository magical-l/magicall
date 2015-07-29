package me.magicall.tagInterfaces;

public interface Copyable<T extends Copyable<T>> extends Cloneable {

	T clone();
}
