package me.magicall.db.outsea.springjdbc;

import me.magicall.db.Condition;
import me.magicall.db.FieldFilter;
import me.magicall.db.meta.DbColumn;
import me.magicall.db.meta.Key;
import me.magicall.db.meta.TableMeta;
import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.outsea.ModelMapTransformer;
import me.magicall.db.outsea.UpdateSqlConfig;
import me.magicall.db.outsea.springjdbc.SqlBuilder.ParamedSqlAndParams;
import me.magicall.db.util.AboutNull;
import me.magicall.db.util.DbUtil;
import me.magicall.util.kit.Kits;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Update<T> extends AbsDataAccessor<T, Integer, UpdateSqlConfig<T>> {

    private final SqlBuilder<UpdateSqlConfig<T>> sqlBuilder = sqlConfig -> {
        final String mainModelName = sqlConfig.getMainModelName();
        final TableMeta tableMeta = tableMetaAccessor.getTableMetaIgnoreCase(mainModelName);

        final T refedModel = sqlConfig.getRefedModel();
        final Map<String, Object> valueMap = modelMapTransformer.modelToMap(refedModel);
        if (Kits.MAP.isEmpty(valueMap)) {
            throw new IllegalArgumentException("can't parse refed model to map with value:" + refedModel);
        }

        final StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(modelNameTableNameTransformer.modelNameToTableName(mainModelName)).append(" SET ");

        final FieldFilter fieldFilter = sqlConfig.getFieldFilter();

        final Map<String, Object> namedParams = new HashMap<>();

        final Key primaryKey = tableMeta.getPrimaryKey();
        List<DbColumn> primaryKeyColumns;
        if (primaryKey == null) {
            primaryKeyColumns = Kits.LIST.emptyValue();
        } else {
            primaryKeyColumns = primaryKey.getColumns();
        }
        final List<DbColumn> columns = tableMeta.getColumns();
        boolean changed = false;
        for (final DbColumn column : columns) {
            if (primaryKeyColumns.contains(column)) {//主键不update
                continue;
            }
            final String columnName = column.getName();
            final String fieldName = fieldNameColumnNameTransformer.columnNameToFieldName(columnName);
            if (fieldFilter == null || fieldFilter.accept(tableMeta, column)) {
                if (valueMap.containsKey(fieldName)) {//若refedModel中某字段未设置值,不update它
                    final Object value = valueMap.get(fieldName);
                    final String paramedName = mainModelName + DbUtil.TABLE_NAME_COLUMN_NAME_SEPERATOR + columnName;
                    final AboutNull aboutNull = sqlConfig.getAboutNull();
                    if (value == null) {
                        if (aboutNull == AboutNull.ESCAPE) {
                            continue;
                        }
                        if (aboutNull == AboutNull.USE_DEFAULT_VALUE) {
                            final Object defaultValue = column.getDefaultValue();
                            if (defaultValue == null && !column.getNullable()) {
                                throw new IllegalArgumentException("column " + columnName + " cannot be null");
                            }
                            namedParams.put(paramedName, defaultValue);
                        } else if (aboutNull == AboutNull.STAY_NULL) {
                            namedParams.put(paramedName, null);
                        }
                    } else {
                        namedParams.put(paramedName, value);
                    }
                    sb.append(columnName).append('=').append(':' + paramedName).append(',');
                    changed = true;
                }
            }
        }
        if (changed) {
            sb.deleteCharAt(sb.length() - 1);
        } else {
            throw new IllegalArgumentException("nothing to update");
        }

        final List<Condition> conditions = sqlConfig.getConditions();
        SqlUtil.appendWhere(sb, tableMeta, fieldNameColumnNameTransformer, conditions, namedParams);

        return new ParamedSqlAndParams(sb.toString(), namedParams);
    };

    public Update(final NamedParameterJdbcOperations namedJdbc, final TableMetaAccessor tableMetaAccessor) {
        super(namedJdbc, tableMetaAccessor);
    }

    @Override
    public UpdateSqlConfig<T> createSqlConfig(final String mainModelName) {
        return new UpdateSqlConfig<>(mainModelName);
    }

    @Override
    protected Integer exe(final String sql, final Map<String, ?> params, final UpdateSqlConfig<T> sqlConfig) {
        return namedJdbc.update(sql, params);
    }

    @Override
    protected SqlBuilder<UpdateSqlConfig<T>> getSqlBuilder() {
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
