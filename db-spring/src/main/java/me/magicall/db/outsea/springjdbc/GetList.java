package me.magicall.db.outsea.springjdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.outsea.ListSqlConfig;
import me.magicall.db.outsea.ModelMapTransformer;
import me.magicall.db.outsea.springjdbc.SqlBuilder.ParamedSqlAndParams;
import me.magicall.db.outsea.springjdbc.SqlUtil.ModelMapping;
import me.magicall.db.util.DbUtil;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * 暂时发现有一个问题： 若在otherModelsNames里指定了某个model,而mainModel里它对应的外键id是null, 那么理论上应该是【取出】这个mainModel,这个mainModel的otherModelName的值为null, 但现在连这个mainModel也【取不出来】,
 * 因为最终sql的where中是main_model_table.other_model_id(在表中为null)=other_model_table.id(不为null),二者不可能相等.
 * 
 * @author MaGiCalL
 * @param <T>
 */
public class GetList<T> extends AbsDataAccessor<T, List<T>, ListSqlConfig<T>> {

	private static final ThreadLocal<Map<String, String>> LOCAL_ResultLabel_TO_FieldName_MAPPING = new ThreadLocal<>();
	private static final ThreadLocal<Map<String, ModelMapping>> LOCAL_modelName_TO_ModelMapping_MAPPING = new ThreadLocal<>();

	private final SqlBuilder<ListSqlConfig<T>> sqlBuilder = sqlConfig -> {
        final StringBuilder sb = new StringBuilder();
        final Map<String, Object> namedParams = new HashMap<>();

        final Map<String, ModelMapping> modelNameToModelMapping = SqlUtil.buildModelMappings(
                getTableMetaAccessor(), getFieldNameColumnNameTransformer(), sqlConfig);
        LOCAL_modelName_TO_ModelMapping_MAPPING.set(modelNameToModelMapping);
        //select from where
        final Map<String, String> resultLabelToFieldNameMapping = SqlUtil.buildSelectFromWhere(sb, namedParams,
                getFieldNameColumnNameTransformer(), sqlConfig, modelNameToModelMapping);
        LOCAL_ResultLabel_TO_FieldName_MAPPING.set(resultLabelToFieldNameMapping);
        //order by
        SqlUtil.appendOrderBy(sb, sqlConfig.getFieldComparator(), sqlConfig.getMainModelName(),
                getFieldNameColumnNameTransformer());
        //limit
        DbUtil.appendLimit(sb, sqlConfig.getPageInfo());

        return new ParamedSqlAndParams(sb.toString(), namedParams);
    };

	protected GetList() {
	}

	public GetList(final NamedParameterJdbcOperations namedJdbc, final TableMetaAccessor tableMetaAccessor) {
		super(namedJdbc, tableMetaAccessor);
	}

	@Override
	public ListSqlConfig<T> createSqlConfig(final String mainModelName) {
		return new ListSqlConfig<>(mainModelName);
	}

	@Override
	protected SqlBuilder<ListSqlConfig<T>> getSqlBuilder() {
		return sqlBuilder;
	}

	@Override
	protected List<T> exe(final String sql, final Map<String, ?> params, final ListSqlConfig<T> sqlConfig) {
		final Map<String, String> resultLabelToFieldNameMapping = LOCAL_ResultLabel_TO_FieldName_MAPPING.get();
		//service$id-service.id service$outDevice$id-service.outDevice.id
		final Map<String, ModelMapping> modelNameToModelMapping = LOCAL_modelName_TO_ModelMapping_MAPPING.get();
		final List<Map<String, Object>> list = getNamedJdbc().query(sql, params, new RowMapper<Map<String, Object>>() {
			@Override
			public Map<String, Object> mapRow(final ResultSet rs, final int rowNum) throws SQLException {
				return SqlUtil.mapRow(getTableMetaAccessor(), getFieldNameColumnNameTransformer(),//
						sqlConfig, resultLabelToFieldNameMapping, modelNameToModelMapping, rs);
			}
		});

		final String mainModelName = sqlConfig.getMainModelName();
		final List<T> rt = new ArrayList<>(list.size());
		for (final Map<String, Object> row : list) {
			rt.add(SqlUtil
					.handlerResultMap(resultLabelToFieldNameMapping, mainModelName, row, getModelMapTransformer()));
		}
		return rt;
	}

	@Override
	public ModelMapTransformer<T> getModelMapTransformer() {
		return super.getModelMapTransformer();
	}

	@Override
	public void setModelMapTransformer(final ModelMapTransformer<T> modelMapTransformer) {
		super.setModelMapTransformer(modelMapTransformer);
	}

	@Override
	public List<T> exe(final String mainModelName) {
		return super.exe(mainModelName);
	}

}
