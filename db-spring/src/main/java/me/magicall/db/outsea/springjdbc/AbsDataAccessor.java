package me.magicall.db.outsea.springjdbc;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.outsea.CommonNameTransformer;
import me.magicall.db.outsea.DataAccessor;
import me.magicall.db.outsea.FieldNameColumnNameTransformer;
import me.magicall.db.outsea.ModelMapTransformer;
import me.magicall.db.outsea.ModelNameTableNameTransformer;
import me.magicall.db.outsea.SqlConfig;
import me.magicall.db.outsea.springjdbc.SqlBuilder.ParamedSqlAndParams;
import me.magicall.db.util.DbUtil;
import me.magicall.lang.bean.FieldValueAccessor;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public abstract class AbsDataAccessor<T, R, C extends SqlConfig> implements DataAccessor<R, C> {

	protected NamedParameterJdbcOperations namedJdbc;
	protected TableMetaAccessor tableMetaAccessor;

	protected ModelMapTransformer<T> modelMapTransformer;
	protected FieldNameColumnNameTransformer fieldNameColumnNameTransformer = CommonNameTransformer.COMMON;
	protected ModelNameTableNameTransformer modelNameTableNameTransformer = CommonNameTransformer.COMMON;

	protected FieldValueAccessor<? super T> fieldValueAccessor = FieldValueAccessor.BEAN_VALUE_ACCESSOR;

	protected List<DataAccessTrigger<R, C>> triggers = new LinkedList<>();

	protected AbsDataAccessor() {

	}

	protected AbsDataAccessor(final NamedParameterJdbcOperations namedJdbc,//
			final TableMetaAccessor tableMetaAccessor) {
		super();
		this.namedJdbc = namedJdbc;
		this.tableMetaAccessor = tableMetaAccessor;
	}

	@Override
	public R exe(final C sqlConfig) {
		for (final DataAccessTrigger<R, C> t : triggers) {
			t.before(sqlConfig);
		}
		final ParamedSqlAndParams paramedSqlAndParams = getSqlBuilder().buildSql(sqlConfig);
		for (final DataAccessTrigger<R, C> t : triggers) {
			t.beforeExe(sqlConfig, paramedSqlAndParams);
		}
		final R result = exe(paramedSqlAndParams.paramedSql, paramedSqlAndParams.params, sqlConfig);
		for (final DataAccessTrigger<R, C> t : triggers) {
			t.afterExe(result, sqlConfig);
		}
		return result;
	}

	protected void addTrigger(final DataAccessTrigger<R, C> trigger) {
		triggers.add(trigger);
	}

	protected R exe(final String mainModelName) {
		return exe(createSqlConfig(mainModelName));
	}

	protected String javaNameToDbName(final String javaName) {
		return DbUtil.javaNameToDbName(javaName);
	}

	protected abstract R exe(final String sql, final Map<String, ?> params, C sqlConfig);

	protected abstract SqlBuilder<C> getSqlBuilder();

	protected NamedParameterJdbcOperations getNamedJdbc() {
		return namedJdbc;
	}

	protected void setNamedJdbc(final NamedParameterJdbcOperations namedJdbc) {
		this.namedJdbc = namedJdbc;
	}

	protected TableMetaAccessor getTableMetaAccessor() {
		return tableMetaAccessor;
	}

	protected void setTableMetaAccessor(final TableMetaAccessor tableMetaAccessor) {
		this.tableMetaAccessor = tableMetaAccessor;
	}

	protected ModelMapTransformer<T> getModelMapTransformer() {
		return modelMapTransformer;
	}

	protected void setModelMapTransformer(final ModelMapTransformer<T> modelMapTransformer) {
		this.modelMapTransformer = modelMapTransformer;
	}

	protected FieldNameColumnNameTransformer getFieldNameColumnNameTransformer() {
		return fieldNameColumnNameTransformer;
	}

	protected void setFieldNameColumnNameTransformer(final FieldNameColumnNameTransformer fieldNameColumnNameTransformer) {
		this.fieldNameColumnNameTransformer = fieldNameColumnNameTransformer;
	}

	protected ModelNameTableNameTransformer getModelNameTableNameTransformer() {
		return modelNameTableNameTransformer;
	}

	protected void setModelNameTableNameTransformer(final ModelNameTableNameTransformer modelNameTableNameTransformer) {
		this.modelNameTableNameTransformer = modelNameTableNameTransformer;
	}

	protected FieldValueAccessor<? super T> getFieldValueAccessor() {
		return fieldValueAccessor;
	}

	protected void setFieldValueAccessor(final FieldValueAccessor<? super T> fieldValueAccessor) {
		this.fieldValueAccessor = fieldValueAccessor;
	}
}
