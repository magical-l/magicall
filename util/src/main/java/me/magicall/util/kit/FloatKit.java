/**
 * 
 */
package me.magicall.util.kit;

public final class FloatKit extends NumKit<Float, float[]> {

	private static final float EMPTY_VALUE = 0F;
	//------------------------------------------------
	public static final FloatKit INSTANCE = new FloatKit();

	//------------------------------------------------
	private FloatKit() {
		super(Float.class, EMPTY_VALUE, float.class, "F");
	}

	@Override
	public Float fromString(final String source) {
		if (Kits.STR.isEmpty(source)) {
			return 0f;
		}
		return Float.parseFloat(source.trim());
	}

	@Override
	protected float[] newPrimitiveArray0(final int size) {
		return new float[size];
	}

	@Override
	protected Float arrayElement(final float[] array, final int i) {
		return array[i];
	}

	@Override
	protected int arrayLen0(final float[] array) {
		return array.length;
	}

	@Override
	protected void setArrayElement(final float[] array, final int i, final Float value) {
		array[i] = value;
	}

	@Override
	protected void setArrayElementValue(final float[] array, final int i, final Float[] toArray, final int toArrayIndex) {
		toArray[toArrayIndex] = array[i];
	}

	@Override
	public Float fromLong(final Long value) {
		return value.floatValue();
	}

	//====================================================

	private static final long serialVersionUID = -8387880529039031786L;

	private Object readResolve() {
		return INSTANCE;
	}
}