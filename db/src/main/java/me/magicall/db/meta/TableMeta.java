package me.magicall.db.meta;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import me.magicall.db.util.DbUtil;
import me.magicall.db.util.FieldType;
import me.magicall.util.kit.Kits;

public class TableMeta extends BaseDbMetaWithMultiColumns implements HasComment {
//	CREATE TABLE `batchtrace` (
//			  `OutVoucherNO` varchar(12) NOT NULL,
//			  `NO` smallint(3) NOT NULL,
//			  `ID` int(11) unsigned NOT NULL,
//			  `Quantity` double DEFAULT '0',
//			  `VoucherName` varchar(10) DEFAULT NULL,
//			  KEY `OutVoucherNO` (`OutVoucherNO`),
//			  KEY `ID` (`ID`),
//			  KEY `NO` (`NO`),
//			  CONSTRAINT `batchtrace_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `kcmx` (`ID`) ON UPDATE CASCADE
//	CONSTRAINT `tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
//			) ENGINE=InnoDB DEFAULT CHARSET=utf8
	private String dbName;

	private Key primaryKey;
	private Collection<Key> keys = new LinkedHashSet<>();
	private Collection<ForeignKey> foreignKeys = new LinkedHashSet<>();
	private String defaultCharsetName;

	private String comment;

//	private Engine engine;

	private Collection<ForeignKey> referencedForeignKeys = new LinkedHashSet<>();

	public TableMeta() {
		super();
	}

	public TableMeta(final String table) {
		super();
		setName(table);
	}

	public String getFullName() {
		final String db = getDbName();
		return Kits.STR.isEmpty(db) ? getName() : db + '.' + getName();
	}

	public DbColumn getColumn(final String columnName) {
		final List<DbColumn> columns = getColumns();
		for (final DbColumn column : columns) {
			if (column.getName().equalsIgnoreCase(columnName)) {
				return column;
			}
		}
		return null;
	}

	public List<DbColumn> getColumns() {
		return getList();
	}

	public void setFields(final List<DbColumn> fields) {
		setList(fields);
	}

	public String getDefaultCharsetName() {
		return defaultCharsetName;
	}

	public void setDefaultCharsetName(final String defaultCharsetName) {
		this.defaultCharsetName = defaultCharsetName;
	}

	public Collection<Key> getKeys() {
		return keys;
	}

	public void setKeys(final Collection<Key> keys) {
		this.keys = keys;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(final String dbName) {
		this.dbName = dbName;
	}

	@Override
	public String toString() {
		return "TableMeta [" + getName() + ']' + getColumns();
	}

	public Key getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(final Key primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Collection<ForeignKey> getForeignKeys() {
		return foreignKeys;
	}

	public void setForeignKeys(final Collection<ForeignKey> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

	public Collection<ForeignKey> getReferencedForeignKeys() {
		return referencedForeignKeys;
	}

	public void setReferencedForeignKeys(final Collection<ForeignKey> referencedForeignKeys) {
		this.referencedForeignKeys = referencedForeignKeys;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		result = prime * result + (dbName == null ? 0 : dbName.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TableMeta other = (TableMeta) obj;
		if (dbName == null) {
			if (other.dbName != null) {
				return false;
			}
		} else if (!dbName.equals(other.dbName)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public ModelMeta getModelMeta() {
		final ModelMeta meta = new ModelMeta();
		for (final DbColumn column : getColumns()) {
			final FieldMeta fieldMeta = new FieldMeta(column);
			//TODO setType
			final FieldType fieldType = column.getType();
			fieldMeta.setType(fieldType.getKit().getClasses()[0]);
			meta.add(fieldMeta);
		}
		meta.setComment(getComment());
		meta.setName(DbUtil.dbNameToJavaName(getName()));
		return meta;
	}
}
