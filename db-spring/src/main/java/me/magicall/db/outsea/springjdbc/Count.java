package me.magicall.db.outsea.springjdbc;

import java.util.HashMap;
import java.util.Map;

import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.outsea.CountSqlConfig;

import me.magicall.db.outsea.springjdbc.SqlBuilder.ParamedSqlAndParams;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class Count<T> extends AbsDataAccessor<T, Integer, CountSqlConfig<T>> {

	private final SqlBuilder<CountSqlConfig<T>> sqlBuilder = sqlConfig -> {
        final StringBuilder sb = new StringBuilder();
        final Map<String, Object> namedParams = new HashMap<>();
        //select from where
        SqlUtil.buildSelectCountFromWhere(sb, namedParams, tableMetaAccessor,//
                fieldNameColumnNameTransformer, sqlConfig);

        return new ParamedSqlAndParams(sb.toString(), namedParams);
    };

	public Count(final NamedParameterJdbcOperations namedJdbc, final TableMetaAccessor tableMetaAccessor) {
		super(namedJdbc, tableMetaAccessor);
	}

	@Override
	public CountSqlConfig<T> createSqlConfig(final String mainModelName) {
		return new CountSqlConfig<>(mainModelName);
	}

	@Override
	protected Integer exe(final String sql, final Map<String, ?> params, final CountSqlConfig<T> sqlConfig) {
		return namedJdbc.queryForInt(sql, params);
	}

	@Override
	protected SqlBuilder<CountSqlConfig<T>> getSqlBuilder() {
		return sqlBuilder;
	}
}
