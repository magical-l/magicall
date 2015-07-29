package me.magicall.util.touple;

public class Tuple {
	public static <A, B> TwoTuple<A, B> of(final A a, final B b) {
		return new TwoTuple<>(a, b);
	}

	public static <A, B, C> ThreeTuple<A, B, C> of(final A a, final B b, final C c) {
		return new ThreeTuple<>(a, b, c);
	}

	public static <A, B, C, D> FourTuple<A, B, C, D> of(final A a, final B b, final C c, final D d) {
		return new FourTuple<>(a, b, c, d);
	}

	public static <A, B, C, D, E> FiveTuple<A, B, C, D, E> of(final A a, final B b, final C c,
			final D d, final E e) {
		return new FiveTuple<>(a, b, c, d, e);
	}
}
