package me.magicall.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.L;
import me.magicall.util.ArrayUtil;

/**
 * 条件。
 * 最终会用来构建成where子句。
 * 例子：
 * Condition《Date》 c=new Condition《Date》("startTime",ConditionOperator.BETWEEN, new Date(0L), new Date());
 * 表示 where start_time between '1970-01-01 00:00:00' and now()
 * 
 * @author MaGiCalL
 */
public class Condition {

	private String fieldName;

	private ConditionOperator conditionOperator;
	/**
	 * 参考值.
	 */
	private List<Object> refedValues;

	public Condition() {
		super();
	}

	public Condition(final String fieldName, final ConditionOperator conditionOperator, final Object refedValue,
			final Object... refedValues) {
		this.fieldName = fieldName;
		this.conditionOperator = conditionOperator;
		final List<Object> list = new ArrayList<>();
		if (refedValue == null) {
			list.add(null);
		} else {
			final List<Object> castToList = castToList(refedValue);
			list.addAll(castToList);
		}
		for (final Object o : refedValues) {
			list.add(o);
		}
		this.refedValues = list;
	}

	public Condition(final String fieldName, final Object refedValues) {
		this.fieldName = fieldName;
		if (String.valueOf(refedValues).equalsIgnoreCase("null")) {
			conditionOperator = ConditionOperator.IS;
			this.refedValues = L.asList((Object) null);
		} else if (String.valueOf(refedValues).equalsIgnoreCase("notnull")) {
			conditionOperator = ConditionOperator.IS_NOT;
			this.refedValues = L.asList((Object) null);
		} else if (refedValues instanceof Object[]) {
			conditionOperator = ConditionOperator.IN;
			this.refedValues = L.asList((Object[]) refedValues);
		} else if (refedValues instanceof Collection<?>) {
			conditionOperator = ConditionOperator.IN;
			this.refedValues = new ArrayList<>((Collection<?>) refedValues);
		} else {
			conditionOperator = ConditionOperator.EQUALS;
			final List<Object> list = new ArrayList<>(1);
			list.add(refedValues);
			this.refedValues = list;
		}
	}

	public Condition(final String fieldName, final Object... refedValues) {
		this.fieldName = fieldName;
		conditionOperator = ConditionOperator.IN;
		this.refedValues = L.asList(refedValues);
	}

	public Condition(final String fieldName, final Collection<?> refedValues) {
		this.fieldName = fieldName;
		conditionOperator = ConditionOperator.IN;
		this.refedValues = new ArrayList<>(refedValues);
	}

	public Condition(final String fieldName) {
		this.fieldName = fieldName;
		conditionOperator = ConditionOperator.IS_NOT;
		refedValues = L.asList((Object) null);
	}

	public void setReferModels(final Object... referValues) {
		refedValues = Arrays.asList(referValues);
	}

	public List<?> getRefedValues() {
		return refedValues;
	}

	public void setRefedValues(final List<Object> referValues) {
		refedValues = referValues;
	}

	public ConditionOperator getConditionOperator() {
		return conditionOperator;
	}

	public void setConditionOperator(final ConditionOperator conditionOperator) {
		this.conditionOperator = conditionOperator;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(final String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String toString() {
		return fieldName + ' ' + conditionOperator + ' ' + refedValues;
	}

	private List<Object> castToList(final Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Collection<?>) {
			return new ArrayList<>((Collection<?>) o);
		}
		if (o.getClass().isArray()) {
			return ArrayUtil.asList(o);
		}
		if (o instanceof Enumeration<?>) {
			final Enumeration<?> e = (Enumeration<?>) o;
			final List<Object> list = new ArrayList<>();
			while (e.hasMoreElements()) {
				list.add(e.nextElement());
			}
			return list;
		}
		return L.asList(o);
	}
}
