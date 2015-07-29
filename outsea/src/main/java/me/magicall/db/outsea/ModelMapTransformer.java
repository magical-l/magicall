package me.magicall.db.outsea;

import java.util.Map;

public interface ModelMapTransformer<T> {

	Map<String, Object> modelToMap(final T model);

	T mapToModel(final Map<String, Object> map, final String mainModelName);
}
