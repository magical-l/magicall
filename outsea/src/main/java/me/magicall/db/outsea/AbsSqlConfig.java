package me.magicall.db.outsea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.magicall.coll.CollFactory;
import me.magicall.coll.CollFactory.L;
import me.magicall.db.Condition;
import me.magicall.db.FieldComparator;
import me.magicall.db.FieldFilter;
import me.magicall.db.util.AboutNull;
import me.magicall.db.util.PageInfo;
import me.magicall.util.ArrayUtil;
import me.magicall.util.kit.Kits;

public class AbsSqlConfig<T> implements SqlConfig {

	protected final String mainModelName;
	protected Collection<String> otherModelsNames;
	/**
	 * 指定如何处理model或结果中为null的字段。
	 * 对于insert/update，抛弃意味着使用数据库自己的默认值，如果数据库没有设置该字段的默认值，通常数据库会抛出异常。
	 * 对于select，会将数据库中值为null的字段置为一个默认值。数字型的置为0，字符串型的置为""（空字符串），boolean置为false，char置为空格，时间置为new Date(0L)
	 */
	protected AboutNull aboutNull = AboutNull.USE_DEFAULT_VALUE;
	/**
	 * 一个Comparator，用来给结果排序，在sql里会用来构建成order by 子句。
	 */
	protected FieldComparator<? super T> fieldComparator;
	/**
	 * 用来分页的东西，最终会用来构建成limit子句。
	 */
	protected PageInfo pageInfo;
	/**
	 * 多个model，支持model.id这种语法，支持驼峰式命名、下划线命名，支持名词所有格（'s）命名法、空格命名法。
	 * 例：
	 * 普通：user.name
	 * 驼峰式命名：userBorn.birthDay
	 * 下划线(db风格)命名：user_born.birth_month,user_Born.birth_Year
	 * 名词所有格命名法：user's age（相当于user.age）
	 * 空格命名法：user born.home town（相当于userBorn.homeTown）
	 * 在sql中最终会构成select子句、from子句。
	 */
	/**
	 * 条件，在sql中最终会用来构建成where子句。
	 */
	protected List<Condition> conditions;

	protected T refedModel;
	protected List<T> otherRefedModels;

	protected FieldFilter fieldFilter;

	protected AbsSqlConfig(final String mainModelName) {
		super();
		this.mainModelName = mainModelName;
	}

	protected FieldComparator<? super T> getFieldComparator() {
		return fieldComparator;
	}

	protected void setFieldComparator(final FieldComparator<? super T> fieldComparator) {
		this.fieldComparator = fieldComparator;
	}

	protected PageInfo getPageInfo() {
		return pageInfo;
	}

