package me.magicall.coll.compatible;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.ElementTransformerUtil;

import java.util.Map;

public class IgnoreAccuracyNumberKeyMap<V> extends AbsCompatibleMap<Number, String, V> {

//	private static final ElementTransformer<Number, Number> TF = new SerializableElementTransformer<Number, Number>() {
//		private static final long serialVersionUID = 6629990247876976225L;
//
//		@Override
//		public Number apply(final int index, final Number element) {
//			return element.doubleValue();
//		}
//	};

	public IgnoreAccuracyNumberKeyMap() {
		super();
	}

	public IgnoreAccuracyNumberKeyMap(final Map<? extends Number, ? extends V> map) {
		super(map);
	}

	@Override
	protected ElementTransformer<? super Number, String> keyTransformer() {
		return ElementTransformerUtil.TO_STRING;
	}

//	public static void main(final String[] args) {
//		final int i = (int) 0.1;
//		final float f = .1f;
//		final double d = .1d;
//		final long l = (long) 0.1;
//		System.out.println("@@@@@@IgnoreAccuracyNumberKeyMap.main():" + i + " " + TF.apply(0, i));
//		System.out.println("@@@@@@IgnoreAccuracyNumberKeyMap.main():" + f + " " + TF.apply(0, f));
//		System.out.println("@@@@@@IgnoreAccuracyNumberKeyMap.main():" + d + " " + TF.apply(0, d));
//		System.out.println("@@@@@@IgnoreAccuracyNumberKeyMap.main():" + l + " " + TF.apply(0, l));
//	}
}
