package me.magicall.db.meta;

import java.util.List;

/**
 * 数据库表的"键"
 * 
 * @author MaGiCalL
 */
public class Key extends BaseDbMetaWithMultiColumns {

	public static enum KeyType {
		/**
		 * 主键
		 */
		PRIMARY,
		/**
		 * 唯一键
		 */
		UNIQUE,
		/**
		 * 普通键
		 */
		COMMON;
	}

	private KeyType type;

	public List<DbColumn> getColumns() {
		return list;
	}

	public void setColumns(final List<DbColumn> columns) {
		list = columns;
	}

	public boolean getUnique() {
		return type != KeyType.COMMON;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
//		UNIQUE KEY `name` (`name`,`address`),

		final KeyType type = getType();
		if (type != KeyType.COMMON) {
			sb.append(type).append(' ');
		}
		sb.append("KEY ");
		if (type != KeyType.PRIMARY) {
			sb.append('`').append(getName()).append('`');
		}
		sb.append('(');
		final List<DbColumn> fields = getColumns();
		for (final DbColumn f : fields) {
			sb.append('`').append(f.getName()).append("`,");
		}
		return sb.deleteCharAt(sb.length() - 1).append(')').toString();
	}

	public KeyType getType() {
		return type;
	}

	public void setType(final KeyType type) {
		this.type = type;
	}
}
