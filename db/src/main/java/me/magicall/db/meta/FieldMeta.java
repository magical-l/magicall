package me.magicall.db.meta;

import me.magicall.db.util.DbUtil;
import me.magicall.mark.Named;

public class FieldMeta implements Named {

	private final DbColumn column;

	private Class<?> type;

	public FieldMeta(final DbColumn column) {
		super();
		this.column = column;
	}

	@Override
	public String getName() {
		return DbUtil.dbNameToJavaName(column.getName());
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(final Class<?> type) {
		this.type = type;
	}

	public String getComment() {
		return column.getComment();
	}

	public int getLength() {
		return column.getLength();
	}

	public Object getDefaultValue() {
		return column.getDefaultValue();
	}

	public boolean getNullable() {
		return column.getNullable();
	}

	public boolean getHasDefaultValue() {
		return column.getHasDefaultValue();
	}
}
