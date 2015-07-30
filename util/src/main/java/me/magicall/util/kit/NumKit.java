package me.magicall.util.kit;

/**
 * 数字型的工具类
 */
public abstract class NumKit<N extends Number & Comparable<N>, A> extends PrimitiveKit<N, A> {

	private static final long serialVersionUID = -3754578181746599423L;

	NumKit(final Class<N> mainClass, final N emptyValue, final Class<?> primitiveClass, final String primitiveArrFlag) {
		super(mainClass, emptyValue, primitiveClass, primitiveArrFlag);
	}

	NumKit(final Class<N> mainClass, final N emptyValue, final String[] shortNames, final Class<?> primitiveClass, final String primitiveArrFlag) {
		super(mainClass, emptyValue, shortNames, primitiveClass, primitiveArrFlag);
	}

	public static boolean deepEquals(final Number o1, final Number o2) {
		return o1.doubleValue() == o2.doubleValue();
	}

	//----------------------------------------
	public abstract N fromLong(Long value);

	//============================================
	static abstract class NotFloatOrDoubleKit<N extends Number & Comparable<N>, A> //
			extends NumKit<N, A> {

		private static final long serialVersionUID = 5064399753056613221L;

		//---------------------------------------------

		NotFloatOrDoubleKit(final Class<N> mainClass, final N emptyValue, final Class<?> primitiveClass, final String primitiveArrFlag) {
			super(mainClass, emptyValue, primitiveClass, primitiveArrFlag);
		}

		NotFloatOrDoubleKit(final Class<N> mainClass, final N emptyValue, final String[] shortNames, final Class<?> primitiveClass,
				final String primitiveArrFlag) {
			super(mainClass, emptyValue, shortNames, primitiveClass, primitiveArrFlag);
		}

		//---------------------------------------------
		/**
		 * 按位与检测第一个参数是否包含第二个参数数组中的每一个.
		 * 
		 * @param big
		 * @param smalls
		 * @return
		 */
		@SafeVarargs
		public final boolean maskTest(final N big, final N... smalls) {
			if (smalls == null) {
				return false;
			}
			final long bigLongValue = big.longValue();
			for (final N n : smalls) {
				final long smallLongValue = n.longValue();
				if (!maskTest0(bigLongValue, smallLongValue)) {
					return false;
				}
			}
			return true;
		}

		public boolean maskTest(final N big, final N small) {
			return maskTest0(big.longValue(), small.longValue());
		}

		public boolean maskTest(final N big, final A smalls) {
			if (smalls == null) {
				return false;
			}
			final long bigLongValue = big.longValue();
			final int len = arrayLen(smalls);
			for (int i = 0; i < len; ++i) {
				final N n = arrayElement(smalls, i);
				final long smallLongValue = n.longValue();
				if (!maskTest0(bigLongValue, smallLongValue)) {
					return false;
				}
			}
			return true;
		}

		@SafeVarargs
		public final N and(final N source, final N... masks) {
			if (masks == null) {
				return source;
			}
			N rt = source;
			for (final N n : masks) {
				rt = and(rt, n);
			}
			return rt;
		}

		public N and(final N source, final A masks) {
			if (masks == null) {
				return source;
			}
			final int len = arrayLen(masks);
			N rt = source;
			for (int i = 0; i < len; ++i) {
				rt = and(rt, arrayElement(masks, i));
			}
			return rt;
		}

		@SafeVarargs
		public final N or(final N source, final N... ns) {
			if (ns == null) {
				return source;
			}
			N rt = source;
			for (final N n : ns) {
				rt = or(rt, n);
			}
			return rt;
		}

		public N or(final N source, final A ns) {
			if (ns == null) {
				return source;
			}
			final int len = arrayLen(ns);
			N rt = source;
			for (int i = 0; i < len; ++i) {
				rt = or(rt, arrayElement(ns, i));
			}
			return rt;
		}

		public N and(final N source, final N mask) {
			return fromInt(source.intValue() & mask.intValue());
		}

		public N or(final N source, final N n) {
			return fromInt(source.intValue() | n.intValue());
		}

		public N not(final N source) {
			return fromInt(~source.intValue());
		}

		protected abstract N fromInt(int i);

		//==========================================
		private static boolean maskTest0(final long bigLongValue, final long smallLongValue) {
			return (bigLongValue & smallLongValue) == smallLongValue;
		}
	}
}
