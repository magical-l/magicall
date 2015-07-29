package me.magicall.util.touple;

public class FiveTuple<A, B, C, D, E> extends FourTuple<A, B, C, D> {

	public final E fifth;

	public FiveTuple(final A a, final B b, final C c, final D d, final E e) {
		super(a, b, c, d);
		fifth = e;
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ", " + third + ", " + fourth + ", " + fifth + ')';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fifth == null) ? 0 : fifth.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final FiveTuple<?, ?, ?, ?, ?> other = (FiveTuple<?, ?, ?, ?, ?>) obj;
		if (fifth == null) {
			if (other.fifth != null) {
				return false;
			}
		} else if (!fifth.equals(other.fifth)) {
			return false;
		}
		return true;
	}

}
