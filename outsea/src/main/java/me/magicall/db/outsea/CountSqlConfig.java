package me.magicall.db.outsea;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.magicall.db.Condition;

public class CountSqlConfig<T> extends AbsSqlConfig<T> {

	public CountSqlConfig(final String mainModelName) {
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
	public Collection<String> getOtherModelsNames() {
		return super.getOtherModelsNames();
	}

	@Override
	public void addOtherModelsNames(final Collection<String> otherModelsNames) {
		super.addOtherModelsNames(otherModelsNames);
	}

	@Override
	public void addOtherModelsNames(final String... otherModelsNames) {
		super.addOtherModelsNames(otherModelsNames);
	}

	@Override
	public void addConditions(final Map<String, ?> params) {
		super.addConditions(params);
	}

}
