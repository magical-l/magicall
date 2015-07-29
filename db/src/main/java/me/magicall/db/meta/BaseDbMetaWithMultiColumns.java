package me.magicall.db.meta;

import java.util.List;

import me.magicall.coll.ElementTransformerUtil;
import me.magicall.util.kit.Kits;

public class BaseDbMetaWithMultiColumns extends BaseDbMeta {
	protected transient List<String> columnNames;

	public List<String> getColumnNames() {
		if (columnNames == null) {
			columnNames = Kits.LIST.transform(getList(), ElementTransformerUtil.TO_NAME);
		}
		return columnNames;
	}

}
