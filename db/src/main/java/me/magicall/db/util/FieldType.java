package me.magicall.db.util;

import java.sql.Types;

import me.magicall.mark.HasIdGetter.HasIntIdGetter;
import me.magicall.mark.Named;
import me.magicall.util.kit.Kit;
import me.magicall.util.kit.Kits;

/**
 * 将java.sql.Types的所有int型的type代理为枚举
 * 
 * @author MaGiCalL
 */
public enum FieldType implements Named, HasIntIdGetter {
	ARRAY, //
	BIGINT(Kits.LONG), //
	BINARY, //byte[]
	BIT(Kits.BOOL), //
	BLOB, //byte[]
	BOOLEAN(Kits.BOOL), //
	CHAR(Kits.STR), //String
	CLOB, //String
	DATALINK,
	DATE(Kits.DATE), //
	DECIMAL, //BigDecimal
	DISTINCT,
	DOUBLE(Kits.DOUBLE), //
	FLOAT(Kits.FLOAT), //
	INTEGER(Kits.INT), //
	JAVA_OBJECT(Kits.OBJ), //
	LONGNVARCHAR, //
	LONGVARBINARY, //byte[]
	LONGVARCHAR, //byte[]
	NCHAR,
	NCLOB,
	NULL,
	NUMERIC(Kits.DOUBLE),
	NVARCHAR, //
	OTHER(Kits.OBJ), //
	REAL(Kits.FLOAT),
	REF,
	ROWID, //
	SMALLINT(Kits.INT),
	SQLXML,
	STRUCT, //
	TIME(Kits.DATE),
	TIMESTAMP(Kits.DATE),
	TINYINT(Kits.BOOL), //
	VARBINARY,
	VARCHAR(Kits.STR) //
	;

	private final int javaSqlTypesConst;
	public final Kit<?> kit;

	private FieldType() {
		this(Kits.OBJ);
	}

	private FieldType(final Kit<?> kit) {
		this.kit = kit;
		int i = 0;//DEFAULT is java.sql.Types.NULL
		try {
			i = Types.class.getField(name()).getInt(null);
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final NoSuchFieldException e) {
			e.printStackTrace();
		}
		javaSqlTypesConst = i;
	}

	public int getValue() {
		return javaSqlTypesConst;
	}

	@Override
	public String getName() {
		return name();
	}

	@Override
	public Integer getId() {
		return getValue();
	}

	public static void main(final String... args) {
		for (final FieldType t : FieldType.values()) {
			System.out.println("@@@@@@" + t.name() + ' ' + t.getValue());
		}
	}

	public Kit<?> getKit() {
		return kit;
	}
}
