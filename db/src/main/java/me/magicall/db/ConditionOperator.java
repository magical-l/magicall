package me.magicall.db;

import java.util.List;

import me.magicall.db.util.DbUtil;
import me.magicall.util.kit.Kits;

/**
 * 负责构造where中的各种条件子句的格式
 * 
 * @author MaGiCalL
 */
public enum ConditionOperator {
	EQUALS("=", Object.class) {
		@Override
		public boolean isFit(final Object target, final List<?> refedValues) {
			return super.isFit(target, refedValues) && target.equals(refedValues.get(0));
		}
	}, //
	NOT_EQUALS("!=", EQUALS), //
	GT(">", Comparable.class) {
		@Override
		public boolean isFit(final Object target, final List<?> refedValues) {
			if (!super.isFit(target, refedValues)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			final Comparable<Comparable<?>> o1 = (Comparable<Comparable<?>>) target;
			final Comparable<?> o2 = (Comparable<?>) refedValues.get(0);
			return o1.compareTo(o2) > 0;
		}
	}, //
	GE(">=", Comparable.class) {
		@Override
		public boolean isFit(final Object target, final List<?> refedValues) {
			if (!super.isFit(target, refedValues)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			final Comparable<Comparable<?>> o1 = (Comparable<Comparable<?>>) target;
			final Comparable<?> o2 = (Comparable<?>) refedValues.get(0);
			return o1.compareTo(o2) >= 0;
		}
	}, //
	LT("<", GE), //
	LE("<=", GT), //
	BETWEEN(" BETWEEN ", Comparable.class, 2) {
		@Override
		public StringBuilder buildSqlUsingColumnName(final StringBuilder sb, final String resultColumnName,
				final SqlValueHandler appender, final List<?> refedValues) {
			if (Kits.COLL.isEmpty(refedValues)) {
				return sb;
			}
			sb.append(resultColumnName).append(sign);
			final String paramedName1 = appender.handle(sb, resultColumnName, 0, facadeValue(refedValues.get(0)));
			sb.append(DbUtil.namedParam(paramedName1));

			sb.append(" AND ");

			final String paramedName2 = appender.handle(sb, resultColumnName, 1, facadeValue(refedValues.get(1)));
			sb.append(DbUtil.namedParam(paramedName2));
			return sb;
		}

		@Override
		public boolean isFit(final Object target, final List<?> refedValues) {
			if (super.isFit(target, refedValues)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			final Comparable<Comparable<?>> o1 = (Comparable<Comparable<?>>) target;
			@SuppressWarnings("unchecked")
			final Comparable<Comparable<?>> o2 = (Comparable<Comparable<?>>) refedValues.get(0);
			@SuppressWarnings("unchecked")
			final Comparable<Comparable<?>> o3 = (Comparable<Comparable<?>>) refedValues.get(1);
			final Comparable<Comparable<?>> bigger;
			final Comparable<Comparable<?>> smaller;
			if (o2.compareTo(o3) > 0) {
				bigger = o2;
				smaller = o3;
			} else {
				bigger = o3;
				smaller = o2;
			}
			return o1.compareTo(smaller) >= 0 && o1.compareTo(bigger) < 0;
		}
	}, //
	IN(" IN ", Object.class) {
		@Override
		public StringBuilder buildSqlUsingColumnName(final StringBuilder sb, final String resultColumnName,
				final SqlValueHandler appender, final List<?> refedValues) {
			sb.append(resultColumnName).append(" IN(");

			if (Kits.COLL.isEmpty(refedValues)) {
				sb.append(" NULL ");
			} else {
				int index = 0;
				for (final Object refedValue : refedValues) {
					final String paramedName = appender.handle(sb, resultColumnName, index, facadeValue(refedValue));
					sb.append(DbUtil.namedParam(paramedName));
					sb.append(',');
					++index;
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			return sb.append(')');
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean isFit(final Object target, final List<?> refedValues) {
			if (super.isFit(target, refedValues)) {
				return false;
			}
			return Kits.LIST.contains((List<Object>) refedValues, target);
		}
	}, //
	IS(" IS ", Object.class) {
		@Override
		public boolean isFit(final Object target, final List<?> refedValues) {
			return super.isFit(target, refedValues) && target.equals(refedValues.get(0));
		}
	}, //
	IS_NOT(" IS NOT ", IS) {
		@Override
		public boolean isFit(final Object target, final List<?> refedValues) {
			return super.isFit(target, refedValues) && !target.equals(refedValues.get(0));
		}
	}, //
	STARTS_WITH(" LIKE ", String.class) {
		@Override
		public Object facadeValue(final Object value) {
			return value + "%";
		}

		@Override
		public boolean isFit(final Object target, final List<?> refedValues) {
			return super.isFit(target, refedValues)//
					&& Kits.STR.startsWithIgnoreCase(String.valueOf(target), String.valueOf(refedValues.get(0)), true);
		}
	}, //
	ENDS_WITH(" LIKE ", String.class) {
		@Override
		public Object facadeValue(final Object value) {
			return "%" + value;
		}

		@Override
		public boolean isFit(final Object target, final List<?> refedValues) {
			return super.isFit(target, refedValues) && //
					Kits.STR.endsWithIgnoreCase(String.valueOf(target), String.valueOf(refedValues.get(0)), true);
		}
	}, //
	CONTAINS(" LIKE ", String.class) {
		@Override
		public Object facadeValue(final Object value) {
			return "%" + value + '%';
		}

		@Override
		public boolean isFit(final Object target, final List<?> refedValues) {
			return super.isFit(target, refedValues) && //
					Kits.STR.containsIgnoreCase(String.valueOf(target), String.valueOf(refedValues.get(0)));
		}
	}, //
	NOT_IN(" NOT IN ", IN) {
		@Override
		public StringBuilder buildSqlUsingColumnName(final StringBuilder sb, final String resultColumnName,
				final SqlValueHandler appender, final List<?> refedValues) {
			return IN.buildSqlUsingColumnName(sb.append(" NOT "), resultColumnName, appender, refedValues);
		}
	}, //
	NOT_STARTS_WITH(" NOT LIKE ", STARTS_WITH) {
		@Override
		public Object facadeValue(final Object value) {
			return STARTS_WITH.facadeValue(value);
		}
	}, //
	NOT_ENDS_WITH(" NOT LIKE ", ENDS_WITH) {
		@Override
		public Object facadeValue(final Object value) {
			return ENDS_WITH.facadeValue(value);
		}
	}, //
	NOT_CONTAINS(" NOT LIKE ", CONTAINS) {
		@Override
		public Object facadeValue(final Object value) {
			return CONTAINS.facadeValue(value);
		}
	}, //
	;
	final String sign;
	final Class<?> clazz;
	final int atLeastArgsCount;
	final ConditionOperator reverse;

	private ConditionOperator(final String sign, final Class<?> clazz) {
		this(sign, clazz, 1);
	}

	private ConditionOperator(final String sign, final ConditionOperator reverse) {
		this.sign = sign;
		clazz = reverse.clazz;
		atLeastArgsCount = reverse.atLeastArgsCount;
		this.reverse = reverse;
	}

	private ConditionOperator(final String sign, final Class<?> clazz, final int argsCount) {
		this.sign = sign;
		this.clazz = clazz;
		atLeastArgsCount = argsCount;
		reverse = null;
	}

	/**
	 * 拼接sql
	 * 
	 * @param sb 用来拼接sql的StringBuilder
	 * @param resultColumnName
	 * @param sqlValueHandler 处理sql中"值"部分的处理器.
	 * @param refedValues 参考值列表
	 * @return
	 */
	public StringBuilder buildSqlUsingColumnName(final StringBuilder sb, final String resultColumnName,
			final SqlValueHandler sqlValueHandler, final List<?> refedValues) {
		if (Kits.COLL.isEmpty(refedValues)) {
			return sb;
		}
		sb.append(resultColumnName).append(sign);
		final String paramedName = sqlValueHandler.handle(sb, resultColumnName, 0, facadeValue(refedValues.get(0)));
		sb.append(DbUtil.namedParam(paramedName));
		return sb;
	}

	/**
	 * 修饰"值".此值最终会被用到sql.当以下情况时需要用此方法:
	 * 使用PrepareStatement写出如下sql : ...where name like ? 时,LIKE系列的ConditionOperator(STARTS_WITH、ENDS_WITH、CONTAINS……）会使用数据库通配符(%)替换sql占位符(?)
	 * 
	 * @param value 值
	 * @return
	 */
	public Object facadeValue(final Object value) {
		return reverse == null ? value : reverse.facadeValue(value);
	}

	/**
	 * 检查目标参数是否在参考值列表指定的范围之内
	 * 
	 * @param target 需要检查的数据目标
	 * @param refedValues 参考值列表
	 * @return
	 */
	public boolean isFit(final Object target, final List<?> refedValues) {
		if (reverse != null) {
			return !reverse.isFit(target, refedValues);
		}
		if (target == null) {
			return false;
		}
		final Class<?> targetClass = target.getClass();
		if (!clazz.isAssignableFrom(targetClass)) {
			return false;
		}
		if (refedValues.size() < atLeastArgsCount) {
			return false;
		}
		return true;
	}
}