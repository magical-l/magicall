package me.magicall.coll.map;

import me.magicall.mark.Wrapper;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 在jsp里面,el表达式里写${map.get(3)},它会把3转化为一个Long来取值,如果我们当时放的是一个int,显然就取不出来值.
 * 因此做了这个类,它装饰了一个源map,重写了get方法,可以放到jsp中用.
 * 【这个类是不可修改的.如需修改,请在源map上修改】
 * 
 * @author MaGicalL
 */
public class ElMap implements Map<Object, Object>, Wrapper<Map<Object,Object>> {

	private final Map<Object, Object> map;

	public ElMap(final Map<?, ?> map) {
		super();
		this.map = Collections.unmodifiableMap(map);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(final Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<Entry<Object, Object>> entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean equals(final Object o) {
		return map.equals(o);
	}

	private Object get0(final Object key) {
		return map.get(key);
	}

	@Override
	public Object get(final Object key) {
		Object o = get0(key);//原始get
		if (o == null) {
			if (key instanceof Long) {
				final Long k = (Long) key;
				o = get0(k.intValue());//int
				if (o == null) {
					o = get0(k.byteValue());//byte
					if (o == null) {
						o = get0(k.shortValue());//short
					}
				}
			} else if (key instanceof Double) {
				final Double k = (Double) key;
				o = get0(k.floatValue());//double
			}
		}
		return o;
	}

	@Override
	public int hashCode() {
		return map.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<Object> keySet() {
		return map.keySet();
	}

	@Override
	public Object put(final Object key, final Object value) {
		return map.put(key, value);
	}

	@Override
	public void putAll(final Map<?, ?> m) {
		map.putAll(m);
	}

	@Override
	public Object remove(final Object key) {
		return map.remove(key);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<Object> values() {
		return map.values();
	}

	@Override
	public Map<Object, Object> unwrap() {
		return map;
	}
}
