package me.magicall.db;

import java.util.List;

import me.magicall.coll.CollFactory.L;
import me.magicall.db.util.DbOrder;
import me.magicall.mark.HasIdGetter;
import me.magicall.util.touple.Tuple;
import me.magicall.util.touple.TwoTuple;

/**
 * 只有一个字段参与排序的比较器类。
 * 
 * @author MaGiCalL
 * @param <T>
 */
public class OneFieldComparator<T> extends FieldComparator<T> {
	private final List<TwoTuple<String, DbOrder>> list;
	/**
	 * 根据id字段逆向排序的比较器。
	 */
	public static final FieldComparator<HasIdGetter<?>> ID_DESC = new OneFieldComparator<>("id", DbOrder.DESC);
	/**
	 * 根据id字段正向排序的比较器。
	 */
	public static final FieldComparator<HasIdGetter<?>> ID_ASC = new OneFieldComparator<>("id", DbOrder.ASC);

	public OneFieldComparator(final String fieldName, final DbOrder dbOrder) {
		list = L.asList(Tuple.of(fieldName, dbOrder));
	}

	@Override
	public List<TwoTuple<String, DbOrder>> getComparingFieldsNamesAndOrders() {
		return list;
	}
}