/**
 * 
 */
package me.magicall.util.kit;

import me.magicall.util.kit.NumKit.NotFloatOrDoubleKit;

public final class ShortKit extends NotFloatOrDoubleKit<Short, short[]> {

	private static final short EMPTY_VALUE = 0;
	//------------------------------------------
	public static final ShortKit INSTANCE = new ShortKit();

	//------------------------------------------
	private ShortKit() {
		super(Short.class, EMPTY_VALUE, short.class, "S");
	}

	//------------------------------------------
	@Override
	public Short fromString(final String source) {
		return Short.parseShort(source);
	}

	//-----------------------------------
	@Override
	protected short[] newPrimitiveArray0(final int size) {
		return new short[size];
	}

	@Override
	protected Short arrayElement(final short[] array, final int i) {
		return array[i];
	}

	@Override
	protected int arrayLen0(final short[] array) {
		return array.length;
	}

	@Override
	protected void setArrayElement(final short[] array, final int i, final Short value) {
		array[i] = value;
	}

	@Override
	protected void setArrayElementValue(final short[] array, final int i, final Short[] toArray, final int toArrayIndex) {
		toArray[toArrayIndex] = array[i];
	}

	@Override
	public Short and(final Short source, final Short mask) {
		return (short) (source & mask);
	}

	@Override
	protected Short fromInt(final int i) {
		return (short) i;
	}

	@Override
	public Short fromLong(final Long value) {
		return value.shortValue();
	}

	//============================================

	private static final long serialVersionUID = -7218500775289184401L;

	private Object readResolve() {
		return INSTANCE;
	}
}