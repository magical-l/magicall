package me.magicall.db.meta;

import java.util.List;

import me.magicall.convenient.BaseHasList;
import me.magicall.tagInterfaces.HasNameGetter;

public class ModelMeta extends BaseHasList<FieldMeta> implements HasNameGetter {

	private String name;

	private String comment;

	public List<FieldMeta> getFields() {
		return getList();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
