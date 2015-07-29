package me.magicall.db.meta;

import java.util.List;

import me.magicall.convenient.BaseHasList;
import me.magicall.db.util.DbUtil;
import me.magicall.tagInterfaces.HasName;

public abstract class BaseDbMeta extends BaseHasList<DbColumn> implements HasName, HasColumns {

	protected String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public DbColumn[] getColumnsArray() {
		final List<DbColumn> fields = getList();
		return fields.toArray(new DbColumn[fields.size()]);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	public String getJavaName() {
		return DbUtil.dbNameToJavaName(getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BaseDbMeta other = (BaseDbMeta) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
