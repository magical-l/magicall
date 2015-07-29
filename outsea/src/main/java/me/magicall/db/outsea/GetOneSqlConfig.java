package me.magicall.db.outsea;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.magicall.db.Condition;
import me.magicall.db.FieldComparator;
import me.magicall.db.FieldFilter;
import me.magicall.db.util.AboutNull;

public class GetOneSqlConfig<T> extends AbsSqlConfig<T> {

	public GetOneSqlConfig(final String mainModelName) {
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
	public List<Condition> getConditions() {
		return super.getConditions();
	}

	public void addConditions(final String key, final Object value) {
		super.addConditions(new Condition(key, value));
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
	public Collection<String> getOtherModelsNames() {
		return super.getOtherModelsNames();
	}

	@Override
	public void addOtherModelsNames(final String... otherModelsNames) {
		super.addOtherModelsNames(otherModelsNames);
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

}
