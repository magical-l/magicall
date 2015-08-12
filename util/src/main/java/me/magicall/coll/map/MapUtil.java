/**
 * 
 */
package me.magicall.coll.map;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author Administrator
 */
public class MapUtil {

//	private static final KeyExtractable<Integer, BaseVO> KEY_EXTRACTABLE = new KeyExtractable<Integer, BaseVO>() {
//
//		@Override
//		public Integer extract(final BaseVO value) {
//			return value.getId();
//		}
//	};
//
//	public static final KeyExtractable<Integer, BaseVO> getDefaultBaseVOExtractor() {
//		return KEY_EXTRACTABLE;
//	}

	@SuppressWarnings("unchecked")
	public enum CollectionType {
		HashSet(java.util.HashSet.class), //
		ArrayList(java.util.ArrayList.class) //
		;

		private Constructor<? extends Collection> constructor;

		public Constructor<? extends Collection> getConstructor() {
			return constructor;
		}

		private CollectionType(final Class<? extends Collection> clazz) {
			try {
				constructor = clazz.getConstructor(Collection.class);
			} catch (final Exception e) {
				constructor = null;
				e.printStackTrace();
			}
		}
	}

//	private static final CollectionToMap<Integer, BaseVO> COLLECTION_TO_MAP //
//	= new CollectionToMap<Integer, BaseVO>(getDefaultBaseVOExtractor());

	/**
	 * // * 将一个BaseVO的集合按(id-BaseVO)搞到一个map里
	 * // *
	 * // * @param <V>
	 * // * @param coll
	 * // * @return
	 * //
	 */
//	@SuppressWarnings("unchecked")
//	public static <V extends BaseVO> Map<Integer, V> collectionToMap(final Collection<V> coll) {
//		return ((CollectionToMap<Integer, V>) COLLECTION_TO_MAP).apply(coll);
//	}

	public static <K, V> Entry<K, V> getIndex(final Map<K, V> map, final int index) {
		if (map == null) {
			return null;
		}
		if (index + 1 > map.size()) {
			return null;
		}
		int i = 0;
		final Set<Entry<K, V>> entrySet = map.entrySet();
		for (final Entry<K, V> entry : entrySet) {
			if (i == index) {
				return entry;
			}
			i++;
		}
		return null;
	}
}
