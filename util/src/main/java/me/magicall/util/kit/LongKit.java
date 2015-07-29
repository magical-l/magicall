/**
 * 
 */
package me.magicall.util.kit;

import me.magicall.util.kit.NumKit.NotFloatOrDoubleKit;

public final class LongKit extends NotFloatOrDoubleKit<Long, long[]> {

	private static final long EMPTY_VALUE = 0L;
	private static final String[] SHORT_NAMES = { "bigint" };
	//-------------------------------------------
	public static final LongKit INSTANCE = new LongKit();

	//-------------------------------------------
	private LongKit() {
		super(Long.class, EMPTY_VALUE, SHORT_NAMES, long.class, "J");
	}

	//-------------------------------------------
	@Override
	public Long fromString(final String source) {
		try {
			return Long.parseLong(source);
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	//-------------------------------------------
	@Override
	protected long[] newPrimitiveArray0(final int size) {
		return new long[size];
	}

	@Override
	protected Long arrayElement(final long[] array, final int i) {
		return array[i];
	}

	@Override
	protected int arrayLen0(final long[] array) {
		return array.length;
	}

	@Override
	protected void setArrayElement(final long[] array, final int i, final Long value) {
		array[i] = value;
	}

	@Override
	protected void setArrayElementValue(final long[] array, final int i, final Long[] toArray, final int toArrayIndex) {
		toArray[toArrayIndex] = array[i];
	}

	@Override
	public Long and(final Long source, final Long mask) {
		return source & mask;
	}

	@Override
	public Long not(final Long source) {
		return ~source;
	}

	@Override
	public Long or(final Long source, final Long n) {
		return source | n;
	}

	@Override
	protected Long fromInt(final int i) {
		return (long) i;
	}

	@Override
	public Long fromLong(final Long value) {
		return value;
	}

	//===================================================
	private static final long serialVersionUID = 7035128855891968371L;

	private Object readResolve() {
		return INSTANCE;
	}
}