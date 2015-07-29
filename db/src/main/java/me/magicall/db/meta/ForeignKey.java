package me.magicall.db.meta;

public class ForeignKey extends BaseDbMeta {

	private TableMeta referencingTable;
	private DbColumn referencingColumn;
	private TableMeta referencedTable;
	private DbColumn referencedColumn;

	public TableMeta getReferencedTable() {
		return referencedTable;
	}

	public void setReferencedTable(final TableMeta refedTable) {
		referencedTable = refedTable;
		refedTable.getReferencedForeignKeys().add(this);
	}

	public DbColumn getReferencedColumn() {
		return referencedColumn;
	}

	public void setReferencedColumn(final DbColumn refedColumn) {
		referencedColumn = refedColumn;
	}

	public DbColumn getReferencingColumn() {
		return referencingColumn;
	}

	public void setReferencingColumn(final DbColumn column) {
		referencingColumn = column;
	}

	public TableMeta getReferencingTable() {
		return referencingTable;
	}

	public void setReferencingTable(final TableMeta referencingTable) {
		this.referencingTable = referencingTable;
		referencingTable.getForeignKeys().add(this);
	}
}
