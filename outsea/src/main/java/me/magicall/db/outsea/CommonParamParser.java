package me.magicall.db.outsea;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.L;
import me.magicall.db.Condition;
import me.magicall.db.ConditionOperator;

import com.google.common.collect.Lists;

public enum CommonParamParser implements ParamParser {
	NULL_VALUE {
		@Override
		public boolean accept(final String paramName, final Object paramValue) {
			return String.valueOf(paramValue).equalsIgnoreCase("null");
		}

		@Override
		public Condition parse(final String paramName, final Object paramValue) {
			return new Condition(paramName, ConditionOperator.IS, L.asList((Object) null));
		}
	}, //
	NOT_NULL_VALUE {
		@Override
		public boolean accept(final String paramName, final Object paramValue) {
			return String.valueOf(paramValue).equalsIgnoreCase("notnull");
		}

		@Override
		public Condition parse(final String paramName, final Object paramValue) {
			return new Condition(paramName, ConditionOperator.IS_NOT, L.asList((Object) null));
		}
	}, //
	ARR_VALUE {
		@Override
		public boolean accept(final String paramName, final Object paramValue) {
			return paramValue instanceof Object[] || paramValue.getClass().isArray();
		}

		@Override
		public Condition parse(final String paramName, final Object paramValue) {
			if (paramValue instanceof Object[]) {
				return new Condition(paramName, ConditionOperator.IN, L.asList((Object[]) paramValue));
			}
			//基本类型数组
			final int len = Array.getLength(paramValue);
			final List<Object> list = Lists.newArrayListWithExpectedSize(len);
			for (int i = 0; i < len; ++i) {
				list.add(Array.get(paramValue, i));
			}
			return new Condition(paramName, ConditionOperator.IN, list);
		}
	}, //
	COLL_VALUE {
		@Override
		public boolean accept(final String paramName, final Object paramValue) {
			return paramValue instanceof Iterable<?>;
		}

		@Override
		public Condition parse(final String paramName, final Object paramValue) {
			final List<Object> list = new LinkedList<>();
			final Iterable<?> iterable = (Iterable<?>) paramValue;
			for (final Object name2 : iterable) {
				list.add(name2);
			}
			return new Condition(paramName, ConditionOperator.IN, list);
		}
	}, //
	;
}
