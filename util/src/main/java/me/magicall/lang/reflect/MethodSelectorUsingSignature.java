package me.magicall.lang.reflect;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodSelectorUsingSignature implements MethodSelector {

	private final String name;
	private final Class<?>[] argTypes;

	public MethodSelectorUsingSignature(final String name, final Class<?>... argTypes) {
		super();
		this.name = name;
		this.argTypes = argTypes;
	}

	@Override
	public boolean accept(final Method method) {
		return method.getName().equals(name) && Arrays.equals(method.getParameterTypes(), argTypes);
	}

}
