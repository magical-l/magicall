package me.magicall.coll.bridge;

import me.magicall.coll.CollFactory.L;
import me.magicall.coll.CollFactory.M;
import me.magicall.coll.map.CommonEntry;
import me.magicall.coll.numseq.SeqSet;
import me.magicall.coll.unmodifiable.UnmodifiableIteratorTemplate;
import me.magicall.coll.unmodifiable.UnmodifiableMapTemplate;
import me.magicall.mark.Unmodifiable;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;

/**
 * <pre>
 * 将一个有序均匀数列(int)映射到一个List的元素上而成的map,可作为list的快速视图
 * 数列的每一个数将按其顺序转换成List的下标.
 * 即,数列长度与list长度相同,并且数列中的每一个元素出现的位置与list中相同位置的元素构成了本map的映射.
 *
 * 可能的适用场景:
 * 对于一组从数据库中取出的<i>连续的</i>记录(list<?>),每个记录都有其id值且这些值是连续的,可用本map将此list快速封装成id-?的映射
 * //List<User> list=[{User id=1,name="aa"},{User id=2,name="bb"},{User id=3,name="cc"}]
 * Map<Integer,User> map=new ListIndexElementMap<User>(list);
 * 此用法的局限性在于要求list必须满足:1,按id排好序的;2,元素的id是连续的或均匀的,没有"空洞"或"跳跃"
 *
 * !不可修改
 * !实现了序列化接口
 * !实现了SortedMap<Integer,V>接口
 * </pre>
 *
 * @param <V>
 * @author MaGicalL
 */
public class ListIndexElementMap<V> extends UnmodifiableMapTemplate<Integer, V>//
        implements Map<Integer, V>, SortedMap<Integer, V> {
    private static final long serialVersionUID = -5639276576996766856L;

    private final List<V> list;
    private final SeqSet keySet;
    private final int keyStep;

    public ListIndexElementMap(final List<V> list, final int keyFrom, final int keyStep) {
        super();
        this.list = list;
        keySet = new SeqSet(keyFrom, keyStep, list.size());
        this.keyStep = keyStep;
    }

    public ListIndexElementMap(final List<V> list, final int keyFrom) {
        this(list, keyFrom, 1);
    }

    public ListIndexElementMap(final List<V> list) {
        this(list, 0);
    }

    private ListIndexElementMap(final List<V> list, final SeqSet keySet, final int keyStep) {
        super();
        this.list = list;
        this.keySet = keySet;
        this.keyStep = keyStep;
    }

    @Override
    public boolean containsKey(final Object key) {
        if (key instanceof Integer) {
            final Integer k = (Integer) key;
            return 0 <= k && k < list.size();
        }
        return false;
    }

    @Override
    public boolean containsValue(final Object value) {
        return list.contains(value);
    }

    @Override
    protected Set<Entry<Integer, V>> entrySet0() {
        return new SeqEntrySet();
    }

    @Override
    public V get(final Object key) {
        if (key instanceof Integer) {
            return list.get((Integer) key);
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    protected Set<Integer> keySet0() {
        return keySet;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected Collection<V> values0() {
        return list;
    }

    @Override
    public String toString() {
        final int size = list.size();
        if (size == 0) {
            return "{}";
        }
        final StringBuilder sb = new StringBuilder("{");
        final Iterator<Integer> i1 = keySet.iterator();
        if (list instanceof RandomAccess) {
            for (int i = 0; i < size; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(i1.next()).append('=').append(list.get(i));
            }
        } else {
            int i = 0;
            for (final V v : list) {
                if (i > 0) {
                    sb.append(", ");
                }
                i++;
                sb.append(i1.next()).append('=').append(v);
            }
        }
        return sb.append('}').toString();
    }

    private class SeqEntrySet extends AbstractSet<Entry<Integer, V>>
            implements Set<Entry<Integer, V>>, Unmodifiable, Serializable {
        private static final long serialVersionUID = 997980960317390030L;

        @Override
        public Iterator<Entry<Integer, V>> iterator() {
            return new SeqEntryIterator();
        }

        @Override
        public int size() {
            return keySet.size();
        }
    }

    private class SeqEntryIterator extends UnmodifiableIteratorTemplate<Entry<Integer, V>> {
        private static final long serialVersionUID = -6651484798498405920L;

        private final Iterator<V> iterator = list.iterator();
        private int cur;

        public SeqEntryIterator(final int index) {
            super();
            cur = index;
        }

        public SeqEntryIterator() {
            this(0);
        }

        @Override
        public boolean hasNext() {
            return cur < keySet.size();
        }

        @Override
        public Entry<Integer, V> next() {
            return M.unmodifiable(new CommonEntry<>(keySet.toList().get(cur++), iterator.next()));
        }
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return keySet.comparator();
    }

    @Override
    public Integer firstKey() {
        return keySet.first();
    }

    @Override
    public SortedMap<Integer, V> headMap(final Integer toKey) {
        return new ListIndexElementMap<>(//
                list.subList(0, list.indexOf(toKey)),//
                (SeqSet) keySet.headSet(toKey), keyStep);
    }

    @Override
    public Integer lastKey() {
        return keySet.last();
    }

    @Override
    public SortedMap<Integer, V> subMap(final Integer fromKey, final Integer toKey) {
        return new ListIndexElementMap<>(//
                list.subList(list.indexOf(fromKey), list.indexOf(toKey)),//
                (SeqSet) keySet.subSet(fromKey, toKey), keyStep);
    }

    @Override
    public SortedMap<Integer, V> tailMap(final Integer fromKey) {
        return new ListIndexElementMap<>(//
                list.subList(list.indexOf(fromKey), list.size()),//
                (SeqSet) keySet.tailSet(fromKey), keyStep);
    }

    public static void main(final String... args) {
        final List<String> list = L.asList("a", "b", "c", "d", "e", "f");
        final Map<Integer, String> map = new ListIndexElementMap<>(list, 2, 2);
        System.out.println(map);
        for (final Entry<Integer, String> e : map.entrySet()) {
            System.out.println(e);
        }
    }
}
