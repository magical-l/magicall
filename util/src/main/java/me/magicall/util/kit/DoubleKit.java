/**
 * 
 */
package me.magicall.util.kit;

public final class DoubleKit extends NumKit<Double, double[]> {

	private static final double EMPTY_VALUE = 0D;
	//------------------------------------------
	public static final DoubleKit INSTANCE = new DoubleKit();

	//------------------------------------------
	private DoubleKit() {
		super(Double.class, EMPTY_VALUE, double.class, "D");
	}

	//------------------------------------------
	@Override
	public Double fromString(final String source) {
		if (Kits.STR.isEmpty(source)) {
			return 0d;
		}
		return Double.parseDouble(source);
	}

	//------------------------------------------
	@Override
	protected double[] newPrimitiveArray0(final int size) {
		return new double[size];
	}

	@Override
	protected Double arrayElement(final double[] array, final int i) {
		return array[i];
	}

	@Override
	protected int arrayLen0(final double[] array) {
		return array.length;
	}

	@Override
	protected void setArrayElement(final double[] array, final int i, final Double value) {
		array[i] = value;
	}

	@Override
	protected void setArrayElementValue(final double[] array, final int i, final Double[] toArray, final int toArrayIndex) {
		toArray[toArrayIndex] = array[i];
	}

	@Override
	public Double fromLong(final Long value) {
		return value.doubleValue();
	}

	//======================================================
	private static final long serialVersionUID = 1610565416858515411L;

	private Object readResolve() {
		return INSTANCE;
	}
}