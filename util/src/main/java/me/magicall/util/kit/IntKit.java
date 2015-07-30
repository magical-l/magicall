/**
 * 
 */
package me.magicall.util.kit;

import me.magicall.util.kit.NumKit.NotFloatOrDoubleKit;

public final class IntKit extends NotFloatOrDoubleKit<Integer, int[]> {

	private static final int EMPTY_VALUE = 0;
	//------------------------------------------
	public static final IntKit INSTANCE = new IntKit();

	//------------------------------------------
	private IntKit() {
		super(Integer.class, EMPTY_VALUE, int.class, "I");
	}

	//------------------------------------------
	@Override
	public Integer fromString(final String source) {
//			return Integer.parseInt(source);
		return fromString(source, 10);
	}

	/**
	 * <pre>
	 * 与Integer.parseInt的不同:
	 * .返回Integer而非int.
	 * .在解析失败时返回null而非抛出异常
	 * </pre>
	 * 
	 * @param s
	 * @param radix
	 * @return
	 */
	public Integer fromStringStrict(final String s, final int radix) {
		if (s == null || s.isEmpty()) {
			return null;
		}

		if (radix < Character.MIN_RADIX) {
			throw new NumberFormatException("radix " + radix + " less than Character.MIN_RADIX");
		}

		if (radix > Character.MAX_RADIX) {
			throw new NumberFormatException("radix " + radix + " greater than Character.MAX_RADIX");
		}

		final int len = s.length();
		//是否为负
		boolean negative = false;
		//用来遍历字符串每一个字符的
		int i = 0;
		//result的极值(最小的负整数 或 最大的正整数的相反数)
		final int limit;

		if (s.charAt(0) == '-') {
			negative = true;
			limit = Integer.MIN_VALUE;
			i++;
		} else {
			limit = -Integer.MAX_VALUE;
		}
		//最大一个可以乘以radix的值
		final int multmin = limit / radix;
		//每一位数字
		int digit;
		//注意result在计算过程中一直为负数,在返回时才判断是否取反.因为负数比整数可以多表示一个数字.
		int result = 0;
		if (i < len) {//第一个数字位
			digit = Character.digit(s.charAt(i++), radix);
			if (digit < 0) {
				return null;
			} else {
				result = -digit;
			}
		}
		while (i < len) {
			// Accumulating negatively avoids surprises near MAX_VALUE
			digit = Character.digit(s.charAt(i++), radix);
			if (digit < 0) {
				return null;
			}

			if (result < multmin) {
				//注意现在result和multmin都是负数.如果result比multmin还小,则不可以再乘radix,否则溢出.
				//此时解析到此为止
				return null;
			}

			final int tmp = result * radix;
			if (tmp < limit + digit) {
				//如果最后这一位的数字加到现在数值上会导致溢出.
				//比如,对于radix=10来说,只有3种情况:
				//2147483648和2147483649,刚好只比Integer.MAX_VALUE大1和2
				//-2147483649只比Integer.MIN_VALUE小1
				//若是这种情况,解析到此为止
				return null;
			}

			result = tmp - digit;
		}
		if (negative) {
			if (i > 1) {
				return result;
			} else { /* Only got "-" */
				return null;
			}
		} else {
			return -result;
		}
	}

	/**
	 * <pre>
	 * 与Integer.parseInt的不同:
	 * .返回Integer而非int.
	 * .解析不了时返回null而非抛出异常.
	 * .若解析到一个字符,不能转化为数字,或者会导致值溢出,则返回此字符前的解析结果.而非抛出异常.
	 * 	..如2147483648(比Integer.MAX_VALUE大1),将返回214748364.
	 * 	..(即可解析数字字母掺杂的情况)如21jjyya,将返回21.
	 * </pre>
	 * 
	 * @param s
	 * @param radix
	 * @return
	 */
	public Integer fromString(final String s, final int radix) {
		if (Kits.STR.isEmpty(s)) {
			return null;
		}

		if (radix < Character.MIN_RADIX) {
			throw new NumberFormatException("radix " + radix + " less than Character.MIN_RADIX");
		}

		if (radix > Character.MAX_RADIX) {
			throw new NumberFormatException("radix " + radix + " greater than Character.MAX_RADIX");
		}

		final int len = s.length();
		//是否为负
		boolean negative = false;
		//用来遍历字符串每一个字符的
		int i = 0;
		//result的极值(最小的负整数 或 最大的正整数的相反数)
		final int limit;

		if (s.charAt(0) == '-') {
			negative = true;
			limit = Integer.MIN_VALUE;
			i++;
		} else {
			limit = -Integer.MAX_VALUE;
		}
		//最大一个可以乘以radix的值
		final int multmin = limit / radix;
		//每一位数字
		int digit;
		//注意result在计算过程中一直为负数,在返回时才判断是否取反.因为负数比整数可以多表示一个数字.
		int result = 0;
		if (i < len) {//第一个数字位
			digit = Character.digit(s.charAt(i++), radix);
			if (digit < 0) {
				return null;
			} else {
				result = -digit;
			}
		}
		while (i < len) {
			// Accumulating negatively avoids surprises near MAX_VALUE
			digit = Character.digit(s.charAt(i++), radix);
			if (digit < 0) {
				break;
			}

			if (result < multmin) {
				//注意现在result和multmin都是负数.如果result比multmin还小,则不可以再乘radix,否则溢出.
				//此时解析到此为止
				break;
			}

			final int tmp = result * radix;
			if (tmp < limit + digit) {
				//如果最后这一位的数字加到现在数值上会导致溢出.
				//比如,对于radix=10来说,只有3种情况:
				//2147483648和2147483649,刚好只比Integer.MAX_VALUE大1和2
				//-2147483649只比Integer.MIN_VALUE小1
				//若是这种情况,解析到此为止
				break;
			}

			result = tmp - digit;
		}
		if (negative) {
			if (i > 1) {
				return result;
			} else { /* Only got "-" */
				return null;
			}
		} else {
			return -result;
		}
	}

	//------------------------------------------
	@Override
	protected int[] newPrimitiveArray0(final int size) {
		return new int[size];
	}

	@Override
	protected Integer arrayElement(final int[] array, final int i) {
		return array[i];
	}

	@Override
	protected int arrayLen0(final int[] array) {
		return array.length;
	}

	@Override
	protected void setArrayElement(final int[] array, final int i, final Integer value) {
		array[i] = value;
	}

	@Override
	protected void setArrayElementValue(final int[] array, final int i, final Integer[] toArray, final int toArrayIndex) {
		toArray[toArrayIndex] = array[i];
	}

	@Override
	public Integer and(final Integer source, final Integer mask) {
		return source & mask;
	}

	@Override
	protected Integer fromInt(final int i) {
		return i;
	}

	@Override
	public Integer fromLong(final Long value) {
		return value.intValue();
	}

	//======================================================
	private static final long serialVersionUID = 2021204638419195922L;

	private Object readResolve() {
		return INSTANCE;
	}
}
