/**
 *
 */
package me.magicall.coll.util;

import me.magicall.coll.KeyAccessor;
import me.magicall.coll.empty.EmptyMap;
import me.magicall.coll.transformed.ValueTransformedMap;
import me.magicall.coll.wrap.UnmodifiableWrapMap;
import me.magicall.mark.Unmodifiable;
import me.magicall.util.kit.Kits;
import me.magicall.util.kit.WithSubclassKit;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.function.Function;

public final class MapKit extends WithSubclassKit<Map<?, ?>> {

    private static final Class<?> CLASS_ARR = Map.class;

    //---------------------------------------------

    public static final MapKit INSTANCE = new MapKit();

    //----------------------------------------------

    @SuppressWarnings("unchecked")
    private MapKit() {
        super((Class<Map<?, ?>>) CLASS_ARR, Collections.emptyMap());
    }

    //----------------------------------------------

    public <K, T, V> Map<K, V> transform(final Map<K, T> source, final Function<T, V> transformer) {
        final Map<K, T> source1 = suit(source);
        return new ValueTransformedMap<>(source1, transformer);
    }

    @SuppressWarnings("unchecked")
    @Override
    //这里覆盖父类方法,是为了返回值能带上泛型
    public <K, V> Map<K, V> emptyValue() {
        return (Map<K, V>) EmptyMap.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <K, V> SortedMap<K, V> emptySortedMap() {
        return (SortedMap<K, V>) EmptyMap.INSTANCE;
    }

    @Override
    public boolean inGreaterRange(final Map<?, ?> source, final Map<?, ?> target) {
        if (source == null) {
            return false;
        } else if (target == null) {
            return true;
        }
        return source.size() > target.size() && source.entrySet().containsAll(target.entrySet());
    }

    @Override
    public boolean inGreaterEqualsRange(final Map<?, ?> source, final Map<?, ?> target) {
        if (target == null) {
            return true;
        } else if (source == null) {
            return false;
        }
        return source.size() >= target.size() && source.entrySet().containsAll(target.entrySet());
    }

    /**
     * 向上强转.把一个子类key对子类value的map强转为一个父类key对父类map.(这本身就是安全的,但jvm认不了)
     *
     * @param <K>
     * @param <K1>
     * @param <V>
     * @param <V1>
     * @param from
     * @return
     */
    @SuppressWarnings("unchecked")
    public <K, K1 extends K, V, V1 extends V> Map<K, V> suit(final Map<K1, V1> from) {
        return (Map<K, V>) from;
    }

    /**
     * <pre>
     * 将一个来历不明的map强转成某种类型的map
     * 可用于类似如下情况:
     * <code>
     * Map<Object,Object> map=new HashMap<String,Integer>();
     * Map<String,Integer> map2=forceSuit(map);
     * </code>
     * 注:这是"非安全的"
     * </pre>
     *
     * @param <K>
     * @param <V>
     * @param from
     * @return
     */
    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> forceSuit(final Map<?, ?> from) {
        return (Map<K, V>) from;
    }

    /**
     * 将源map代理为一个不可修改的map
     *
     * @param <K>
     * @param <V>
     * @param source
     * @return
     */
    public <K, V> Map<K, V> unmodifiable(final Map<K, V> source) {
        if (source instanceof Unmodifiable) {
            return source;
        }
        if (source == Collections.EMPTY_MAP) {
            return source;
        }
        return new UnmodifiableWrapMap<>(source);
    }

    public <K, V> Map<K, V> fromCollection(final Collection<? extends V> source,
                                           final KeyAccessor<? extends K, ? super V> keyExtractor) {
        final Map<K, V> map = new HashMap<>(source.size());
        for (final V e : source) {
            map.put(keyExtractor.extract(e), e);
        }
        return map;
    }

    public boolean deepEquals(final Map<?, ?> map1, final Map<?, ?> map2) {
        if (isEmpty(map1) && isEmpty(map2)) {
            return true;
        }
        for (final Entry<?, ?> e : map1.entrySet()) {
            final Object v1 = e.getValue();
            final Object v2 = map2.get(e.getKey());
            if (!Kits.OBJ.deepEquals(v1, v2)) {
                return false;
            }
        }
        return true;
    }

    //-----------------------------
    @Override
    public boolean isEmpty(final Map<?, ?> source) {
        return source == null || source.isEmpty();
    }

    //==============================================

    private static final long serialVersionUID = -5668892007091787439L;

    private Object readResolve() {
        return INSTANCE;
    }
}