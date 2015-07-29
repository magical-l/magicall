package me.magicall.coll.compatible;

public class IgnoreAccuraryNotDecimalKeyMap<V> extends AbsCompatibleMap<Number, Long, V> {

	@Override
	protected Long keyTransform(final Number key) {
		return key.longValue();
	}

}
