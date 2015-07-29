package me.magicall.db.outsea.springjdbc;

import java.util.HashMap;
import java.util.Map;

import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.outsea.DelSqlConfig;
import me.magicall.db.outsea.springjdbc.SqlBuilder.ParamedSqlAndParams;
import me.magicall.db.util.DbUtil;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class Del<T> extends AbsDataAccessor<T, Integer, DelSqlConfig<T>> {

	private final SqlBuilder<DelSqlConfig<T>> sqlBuilder = sqlConfig -> {
        final String mainModelName = sqlConfig.getMainModelName();
        final StringBuilder sb = DbUtil.buildDeleteFromTable(modelNameTableNameTransformer.modelNameToTableName(mainModelName));
        //where
        final Map<String, Object> namedParams = new HashMap<>();
        SqlUtil.appendWhere(sb, tableMetaAccessor.getTableMetaIgnoreCase(mainModelName),//
                fieldNameColumnNameTransformer, sqlConfig.getConditions(), namedParams);
        //order by
        DbUtil.appendOrderBy(sb, sqlConfig.getFieldComparator());
        //limit
        DbUtil.appendLimit(sb, sqlConfig.getPageInfo());

        return new ParamedSqlAndParams(sb.toString(), namedParams);
    };

	public Del(final NamedParameterJdbcOperations namedJdbc, final TableMetaAccessor tableMetaAccessor) {
		super(namedJdbc, tableMetaAccessor);
	}

	@Override
	public DelSqlConfig<T> createSqlConfig(final String mainModelName) {
		return new DelSqlConfig<>(mainModelName);
	}

	@Override
	protected Integer exe(final String sql, final Map<String, ?> params, final DelSqlConfig<T> sqlConfig) {
		return namedJdbc.update(sql, params);
	}

	@Override
	protected SqlBuilder<DelSqlConfig<T>> getSqlBuilder() {
		return sqlBuilder;
	}

}
