package me.magicall.db.util;

/**
 * order by语句的两个顺序枚举.
 * 
 * @author MaGiCalL
 */
public enum DbOrder /* implements SqlElement */{

	/**
	 * 正序,asc
	 */
	ASC, //
	/**
	 * 逆序,desc
	 */
	DESC, //
	;

	private final String sql;

	private DbOrder() {
		sql = ' ' + name() + ' ';
	}

//	@Override
	public String toSql() {
		return sql;
	}

//	@Override
	public StringBuilder appendTo(final StringBuilder sb) {
		return sb.append(sql);
	}
}
