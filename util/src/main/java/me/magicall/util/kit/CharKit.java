/**
 * 
 */
package me.magicall.util.kit;

public final class CharKit extends PrimitiveKit<Character, char[]> {

	private static final char EMPTY_VALUE = 0;
	//--------------------------------------
	public static final CharKit INSTANCE = new CharKit();

	//--------------------------------------
	private CharKit() {
		super(Character.class, EMPTY_VALUE, char.class, "C");
	}

	//--------------------------------------
	@Override
	public boolean equals(final Character c1, final Character c2) {
		return c1 == null ? c2 == null : equalsIgnoreCase(c1.charValue(), c2.charValue());
	}

	public boolean equalsIgnoreCase(final char c1, final char c2) {
		return Character.toLowerCase(c1) == Character.toLowerCase(c2);
	}

	@Override
	public Character fromString(final String source) {
		return source == null ? emptyValue() : source.charAt(0);
	}

	public boolean isEnLetter(final char c) {
		return isEnUpperCase(c) || isEnLowerCase(c);
	}

	public boolean isEnUpperCase(final char c) {
		return 'A' <= c && c <= 'Z';
	}

	public boolean isEnLowerCase(final char c) {
		return 'a' <= c && c <= 'z';
	}

	public boolean isEnUpperCase(final int c) {
		return 'A' <= c && c <= 'Z';
	}

	public boolean isEnLowerCase(final int c) {
		return 'a' <= c && c <= 'z';
	}

	public boolean isEnLetter(final int c) {
		return isEnUpperCase(c) || isEnLowerCase(c);
	}

	//-----------------------------------------
	@Override
	protected char[] newPrimitiveArray0(final int size) {
		return new char[size];
	}

	@Override
	protected Character arrayElement(final char[] array, final int i) {
		return array[i];
	}

	@Override
	protected int arrayLen0(final char[] array) {
		return array.length;
	}

	@Override
	protected void setArrayElement(final char[] array, final int i, final Character value) {
		array[i] = value;
	}

	@Override
	protected void setArrayElementValue(final char[] array, final int i, final Character[] toArray, final int toArrayIndex) {
		toArray[toArrayIndex] = array[i];
	}

	//===============================================
	private static final long serialVersionUID = -3420318554473780428L;

	private Object readResolve() {
		return INSTANCE;
	}
}