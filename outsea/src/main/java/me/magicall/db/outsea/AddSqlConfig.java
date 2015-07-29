package me.magicall.db.outsea;

import java.util.Collection;
import java.util.List;

import me.magicall.db.FieldFilter;
import me.magicall.db.util.AboutNull;
import me.magicall.db.util.OptionOnExist;

public class AddSqlConfig<T> extends AbsSqlConfig<T> {

	private OptionOnExist optionOnExist = OptionOnExist.EXCEPTION;

	public AddSqlConfig(final String mainModelName) {
		super(mainModelName);
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

	@Override
	public void addOtherRefedModels(final List<T> otherRefedModels) {
		super.addOtherRefedModels(otherRefedModels);
	}

	@Override
	public void addOtherRefedModels(final T... otherRefedModels) {
		super.addOtherRefedModels(otherRefedModels);
	}

	@Override
	public void setRefedModels(final T refedModel, final T... otherRefedModels) {
		super.setRefedModels(refedModel, otherRefedModels);
	}

	@Override
	public List<T> getRefedModels() {
		return super.getRefedModels();
	}

	public OptionOnExist getOptionOnExist() {
		return optionOnExist;
	}

	public void setOptionOnExist(final OptionOnExist optionOnExist) {
		this.optionOnExist = optionOnExist;
	}

	@Override
	public List<T> getOtherNewValues() {
		return super.getOtherNewValues();
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
	public void setRefedModels(final T... refedModels) {
		super.setRefedModels(refedModels);
	}

	@Override
	public void setRefedModels(final Collection<? extends T> refedModels) {
		super.setRefedModels(refedModels);
	}

}
