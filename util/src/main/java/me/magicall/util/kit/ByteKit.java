/**
 * 
 */
package me.magicall.util.kit;

import me.magicall.util.kit.NumKit.NotFloatOrDoubleKit;

public final class ByteKit extends NotFloatOrDoubleKit<Byte, byte[]> {

	private static final byte EMPTY_VALUE = 0;
	//-----------------------------------------
	public static final ByteKit INSTANCE = new ByteKit();

	//-----------------------------------------
	private ByteKit() {
		super(Byte.class, EMPTY_VALUE, byte.class, "B");
	}

	//-----------------------------------------
	@Override
	public Byte fromString(final String source) {
		return Byte.parseByte(source);
	}

	//------------------------------------
	@Override
	protected byte[] newPrimitiveArray0(final int size) {
		return new byte[size];
	}

	@Override
	protected Byte arrayElement(final byte[] array, final int i) {
		return array[i];
	}

	@Override
	protected int arrayLen0(final byte[] array) {
		return array.length;
	}

	@Override
	protected void setArrayElement(final byte[] array, final int i, final Byte value) {
		array[i] = value;
	}

	@Override
	protected void setArrayElementValue(final byte[] array, final int i, final Byte[] toArray, final int toArrayIndex) {
		toArray[toArrayIndex] = array[i];
	}

	@Override
	public Byte and(final Byte source, final Byte mask) {
		return (byte) (source & mask);
	}

	@Override
	protected Byte fromInt(final int i) {
		return (byte) i;
	}

	@Override
	public Byte fromLong(final Long value) {
		return value.byteValue();
	}

	//===================================================

	private static final long serialVersionUID = -7203473662282000660L;

	private Object readResolve() {
		return INSTANCE;
	}
}