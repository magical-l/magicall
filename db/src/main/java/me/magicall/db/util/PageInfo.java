package me.magicall.db.util;

import me.magicall.util.kit.Kits;

public class PageInfo {

	public static enum PageMode {
		FROM_0, FROM_1;
		public final int firstPage;

		private PageMode() {
			final String name = name();
			firstPage = Kits.INT.fromString(Kits.STR.subStringAfter(name, "_"));
		}

		public int getFirstPage() {
			return firstPage;
		}
	}

	public static PageInfo ALL_PAGE_INFO = offsetSize(0, Integer.MAX_VALUE);

	public static PageInfo NONE_PAGE_INFO = offsetSize(0, 0);

	public static PageInfo FIRST_ONE = offsetSize(0, 1);

	public static PageInfo offsetSize(final int offset, final int size) {
		final PageInfo pageInfo = new PageInfo();
		pageInfo.setOffset(offset);
		pageInfo.setSize(size);
		return pageInfo;
	}

	public static PageInfo pageWithMode(final PageMode pageMode, final int page, final int size) {
		final PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(pageMode, page);
		pageInfo.setSize(size);
		return pageInfo;
	}

	public static PageInfo pageAndSize(final int page, final int size) {
		final PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setSize(size);
		return pageInfo;
	}

	private PageMode pageMode = PageMode.FROM_1;
	private int page;
	private int size;
	private Integer offset;

	public PageInfo() {
		super();
	}

	public int getOffset() {
		if (offset == null) {
			offset = (pageMode == PageMode.FROM_0 ? page : page - 1) * size;
		}
		return offset;
	}

	public int getPage() {
		return page;
	}

	public void setPage(final PageMode pageMode, final int page) {
		this.pageMode = pageMode;
		this.page = page;
	}

	public void setPage(final int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(final int size) {
		this.size = size;
	}

	public void setOffset(final int offset) {
		this.offset = offset;
	}

	public PageMode getPageMode() {
		return pageMode;
	}

	public void setPageMode(final PageMode pageMode) {
		this.pageMode = pageMode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (offset == null ? 0 : offset.hashCode());
		result = prime * result + page;
		result = prime * result + (pageMode == null ? 0 : pageMode.hashCode());
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PageInfo other = (PageInfo) obj;
		if (offset == null) {
			if (other.offset != null) {
				return false;
			}
		} else if (!offset.equals(other.offset)) {
			return false;
		}
		if (page != other.page) {
			return false;
		}
		if (pageMode != other.pageMode) {
			return false;
		}
		if (size != other.size) {
			return false;
		}
		return true;
	}

}
