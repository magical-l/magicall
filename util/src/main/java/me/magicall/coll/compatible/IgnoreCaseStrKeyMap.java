package me.magicall.coll.compatible;

import me.magicall.coll.ElementTransformer;

public class IgnoreCaseStrKeyMap<V> extends AbsCompatibleMap<String, String, V> {

	private static final ElementTransformer<String, String> TO_LOWER_CASE = (index, element) -> element.toLowerCase();

	@Override
	protected ElementTransformer<? super String, String> keyTransformer() {
		return TO_LOWER_CASE;
	}

}
