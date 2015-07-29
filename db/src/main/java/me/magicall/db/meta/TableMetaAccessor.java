package me.magicall.db.meta;

import java.util.Collection;

public interface TableMetaAccessor {

	TableMeta getTableMetaIgnoreCase(String tableNameOrModelName);

	Collection<TableMeta> tableMetas();
}