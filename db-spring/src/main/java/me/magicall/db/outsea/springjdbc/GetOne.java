package me.magicall.db.outsea.springjdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.outsea.GetOneSqlConfig;
import me.magicall.db.outsea.ModelMapTransformer;
import me.magicall.db.outsea.springjdbc.SqlBuilder.ParamedSqlAndParams;
import me.magicall.db.outsea.springjdbc.SqlUtil.ModelMapping;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class GetOne<T> extends AbsDataAccessor<T, T, GetOneSqlConfig<T>> {

	private static final ThreadLocal<Map<String, String>> LOCAL_ResultLabel_TO_FieldName_MAPPING = new ThreadLocal<>();
	private static final ThreadLocal<Map<String, ModelMapping>> LOCAL_modelName_TO_ModelMapping_MAPPING = new ThreadLocal<>();

	private final SqlBuilder<GetOneSqlConfig<T>> sqlBuilder = sqlConfig -> {
        final StringBuilder sb = new StringBuilder();
        final Map<String, Object> namedParams = new HashMap<>();

        final Map<String, ModelMapping> modelNameToModelMapping = SqlUtil.buildModelMappings(tableMetaAccessor, fieldNameColumnNameTransformer, sqlConfig);
        LOCAL_modelName_TO_ModelMapping_MAPPING.set(modelNameToModelMapping);
        //select from where
        final Map<String, String> resultLabelToFieldNameMapping = SqlUtil.buildSelectFromWhere(sb, namedParams, fieldNameColumnNameTransformer, sqlConfig,
                modelNameToModelMapping);
        LOCAL_ResultLabel_TO_FieldName_MAPPING.set(resultLabelToFieldNameMapping);

        return new ParamedSqlAndParams(sb.toString(), namedParams);
    };

	public GetOne(final NamedParameterJdbcOperations namedJdbc, final TableMetaAccessor tableMetaAccessor) {
		super(namedJdbc, tableMetaAccessor);
	}

	@Override
	public GetOneSqlConfig<T> createSqlConfig(final String mainModelName) {
		return new GetOneSqlConfig<>(mainModelName);
	}

	@Override
	protected T exe(final String sql, final Map<String, ?> params, final GetOneSqlConfig<T> sqlConfig) {
		final Map<String, String> resultLabelToFieldNameMapping = LOCAL_ResultLabel_TO_FieldName_MAPPING.get();
		final Map<String, ModelMapping> modelNameToModelMapping = LOCAL_modelName_TO_ModelMapping_MAPPING.get();
		try {
			final Map<String, Object> resultMap = namedJdbc.queryForObject(sql, params, new RowMapper<Map<String, Object>>() {
				@Override
				public Map<String, Object> mapRow(final ResultSet rs, final int rowNum) throws SQLException {
					return SqlUtil.mapRow(tableMetaAccessor, fieldNameColumnNameTransformer,//
							sqlConfig, resultLabelToFieldNameMapping, modelNameToModelMapping, rs);
				}
			});
			return SqlUtil.handlerResultMap(resultLabelToFieldNameMapping, sqlConfig.getMainModelName(), resultMap, modelMapTransformer);
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	protected SqlBuilder<GetOneSqlConfig<T>> getSqlBuilder() {
		return sqlBuilder;
	}

	@Override
	public ModelMapTransformer<T> getModelMapTransformer() {
		return super.getModelMapTransformer();
	}

	@Override
	public void setModelMapTransformer(final ModelMapTransformer<T> modelMapTransformer) {
		super.setModelMapTransformer(modelMapTransformer);
	}

}
