package me.magicall.db.outsea;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.magicall.db.Condition;
import me.magicall.db.FieldComparator;
import me.magicall.db.util.PageInfo;

public class DelSqlConfig<T> extends AbsSqlConfig<T> {

	public DelSqlConfig(final String mainModelName) {
		super(mainModelName);
	}

	@Override
	public FieldComparator<? super T> getFieldComparator() {
		return super.getFieldComparator();
	}

	@Override
	public void setFieldComparator(final FieldComparator<? super T> fieldComparator) {
		super.setFieldComparator(fieldComparator);
	}

	@Override
	public PageInfo getPageInfo() {
		return super.getPageInfo();
	}

	@Override
	public void setPageInfo(final PageInfo pageInfo) {
		super.setPageInfo(pageInfo);
	}

	@Override
	public List<Condition> getConditions() {
		return super.getConditions();
	}

	@Override
	public void addConditions(final Condition... conditions) {
		super.addConditions(conditions);
	}

	@Override
	public void addConditions(final Collection<? extends Condition> conditions) {
		super.addConditions(conditions);
	}

	@Override
	public void addConditions(final Map<String, ?> params) {
		super.addConditions(params);
	}

}
