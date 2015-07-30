package me.magicall.db.outsea;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.magicall.db.Condition;
import me.magicall.db.FieldComparator;
import me.magicall.db.FieldFilter;
import me.magicall.db.util.AboutNull;
import me.magicall.db.util.PageInfo;

public class UpdateSqlConfig<T> extends AbsSqlConfig<T> {

	public UpdateSqlConfig(final String mainModelName) {
		super(mainModelName);
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
	public AboutNull getAboutNull() {
		return super.getAboutNull();
	}

	@Override
	public void setAboutNull(final AboutNull aboutNull) {
		super.setAboutNull(aboutNull);
	}

	@Override
	public T getRefedModel() {
		return super.getRefedModel();
	}

	@Override
	public void setRefedModel(final T refedModel) {
		super.setRefedModel(refedModel);
	}

	@SafeVarargs
	@Override
	public final void setRefedModels(final T... refedValues) {
		super.setRefedModels(refedValues);
	}

	@Override
	public void setRefedModels(final Collection<? extends T> refedModelValues) {
		super.setRefedModels(refedModelValues);
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
	public FieldFilter getFieldFilter() {
		return super.getFieldFilter();
	}

	@Override
	public void setFieldFilter(final FieldFilter fieldFilter) {
		super.setFieldFilter(fieldFilter);
	}

	@Override
	public void addConditions(final Map<String, ?> params) {
		super.addConditions(params);
	}

	@Override
	public List<T> getOtherNewValues() {
		return super.getOtherNewValues();
	}

	@Override
	public List<T> getRefedModels() {
		return super.getRefedModels();
	}
}
