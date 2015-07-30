package me.magicall.db.outsea.springjdbc;

import me.magicall.db.FieldFilter;
import me.magicall.db.meta.DbColumn;
import me.magicall.db.meta.Key;
import me.magicall.db.meta.TableMeta;
import me.magicall.db.meta.TableMetaAccessor;
import me.magicall.db.outsea.AddSqlConfig;
import me.magicall.db.outsea.ModelMapTransformer;
import me.magicall.db.outsea.springjdbc.SqlBuilder.ParamedSqlAndParams;
import me.magicall.db.util.DbUtil;
import me.magicall.db.util.OptionOnExist;
import me.magicall.lang.bean.FieldValueAccessor;
import me.magicall.util.kit.Kits;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Add<T> extends AbsDataAccessor<T, Integer, AddSqlConfig<T>> {

	private final SqlBuilder<AddSqlConfig<T>> sqlBuilder = sqlConfig -> {
        final String mainModelName = sqlConfig.getMainModelName();
        final TableMeta tableMeta = tableMetaAccessor.getTableMetaIgnoreCase(mainModelName);
        if (tableMeta == null) {
            throw new IllegalArgumentException("no such table");
        }
        //insert | insert ignore
        final StringBuilder sb = new StringBuilder("insert ");
        final OptionOnExist onExist = sqlConfig.getOptionOnExist();
        if (onExist == OptionOnExist.IGNORE) {
            sb.append("ignore ");
        }
        //into fields
        final FieldFilter fieldFilter = sqlConfig.getFieldFilter();
        sb.append("into ").append(tableMeta.getName()).append('(');
        final List<DbColumn> columns = tableMeta.getColumns();
        final List<DbColumn> columnsToUse = new ArrayList<>(columns.size());
        if (fieldFilter == null) {
            for (final DbColumn column : columns) {
                sb.append(DbUtil.quoteDbName(column.getName())).append(',');
                columnsToUse.add(column);
            }
        } else {
            for (final DbColumn column : columns) {
                final String columnName = column.getName();
                if (fieldFilter.accept(tableMeta, column)) {
                    sb.append(DbUtil.quoteDbName(columnName)).append(',');
                    columnsToUse.add(column);
                }
            }
        }
        sb.deleteCharAt(sb.length() - 1)
        //values
                .append(")values");

        final Map<String, Object> namedParams = new HashMap<>();

        final T newValue = sqlConfig.getRefedModel();
        appendNewValue(sb, mainModelName, newValue, columnsToUse, namedParams);
        //more values
        final List<T> otherNewValues = sqlConfig.getOtherNewValues();
        if (!Kits.LIST.isEmpty(otherNewValues)) {
            int index = 0;
            for (final T t : otherNewValues) {
                sb.append(',');
                appendNewValue(sb, mainModelName + index, t, columnsToUse, namedParams);
                ++index;
            }
        }
        //on duplicate key
        if (onExist == OptionOnExist.REPLACE) {
            sb.append(" on duplicate key update ");
            final Key primaryKey = tableMeta.getPrimaryKey();
            List<DbColumn> primaryKeyColumns;
            if (primaryKey == null) {
                primaryKeyColumns = Kits.LIST.emptyValue();
            } else {
                primaryKeyColumns = primaryKey.getColumns();
            }
            for (final DbColumn column : columnsToUse) {
                if (primaryKeyColumns.contains(column)) {//主键不update
                    continue;
                }
                final String quotedColumnName = DbUtil.quoteDbName(column.getName());
                sb.append(quotedColumnName).append("=values(").append(quotedColumnName).append("),");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        return new ParamedSqlAndParams(sb.toString(), namedParams);
    };

	private void appendNewValue(final StringBuilder sb, //
			final String modelName, final T newModel, final List<DbColumn> columnsToUse,//
			final Map<String, Object> namedParams) {
		sb.append('(');
		final Map<String, Object> valueMap = modelMapTransformer.modelToMap(newModel);

		boolean changed = false;
		for (final DbColumn column : columnsToUse) {//是该表的字段才管它.
			final String columnName = column.getName();
			final String paramedName = modelName + DbUtil.TABLE_NAME_COLUMN_NAME_SEPERATOR + columnName;
			sb.append(':' + paramedName).append(',');

			final String fieldName = fieldNameColumnNameTransformer.columnNameToFieldName(columnName);
			final Object value = valueMap.get(fieldName);
			if (value == null && !column.getNullable() && !column.getHasDefaultValue()) {
				throw new EmptyValueException(column);
			}
			namedParams.put(paramedName, value);

			changed = true;
		}
		sb.deleteCharAt(sb.length() - 1);//it is '(' or ','
		if (changed) {
			sb.append(')');
		}
	}

	public Add(final NamedParameterJdbcOperations namedJdbc, final TableMetaAccessor tableMetaAccessor) {
		super(namedJdbc, tableMetaAccessor);
	}

	@Override
	public AddSqlConfig<T> createSqlConfig(final String mainModelName) {
		return new AddSqlConfig<>(mainModelName);
	}

	@Override
	protected Integer exe(final String sql, final Map<String, ?> params, final AddSqlConfig<T> sqlConfig) {
		//注:mysql返回的自增id在以下情况下不太对:
		//同时插入多条记录,并且其中有一条记录设置了主键;
		//或 在insert on duplicate key情况下,有记录触发了update
		//或 该表有联合主键,其中一个字段是自增的,另一个字段不是自增的,并且同时插入多条记录,并且多条记录中那个自增的字段是相等的,此时返回的自增key不正确.
		final OptionOnExist onExist = sqlConfig.getOptionOnExist();
		if (onExist != OptionOnExist.IGNORE && onExist != OptionOnExist.REPLACE) {
			final TableMeta tableMeta = tableMetaAccessor.getTableMetaIgnoreCase(sqlConfig.getMainModelName());
			final Key key = tableMeta.getPrimaryKey();
			final List<DbColumn> primaryKeyColumns = key.getColumns();
			if (primaryKeyColumns.size() > 1) {
				//来到这里有可能是:该表有联合主键,其中一个字段是自增的,另一个字段不是自增的,并且同时插入多条记录,并且多条记录中那个自增的字段是相等的,此时返回的自增key不正确.
				return namedJdbc.update(sql, params);
			}
			final List<T> newValues = sqlConfig.getRefedModels();
			if (hasId(newValues, primaryKeyColumns)) {//这里有可能是"同时插入多条记录,并且其中有一条记录设置了主键"
				return namedJdbc.update(sql, params);
			} else {
				//填充自增主键
				final KeyHolder keyHolder = new GeneratedKeyHolder();
				final ParamedSqlAndParams paramedSqlAndParams = getSqlBuilder().buildSql(sqlConfig);
				final int changed = namedJdbc.update(paramedSqlAndParams.paramedSql,//
						new MapSqlParameterSource(paramedSqlAndParams.params), keyHolder);
				final List<Map<String, Object>> keyList = keyHolder.getKeyList();
				int index = 0;
				for (final T t : newValues) {
					final Map<String, Object> returnKey = keyList.get(index++);
					for (final DbColumn column : primaryKeyColumns) {
						if (column.getAutoInc()) {
							final String columnName = column.getName();
							final String fieldName = fieldNameColumnNameTransformer.columnNameToFieldName(columnName);
							fieldValueAccessor.setValue(t, fieldName, returnKey.values().iterator().next());
						}
					}
				}
				return changed;
			}
		} else {//来到这里有可能是"insert on duplicate key情况下,有记录触发了update"
			return namedJdbc.update(sql, params);
		}
	}

	@Override
	protected SqlBuilder<AddSqlConfig<T>> getSqlBuilder() {
		return sqlBuilder;
	}

	private boolean hasId(final List<T> newValues, final List<DbColumn> primaryKeyColumns) {
		for (final T t : newValues) {
			for (final DbColumn column : primaryKeyColumns) {
				final String fieldName = fieldNameColumnNameTransformer.columnNameToFieldName(column.getName());
				if (column.getAutoInc() && fieldValueAccessor.getValue(t, fieldName) != null) {
					return true;
				}
			}
		}
		return false;
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
	public FieldValueAccessor<? super T> getFieldValueAccessor() {
		return super.getFieldValueAccessor();
	}

	@Override
	public void setFieldValueAccessor(final FieldValueAccessor<? super T> fieldValueAccessor) {
		super.setFieldValueAccessor(fieldValueAccessor);
	}
}