	protected void setPageInfo(final PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	protected List<Condition> checkConditions() {
		if (conditions == null) {
			conditions = new ArrayList<>();
		}
		return conditions;
	}

	protected List<Condition> getConditions() {
		return checkConditions();
	}

	protected void addConditions(final Condition... conditions) {
		addConditions(L.asList(conditions));
	}

	protected void addConditions(final Collection<? extends Condition> conditions) {
		checkConditions().addAll(conditions);
	}

	protected void addConditions(final Map<String, ?> params) {
		final List<Condition> conditions = checkConditions();
		for (final Entry<String, ?> e : params.entrySet()) {
			conditions.add(new Condition(e.getKey(), e.getValue()));
		}
	}

	@Override
	public String getMainModelName() {
		return mainModelName;
	}

	protected AboutNull getAboutNull() {
		return aboutNull;
	}

	protected void setAboutNull(final AboutNull aboutNull) {
		this.aboutNull = aboutNull;
	}

	protected Collection<String> checkOtherModelsNames() {
		if (otherModelsNames == null) {
			otherModelsNames = new LinkedHashSet<>();
		}
		return otherModelsNames;
	}

	protected Collection<String> getOtherModelsNames() {
		return checkOtherModelsNames();
	}

	protected void addOtherModelsNames(final Collection<String> otherModelsNames) {
		final Collection<String> checkedOtherModelsNames = checkOtherModelsNames();
		for (final String modelName : otherModelsNames) {
			if (!Kits.STR.isEmpty(modelName)) {
				checkedOtherModelsNames.add(modelName);
			}
		}
	}

	protected void addOtherModelsNames(final String... otherModelsNames) {
		addOtherModelsNames(L.asList(otherModelsNames));
	}

	protected T getRefedModel() {
		return refedModel;
	}

	protected void setRefedModel(final T refedModel) {
		this.refedModel = refedModel;
	}

	protected List<T> checkOtherRefedModels() {
		if (otherRefedModels == null) {
			otherRefedModels = new ArrayList<>();
		}
		return otherRefedModels;
	}

	protected List<T> getOtherNewValues() {
		return checkOtherRefedModels();
	}

	protected void addOtherRefedModels(final List<T> otherRefedModels) {
		checkOtherRefedModels().addAll(otherRefedModels);
	}

	protected void addOtherRefedModels(final T... otherRefedModels) {
		addOtherRefedModels(L.asList(otherRefedModels));
	}

	protected void setRefedModels(final T refedModel, final T... otherRefedModels) {
		if (this.refedModel == null) {
			this.refedModel = refedModel;
			addOtherRefedModels(otherRefedModels);
		} else {
			final List<T> list = checkOtherRefedModels();
			list.add(refedModel);
			addOtherRefedModels(otherRefedModels);
		}
	}

	protected void setRefedModels(final T... refedModels) {
		if (ArrayUtil.isEmpty(refedModels)) {
			return;
		}
		if (refedModel == null) {
			refedModel = refedModels[0];
			for (int i = 1; i < refedModels.length; ++i) {
				checkOtherRefedModels().add(refedModels[i]);
			}
		} else {
			final List<T> list = checkOtherRefedModels();
			list.addAll(L.asList(refedModels));
		}
	}

	protected void setRefedModels(final Collection<? extends T> refedModels) {
		if (Kits.COLL.isEmpty(refedModels)) {
			return;
		}
		final Iterator<? extends T> iterator = refedModels.iterator();
		if (refedModel == null) {
			refedModel = iterator.next();
			while (iterator.hasNext()) {
				checkOtherRefedModels().add(iterator.next());
			}
		} else {
			final List<T> list = checkOtherRefedModels();
			list.addAll(refedModels);
		}
	}

	protected List<T> getRefedModels() {
		if (Kits.LIST.isEmpty(otherRefedModels)) {
			return L.asList(refedModel);
		} else {
			final List<T> rt = new ArrayList<>(1 + otherRefedModels.size());
			rt.add(refedModel);
			rt.addAll(otherRefedModels);
			return rt;
		}
	}

	protected FieldFilter getFieldFilter() {
		return fieldFilter;
	}

	protected void setFieldFilter(final FieldFilter fieldFilter) {
		this.fieldFilter = fieldFilter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aboutNull == null ? 0 : aboutNull.hashCode());
		result = prime * result + (conditions == null ? 0 : conditions.hashCode());
		result = prime * result + (fieldComparator == null ? 0 : fieldComparator.hashCode());
		result = prime * result + (fieldFilter == null ? 0 : fieldFilter.hashCode());
		result = prime * result + (mainModelName == null ? 0 : mainModelName.hashCode());
		result = prime * result + (otherModelsNames == null ? 0 : otherModelsNames.hashCode());
		result = prime * result + (otherRefedModels == null ? 0 : otherRefedModels.hashCode());
		result = prime * result + (pageInfo == null ? 0 : pageInfo.hashCode());
		result = prime * result + (refedModel == null ? 0 : refedModel.hashCode());
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
		final AbsSqlConfig<?> other = (AbsSqlConfig<?>) obj;
		if (aboutNull != other.aboutNull) {
			return false;
		}
		if (conditions == null) {
			if (other.conditions != null) {
				return false;
			}
		} else if (!conditions.equals(other.conditions)) {
			return false;
		}
		if (fieldComparator == null) {
			if (other.fieldComparator != null) {
				return false;
			}
		} else if (!fieldComparator.equals(other.fieldComparator)) {
			return false;
		}
		if (fieldFilter == null) {
			if (other.fieldFilter != null) {
				return false;
			}
		} else if (!fieldFilter.equals(other.fieldFilter)) {
			return false;
		}
		if (mainModelName == null) {
			if (other.mainModelName != null) {
				return false;
			}
		} else if (!mainModelName.equals(other.mainModelName)) {
			return false;
		}
		if (otherModelsNames == null) {
			if (other.otherModelsNames != null) {
				return false;
			}
		} else if (!otherModelsNames.equals(other.otherModelsNames)) {
			return false;
		}
		if (otherRefedModels == null) {
			if (other.otherRefedModels != null) {
				return false;
			}
		} else if (!otherRefedModels.equals(other.otherRefedModels)) {
			return false;
		}
		if (pageInfo == null) {
			if (other.pageInfo != null) {
				return false;
			}
		} else if (!pageInfo.equals(other.pageInfo)) {
			return false;
		}
		if (refedModel == null) {
			if (other.refedModel != null) {
				return false;
			}
		} else if (!refedModel.equals(other.refedModel)) {
			return false;
		}
		return true;
	}

}
