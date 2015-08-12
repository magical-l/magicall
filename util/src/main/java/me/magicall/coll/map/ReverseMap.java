package me.magicall.coll.map;

import me.magicall.mark.Unmodifiable;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 这个飞机map的实现,是把一个现有map的key-value对应关系翻转来用.
 * 注意:由于原map可能存在多key对应一个value的情况,
 * 1.翻转后的map的key(原value)只对应其中的一个value(原key).具体对应哪个value,取决于源map的entrySet()
 * 的iterator()的遍历顺序
 * 
 * @author MaGiCalL
 * @param <K>
 * @param <V>
 */
public class ReverseMap<K, V> implements Map<K, V>, Unmodifiable, Serializable {

	private static final long serialVersionUID = -866040709518756325L;

	public static <K, V> ReverseMap<K, V> create(final Map<V, K> map) {
		return new ReverseMap<>(map);
	}

	//============================================

	private final Map<V, K> map;
	private volatile transient EntrySet<K, V> entrySet;

	//============================================

	private ReverseMap(final Map<V, K> map) {
		this.map = Collections.unmodifiableMap(map);
	}

	//============================================

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(final Object key) {
		return map.containsValue(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		return map.containsKey(value);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public V put(final K key, final V value) {
		map.put(value, key);
		return value;
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V remove(final Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return Collections.unmodifiableCollection(map.keySet());
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		if (entrySet == null) {
			entrySet = new EntrySet<>(map);
		}
		// TODO 这谁写的？这里大悲剧啊，谁调谁死掉。
//		return entrySet();
//		-_-b按回车的时候自动给我补全成方法名了
		return entrySet;
	}

	@Override
	public V get(final Object key) {
		if (key == null) {
			for (final Entry<V, K> e : map.entrySet()) {
				final K k = e.getValue();
				if (k == null) {
					return e.getKey();
				}
			}
		} else {
			for (final Entry<V, K> e : map.entrySet()) {
				final K k = e.getValue();
				if (key.equals(k)) {
					return e.getKey();
				}
			}
		}
		return null;
	}

	@Override
	public Set<K> keySet() {
		return Collections.unmodifiableSet(new HashSet<>(map.values()));
	}

	//=======================================================================

	private static class EntrySet<K, V> extends AbstractSet<Entry<K, V>> implements Unmodifiable, Serializable {

		private static final long serialVersionUID = -1567624779283525017L;
		private final Map<V, K> map;

		private EntrySet(final Map<V, K> map) {
			super();
			this.map = map;
		}

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new I<>(map.entrySet().iterator());
		}

		@Override
		public int size() {
			return map.values().size();
		}
	}

	private static class I<K, V> implements Iterator<Entry<K, V>>, Serializable {

		private static final long serialVersionUID = -763678651932393382L;
		private final Iterator<Entry<V, K>> iterator;

		private I(final Iterator<Entry<V, K>> iterator) {
			super();
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public Entry<K, V> next() {
			return new E<>(iterator.next());
		}

		@Override
		public void remove() {
			iterator.remove();
		}
	}

	private static class E<K, V> implements Entry<K, V>, Unmodifiable, Serializable {

		private static final long serialVersionUID = -6492822014046247614L;
		final Entry<V, K> e;

		private E(final Entry<V, K> e) {
			super();
			this.e = e;
		}

		@Override
		public K getKey() {
			return e.getValue();
		}

		@Override
		public V getValue() {
			return e.getKey();
		}

		@Override
		public V setValue(final V value) {
			throw new UnsupportedOperationException();
		}
	};

	public static void main(final String... args) {

	}

}
