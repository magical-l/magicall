package me.magicall.db;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

import me.magicall.db.util.DbOrder;
import me.magicall.util.MethodUtil;
import me.magicall.util.touple.TwoTuple;

/**
 * 一种比较器，指定对象参与排序的字段以及排序顺序（正序或逆序）。
 * 
 * @author MaGiCalL
 * @param <T>
 */
public abstract class FieldComparator<T> implements Comparator<T> {
	@Override
	public int compare(final T o1, final T o2) {
		final List<TwoTuple<String, DbOrder>> comparingFieldsNamesAndOrders = getComparingFieldsNamesAndOrders();
		for (final TwoTuple<String, DbOrder> t : comparingFieldsNamesAndOrders) {
			final String fieldName = t.first;
			final DbOrder order = t.second;
			final Comparable<?> c1 = getValue(o1, fieldName);
			final Comparable<?> c2 = getValue(o2, fieldName);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final int i = ((Comparable) c1).compareTo(c2);
			if (i == 0) {
				continue;
			}
			if (i < 0) {
				if (order == DbOrder.ASC) {
					return i;
				} else {
					return i == Integer.MIN_VALUE ? Integer.MAX_VALUE : -i;
				}
			} else {
				if (order == DbOrder.ASC) {
					return i == Integer.MIN_VALUE ? Integer.MAX_VALUE : -i;
				} else {
					return i;
				}
			}
		}
		return 0;
	}

	/**
	 * 返回对象指定字段的值。
	 * 
	 * @param o
	 * @param fieldName
	 */
	protected Comparable<?> getValue(final T o, final String fieldName) {
		final Method getter = MethodUtil.getGetter(fieldName, o.getClass());
		if (getter == null) {
			throw new RuntimeException("the field " + fieldName + " of " + o + " has no public getter ");
		}
		final Object v = MethodUtil.invokeMethod(o, getter);
		if (v == null) {
			throw new RuntimeException("the field " + fieldName + " has no value");
		}
		if (!(v instanceof Comparable<?>)) {
			throw new RuntimeException("the field " + fieldName + " is not Comparable");
		}
		return (Comparable<?>) v;
	}

	/**
	 * 返回一个列表，列表的每个元素指定哪个字段参与比较排序，以及排序是正序还是倒序。
	 * 字段的权重由其在列表中的顺序确定，排在前面的权重高。
	 * 
	 * @return
	 */
	public abstract List<TwoTuple<String, DbOrder>> getComparingFieldsNamesAndOrders();
}
