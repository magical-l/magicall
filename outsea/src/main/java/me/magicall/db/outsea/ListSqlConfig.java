package me.magicall.db.outsea;

import me.magicall.db.util.PageInfo;

public class ListSqlConfig<T> extends GetOneSqlConfig<T> {

	public ListSqlConfig(final String mainModelName) {
		super(mainModelName);
	}

	@Override
	public PageInfo getPageInfo() {
		return super.getPageInfo();
	}

	@Override
	public void setPageInfo(final PageInfo pageInfo) {
		super.setPageInfo(pageInfo);
	}

}
