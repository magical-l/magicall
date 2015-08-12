package me.magicall.coll.sorted;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

/**
 * 这个玩意可以快速制造一个稀疏列表(大部分元素为null或指定值)
 * 可修改
 * 
 * @author MaGiCalL
 * @param <E>
 */
public class MapList<E> extends AbstractList<E> implements List<E>, RandomAccess, Serializable {

	private static final long serialVersionUID = -16787602475127134L;
	/**
	 * 在map中,null是defVal的占位符.
	 * 当defVal不是null时,如果向此list中添加null对象,则会将这个Object作为null的占位符放到map中.
	 */
	private static final Object USE_AS_NULL_WHEN_DEF_VAL_IS_NOT_NULL = new Object();

	/**
	 * 当该下标位置有"非空"元素,才会存在这个map里
	 */
	private final Map<Integer, Object> map;
	private int size;
	private E defVal;

	//------------------------------------------------

	public MapList(final Map<Integer, Object> map, final int size, final E defVal) {
		super();
		this.map = map;
		this.size = size;
		this.defVal = defVal;
	}

	public MapList(final Map<Integer, Object> map, final int size) {
		this(map, size, null);
	}

	public MapList(final int size, final E defVal) {
		this(new HashMap<>(), size, defVal);
	}

	public MapList(final E defVal) {
		this(0, defVal);
	}

	public MapList(final int size) {
		this(size, null);
	}

	public MapList() {
		this(0);
	}

	//------------------------------------------------

	public void fillTo(final int toSize) {
		if (size < toSize) {
			size = toSize;
		}
	}

	public void fillTo(final int index, final E e) {
		if (index > size) {
			fillTo(index);
		}
		set(index, e);
	}

	@Override
	public E get(final int index) {
		rangeCheck(index);
		return get0(index);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(final int index, final E element) {
		++size;
		rangeCheck(index);
		if (index == size) {
			put(index, element);
		} else {//插入到中间,则要将index之后的元素挪一挪
			E value = element;
			for (int i = index; i < size; ++i) {
				value = put(i, value);
			}
		}
	}

	@Override
	public E remove(final int index) {
		rangeCheck(index);
		--size;
		if (index == size) {
			return checkValue(map.remove(index));
		} else {//删除中间元素，要将index之后的元素挪一挪
			E value = getDefaultValue();
			for (int i = size; i >= index; --i) {
				value = put(index, value);
			}
			return value;
		}
	}

	@Override
	public E set(final int index, final E element) {
		rangeCheck(index);
		return put(index, element);
	}

	@Override
	public void clear() {
		map.clear();
		size = 0;
	}

	@Override
	protected void removeRange(final int fromIndex, final int toIndex) {
		final int removeCount = toIndex - fromIndex;
		final int finalSize = size - removeCount;
		int index = fromIndex;
		//被删元素后面的元素前移
		for (int i = toIndex; i < size; ++i) {
			//只是从原map中移动元素,故不check,是什么值就移什么值
			final Object v = map.remove(i);
			assert v != null;
			map.put(index, v);
			++index;
		}
		for (; index < toIndex; ++index) {
			map.remove(index);
		}
		size = finalSize;
	}

	/**
	 * 用此方法需保证参数是从合法的范围内(0~size)取出来的.可先用rangeCheck检查
	 * 
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private E checkValue(final Object o) {
		if (o == USE_AS_NULL_WHEN_DEF_VAL_IS_NOT_NULL) {
			//注意,此时defVal(洞)必不是null.这里返回的null,说明用户往这个位置放了一个"非洞"元素,值为null
			assert getDefaultValue() != null;
			return null;
		}
		if (o == null) {//map中没有这个元素，这说明这个元素是个默认值。
			return getDefaultValue();
		}
		return (E) o;
	}

	private E put(final int index, final E e) {
		if (e == null) {
			if (getDefaultValue() == null) {// 根本不会也不需要往map中put null,但需要把原值删除
				return remove0(index);
			} else {//默认元素不为null，想放null，就放一个代表null的元素进去
				return putNullElement(index);
			}
		} else {//普通情况
			return checkValue(map.put(index, e));
		}
	}

	/**
	 * 内部使用
	 * 
	 * @param index
	 * @return
	 */
	private E get0(final int index) {
		return checkValue(map.get(index));
	}

	private E remove0(final int index) {
		return checkValue(map.remove(index));
	}

	private E putNullElement(final int index) {
		return checkValue(map.put(index, USE_AS_NULL_WHEN_DEF_VAL_IS_NOT_NULL));
	}

	private void rangeCheck(final int index) {
		rangeCheck(index, 0, size());
	}

	private static void rangeCheck(final int index, final int min, final int max) {
		if (index < min || index >= max) {
			throw new IndexOutOfBoundsException("Index: " + index + ", min: " + min + ", max:" + max);
		}
	}

	public E getDefaultValue() {
		return defVal;
	}

	public void setDefaultValue(final E defVal) {
		this.defVal = defVal;
	}

	public static void main(final String... args) {
        final MapList<String> list = new MapList<>("");
		for (int i = 0; i < 10; ++i) {
			list.add(String.valueOf(i));
		}
		System.out.println("@@@@@@" + list + list.map);

        final int maxSize = 20;
        list.fillTo(maxSize);
		System.out.println("@@@@@@" + list + list.map);

		for (int i = 10; i < maxSize; ++i) {
			list.set(i, String.valueOf(i));
		}
		System.out.println("@@@@@@" + list + list.map);

		list.removeRange(10, 15);
		System.out.println("@@@@@@" + list + list.map);

		list.add(null);
		list.add(list.getDefaultValue());
		list.add(null);
		System.out.println("@@@@@@" + list + list.map);

		System.out.println("@@@@@@" + list.remove(list.size() - 1));

		System.out.println("@@@@@@" + list + list.map);
	}
}
