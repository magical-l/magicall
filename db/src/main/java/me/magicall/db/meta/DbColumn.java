package me.magicall.db.meta;

import me.magicall.db.util.FieldType;
import me.magicall.mark.Renamable;
import me.magicall.util.kit.Kits;

public class DbColumn implements Renamable, HasComment {

	private String name;
	private FieldType type;
	private int length;
	private Object defaultValue;
	private Boolean hasDefaultValue;
//	private Collaction collaction;
	private boolean nullable;
	private boolean unsigned;
	private boolean autoInc;
	private boolean zeroFill;
	private String comment;

	public DbColumn() {
		super();
	}

	public DbColumn(final DbColumn other) {
		this();
		setAutoInc(other.getAutoInc());
		setComment(other.getComment());
		setDefaultValue(other.getDefaultValue());
		setLength(other.getLength());
		setName(other.getName());
		setNullable(other.getNullable());
		setType(other.getType());
		setUnsigned(other.getUnsigned());
		setZeroFill(other.getZeroFill());
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("`").append(getName()).append('`').append(' ');
		sb.append(getType()).append(' ');

		final int len = getLength();
		if (len > 0) {
			sb.append('(').append(len).append(')').append(' ');
		}

		if (getUnsigned()) {
			sb.append("unsigned ");
		}

		if (getZeroFill()) {
			sb.append("zerofill ");
		}

		if (!getNullable()) {
			sb.append("NOT NULL ");
		}

		final Object defValue = getDefaultValue();
		if (defValue != null) {
			sb.append(" DEFAULT '").append(defValue).append("' ");
		}

		final String comment = getComment();
		if (!Kits.STR.isEmpty(comment)) {
			sb.append("COMMENT '").append(comment).append("' ");
		}
		return sb.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public FieldType getType() {
		return type;
	}

	public void setType(final FieldType type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(final int length) {
		this.length = length;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(final Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean getNullable() {
		return nullable;
	}

	public void setNullable(final boolean nullable) {
		this.nullable = nullable;
	}

	public boolean getUnsigned() {
		return unsigned;
	}

	public void setUnsigned(final boolean unsigned) {
		this.unsigned = unsigned;
	}

	public boolean getAutoInc() {
		return autoInc;
	}

	public void setAutoInc(final boolean autoInc) {
		this.autoInc = autoInc;
	}

	public boolean getZeroFill() {
		return zeroFill;
	}

	public void setZeroFill(final boolean zeroFill) {
		this.zeroFill = zeroFill;
	}

	public void setHasDefaultValue(final boolean hasDefaultValue) {
		this.hasDefaultValue = hasDefaultValue;
	}

	public boolean getHasDefaultValue() {
		return hasDefaultValue == null ? getDefaultValue() != null : hasDefaultValue;
	}
}
