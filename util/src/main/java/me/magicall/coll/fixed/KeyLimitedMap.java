/**
 * 
 */
package me.magicall.coll.fixed;

import me.magicall.coll.map.CommonEntry;
import me.magicall.coll.unmodifiable.UnmodifiableCollectionTemplate;
import me.magicall.coll.unmodifiable.UnmodifiableSetTemplate;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * 这是一种限制了key的取值范围的map,与EnumMap类似,但键值并非enum.
 * 它包含且只包含有指定的key,由构造时所传入的keySet参数指定.
 * 它还可以设置一个默认值给没有获得值的key.
 * get方法若传入不包含在keySet中的key,则恒返回null;否则返回key所对应的值,如果没有为它设置过值则返回defValue
 * put方法与通常的map有些差异.若传入不包含在keySet中的key,将抛出IllegalArgumentException.因此在put之前最好请先检查一下.
 * 在如下情况,我认为你会喜欢用这个map的:
 * 当你需要构造一个很大的map,其中很多key的值都是一样的,在通常情况下你会挨个put这些破key.而用本map你只需要指定defValue,并且这些值的影射其实都是不存在的,大大节省了内存.
 * 
 * @author Administrator
 * @version Mar 6, 2011 9:55:10 PM
 *
 */
public class KeyLimitedMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Fixed {

	private final Set<K> keySet;
	private final Map<K, V> map;
	private final V defValue;

	public KeyLimitedMap(final Set<K> keySet, final V defValue) {
		this.keySet = keySet;
		this.defValue = defValue;
		map = new HashMap<>(keySet.size());
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(final Object key) {
		return keySet.contains(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		if (map.containsValue(value)) {
			return true;
		}
		return eqDef(value) && keySet.size() > map.size();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new UnmodifiableSetTemplate<Entry<K, V>>() {
			private static final long serialVersionUID = -3071472392368964862L;

			@Override
			protected Iterator<Entry<K, V>> iterator0() {
				return new Iterator<Entry<K, V>>() {

					Iterator<K> keyIter = keySet.iterator();

					@Override
					public boolean hasNext() {
						return keyIter.hasNext();
					}

					@Override
					public Entry<K, V> next() {
						final K key = keyIter.next();
						return new CommonEntry<>(key, map.get(key));
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}

			@Override
			public int size() {
				return keySet.size();
			}
		};
	}

	@Override
	public V get(final Object key) {
		if (keySet.contains(key)) {
			return checkedValue(map.get(key));
		}
		return null;
	}

	@Override
	public Set<K> keySet() {
		return keySet;
	}

	@Override
	public V put(final K key, final V value) {
		if (keySet.contains(key)) {
			return map.put(key, value);
		} else {
			throw new IllegalArgumentException("illegal key:" + key + ",it's not in keySet");
		}
	}

	@Override
	public V remove(final Object key) {
		if (keySet.contains(key)) {
			return checkedValue(map.remove(key));
		} else {
			return null;
		}
	}

	@Override
	public int size() {
		return keySet.size();
	}

	@Override
	public Collection<V> values() {
		return new UnmodifiableCollectionTemplate<V>() {
			private static final long serialVersionUID = -4369032775999953308L;

			@Override
			protected Iterator<V> iterator0() {
				return new Iterator<V>() {
					Iterator<V> valueIter = map.values().iterator();

					@Override
					public boolean hasNext() {
						return valueIter.hasNext();
					}

					@Override
					public V next() {
						return checkedValue(valueIter.next());
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}

			@Override
			public int size() {
				return keySet.size();
			}
		};
	}

	private boolean eqDef(final Object value) {
		return defValue == null ? value == null : defValue.equals(value);
	}

	private V checkedValue(final V valueFromMap) {
		return valueFromMap == null ? defValue : valueFromMap;
	}
}
