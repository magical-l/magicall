package me.magicall.db.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.ElementTransformerUtil;
import me.magicall.coll.ElementTransformerUtil.SerializableElementTransformer;
import me.magicall.db.FieldComparator;
import me.magicall.db.meta.DbColumn;
import me.magicall.db.meta.TableMeta;
import me.magicall.db.model.BaseModel.BaseModelInt;
import me.magicall.util.LanguageLabelConverter;
import me.magicall.util.kit.Kits;
import me.magicall.util.time.TimeFormatter;
import me.magicall.util.touple.TwoTuple;

public class DbUtil {

	/**
	 * 调用ResultSet的getXxx方法将数据转化成相应的java格式.
	 * (本接口其实就是把ResultSet的一系列getXxx方法转变成一系列对象).
	 * 
	 * @author MaGiCalL
	 */
	public interface DbTypeConvert {
		Object convert(ResultSet rs, int columnIndex) throws SQLException;
	}

	//=============================== 一些工具常量 some tooling constants
	public static final String PLACE_HOLDER = " ? ";
	/**
	 * java.sql.ResultSet的列下标是从1开始的
	 */
	public static final int RESULT_SET_COLUMN_START_INDEX = 1;
	/**
	 * java.sql.PreparedStatement参数列表下标是从1开始的.
	 */
	public static final int PREPARED_STATEMENT_PARAMETER_START_INDEX = 1;
	public static final String COMMON_ID_FIELD_NAME = "id";
	public static final String COMMON_ID_COLUMN_NAME = javaNameToDbName(COMMON_ID_FIELD_NAME);

	private static final char[] QUOTE_SIGNS = { '`', '\'', '\"' };
	/**
	 * 为集合中的每一个元素预留一个占位符
	 */
	public static final ElementTransformer<Object, String> ELEMENTS_COUNT_TO_PLACE_HOLDER = (index,
																							 element) -> PLACE_HOLDER;
	/**
	 * 将列名转化成字段名
	 */
	public static final ElementTransformer<String, String> COLUMN_NAME_TO_FIELD_NAME = (index,
																						element) -> dbNameToJavaName(element);

	public static final DbTypeConvert COMMON_DB_TYPE_CONVERTER = (rs, columnIndex) -> rs.getObject(columnIndex);

	private static final Map<Class<?>, DbTypeConvert> DB_TYPE_CONVERTERS = new LinkedHashMap<>();
	private static final Collection<Class<?>> DB_TYPES;
	static {
		//int
		final DbTypeConvert i = (rs, columnIndex) -> rs.getInt(columnIndex);
		DB_TYPE_CONVERTERS.put(int.class, i);
		DB_TYPE_CONVERTERS.put(Integer.class, i);
		//string
		DB_TYPE_CONVERTERS.put(String.class, (rs, columnIndex) -> rs.getString(columnIndex));
		//long
		final DbTypeConvert l = (rs, columnIndex) -> rs.getLong(columnIndex);
		DB_TYPE_CONVERTERS.put(long.class, l);
		DB_TYPE_CONVERTERS.put(Long.class, l);
		//java.util.date & timestamp
		final DbTypeConvert t = (rs, columnIndex) -> rs.getTimestamp(columnIndex);
		DB_TYPE_CONVERTERS.put(Date.class, t);
		DB_TYPE_CONVERTERS.put(Timestamp.class, t);
		//boolean
		final DbTypeConvert b = (rs, columnIndex) -> rs.getBoolean(columnIndex);
		DB_TYPE_CONVERTERS.put(boolean.class, b);
		DB_TYPE_CONVERTERS.put(Boolean.class, b);
		//float
		final DbTypeConvert f = (rs, columnIndex) -> rs.getFloat(columnIndex);
		DB_TYPE_CONVERTERS.put(float.class, f);
		DB_TYPE_CONVERTERS.put(Float.class, f);
		//double
		final DbTypeConvert d = (rs, columnIndex) -> rs.getDouble(columnIndex);
		DB_TYPE_CONVERTERS.put(double.class, d);
		DB_TYPE_CONVERTERS.put(Double.class, d);
		//byte
		final DbTypeConvert bt = (rs, columnIndex) -> rs.getByte(columnIndex);
		DB_TYPE_CONVERTERS.put(byte.class, bt);
		DB_TYPE_CONVERTERS.put(Byte.class, bt);
		//short
		final DbTypeConvert s = (rs, columnIndex) -> rs.getShort(columnIndex);
		DB_TYPE_CONVERTERS.put(short.class, s);
		DB_TYPE_CONVERTERS.put(Short.class, s);
		//char
		final DbTypeConvert c = (rs, columnIndex) -> rs.getCharacterStream(columnIndex);
		DB_TYPE_CONVERTERS.put(char.class, c);
		DB_TYPE_CONVERTERS.put(Character.class, c);
		//java.sql.Date
		DB_TYPE_CONVERTERS.put(java.sql.Date.class, (rs, columnIndex) -> rs.getDate(columnIndex));
		//time
		DB_TYPE_CONVERTERS.put(Time.class, (rs, columnIndex) -> rs.getTime(columnIndex));
		//object
		DB_TYPE_CONVERTERS.put(Object.class, COMMON_DB_TYPE_CONVERTER);
		//comparable
		DB_TYPE_CONVERTERS.put(Comparable.class, COMMON_DB_TYPE_CONVERTER);
		//XXX:Number

		DB_TYPES = DB_TYPE_CONVERTERS.keySet();
	}

	public static DbTypeConvert getDbTypeConvert(final Class<?> clazz) {
		final DbTypeConvert convert = DB_TYPE_CONVERTERS.get(clazz);
		return convert == null ? COMMON_DB_TYPE_CONVERTER : convert;
	}

	public static boolean isDbType(final Class<?> clazz) {
		return Kits.COLL.contains(DB_TYPES, clazz);
	}

	/**
	 * 在指定的表的元数据中寻找指定的字段所对应的列的元数据.
	 * 
	 * @param tableMeta
	 * @param javaFieldName
	 * @return
	 */
	public static DbColumn findColumn(final TableMeta tableMeta, final String javaFieldName) {
		return tableMeta.getColumn(javaNameToDbName(javaFieldName));
	}

	public static Collection<Integer> toIntIds(final Collection<? extends BaseModelInt> source) {
		return Kits.COLL.transform(source, ElementTransformerUtil.TO_INT_ID);
	}

	/**
	 * //注：目前只支持单字段主键
	 * //目前只支持Integer和Long的id
	 * 
	 * @param tableMeta
	 * @return
	 */
	public static boolean idIsInt(final TableMeta tableMeta) {
		return tableMeta.getPrimaryKey().getColumns().get(0).getType() == FieldType.INTEGER;
	}

	//------------------------------- about sql
	public static StringBuilder buildSql_inParamName(final StringBuilder sb, final Collection<?> coll,
			final String paramNamePrefix) {
		return Kits.STR.join(sb.append(" in("), coll, ",", new SerializableElementTransformer<Object, String>() {
			private static final long serialVersionUID = 4859958118274645385L;

			@Override
			public String transform(final int index, final Object element) {
				return paramNamePrefix + index;
			}
		}).append(')');
	}

	/**
	 * 拼接in子句,使用'?'作为占位符,个数=coll.size()
	 * 
	 * @param sb
	 * @param coll
	 * @return
	 */
	public static StringBuilder appendInPlaceHolder(final StringBuilder sb, final Collection<?> coll) {
		return appendParameterPlaceHolder(sb.append(" in("), coll).append(')');
	}

	public static StringBuilder appendParameterPlaceHolder(final StringBuilder sb, final Collection<?> coll) {
		return Kits.STR.join(sb, coll, ",", ELEMENTS_COUNT_TO_PLACE_HOLDER);
	}

	public static StringBuilder buildSql_Insert(final List<String> columnNames, final String tableName,
			final ElementTransformer<String, String> placeHolderTF) {
		final StringBuilder sb = new StringBuilder("insert into ").append(tableName).append('(');
		Kits.STR.join(sb, columnNames, ",");
		sb.append(")values(");
//		return appendParameterPlaceHolder(sb, columnNames).append(")");
		Kits.STR.join(sb, columnNames, ",", placeHolderTF);
		return sb.append(')');
	}

	public static StringBuilder buildSql_Insert(final TableMeta tableMeta,
			final ElementTransformer<String, String> placeHolderTF) {
		return buildSql_Insert(Kits.LIST.transform(tableMeta.getColumns(), ElementTransformerUtil.TO_NAME),
				tableMeta.getName(), placeHolderTF);
	}

	public static StringBuilder buildSql_UpdateSetValuePart(final StringBuilder sb, final List<String> columnNames,
			final ElementTransformer<String, String> placeHolderTF, final boolean escapeId) {
		if (escapeId) {
			int index = 0;
			for (final String column : columnNames) {
				if (!COMMON_ID_COLUMN_NAME.equalsIgnoreCase(column)) {
					sb.append(column).append('=').append(placeHolderTF.transform(index, column)).append(',');
				}
				++index;
			}
		} else {
			int index = 0;
			for (final String column : columnNames) {
				sb.append(column).append('=').append(placeHolderTF.transform(index, column)).append(',');
				++index;
			}
		}
		return sb.deleteCharAt(sb.length() - 1);
	}

	public static StringBuilder buildSql_SelectAllFrom(final String tableName, final List<String> columnNames) {
		final StringBuilder sb = new StringBuilder("select ");
		Kits.STR.join(sb, columnNames, ",");
		return sb.append(" from ").append(tableName);
	}

	public static StringBuilder buildSql_SelectAllFromWhereId(final String tableName, final List<String> columnNames) {
		return buildSql_SelectAllFromWhereId(tableName, columnNames, COMMON_ID_COLUMN_NAME);
	}

	public static StringBuilder buildSql_SelectAllFromWhereId(final String tableName, final List<String> columnNames,
			final String idColumnName) {
		return buildSql_SelectAllFrom(tableName, columnNames).append(" where ").append(idColumnName);
	}

	public static StringBuilder buildSql_SelectAllFrom(final TableMeta tableMeta) {
		return buildSql_SelectAllFrom(tableMeta.getName(),
				Kits.LIST.transform(tableMeta.getColumns(), ElementTransformerUtil.TO_NAME));
	}

	public static StringBuilder buildSql_SelectAllFromWhereId(final TableMeta tableMeta) {
		return buildSql_SelectAllFrom(tableMeta).append(" where ").append(COMMON_ID_FIELD_NAME);
	}

	public static StringBuilder appendLimit(final StringBuilder sb, final PageInfo pageInfo) {
		if (pageInfo != null) {
			final int size = pageInfo.getSize();
			final int offset = pageInfo.getOffset();
			if (size < Integer.MAX_VALUE) {
				sb.append(" LIMIT ");
				if (offset > 0) {
					sb.append(offset).append(',');
				}
				return sb.append(size);
			}
		}
		return sb;
	}

	public static StringBuilder buildDeleteFromTable(final String modelName) {
		return new StringBuilder("delete from ").append(javaNameToDbName(modelName));
	}

	public static StringBuilder appendOrderBy(final StringBuilder sb, final FieldComparator<?> fieldComparator) {
		if (fieldComparator != null) {
			final List<TwoTuple<String, DbOrder>> comparingFieldsNamesAndOrders = fieldComparator
					.getComparingFieldsNamesAndOrders();
			if (!Kits.COLL.isEmpty(comparingFieldsNamesAndOrders)) {
				sb.append(" ORDER BY ");
				for (final TwoTuple<String, DbOrder> t : comparingFieldsNamesAndOrders) {
					final String fieldName = t.first;
					final DbOrder order = t.second;
					sb.append(fieldName).append(' ');
					if (order == null) {
						sb.append(DbOrder.ASC.toSql()).append(',');
					} else {
						sb.append(order.toSql()).append(',');
					}
				}
				sb.deleteCharAt(sb.length() - 1);
			}
		}
		return sb;
	}

	//------------------------------- about resultSet
	public static Object getIdFromResultSet(final ResultSet resultSet, final TableMeta tableMeta) throws SQLException {
		//1，直接写id=resultSet.getObject，返回的是一个Long
		//2，如果写成 id = idIsInt ? resultSet.getInt(...) : resultSet.getLong(...)，因为冒号两端会被弄成相同类型，最终左侧无论如何也会被包装成一个Long
		if (idIsInt(tableMeta)) {
			return resultSet.getInt(COMMON_ID_COLUMN_NAME);
		} else {
			return resultSet.getLong(COMMON_ID_COLUMN_NAME);
		}
	}

	//------------------------------- about name
	/**
	 * 将 xx_yy_zz 转换成 xxYyZz
	 * 
	 * @param dbName
	 * @return
	 */
	public static String dbNameToJavaName(String dbName) {
		dbName = unquote(dbName);

		final StringBuilder sb = new StringBuilder();
		final int len = dbName.length();
		for (int i = 0; i < len; ++i) {
			char c = dbName.charAt(i);
			if (c == '_') {
				++i;
				c = Character.toUpperCase(dbName.charAt(i));
			} else {
				c = Character.toLowerCase(c);
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 将xx_yy_zz转换成xxYyZz
	 * 
	 * @param source
	 * @return
	 */
	public static List<String> dbNameToJavaName(final List<String> source) {
		return Kits.LIST.transform(source, COLUMN_NAME_TO_FIELD_NAME);
	}

	/**
	 * convert xxYyZz to xx_yy_zz
	 * 
	 * @param javaName
	 * @return
	 */
	public static String javaNameToDbName(final String javaName) {
		return LanguageLabelConverter.CAMEL.convertTo(LanguageLabelConverter.SQL, javaName);
	}

	/**
	 * 取出最外层小括号[第一个'('和最后一个')']中的字符串.
	 * 
	 * @param source
	 * @return
	 */
	public static String strInBrackets(final String source) {
		final int indexOfLeft = source.indexOf('(');
		if (indexOfLeft < 0) {
			return null;
		}
		final int beginIndex = indexOfLeft + 1;
		return source.substring(beginIndex, source.lastIndexOf(')'));
	}

	/**
	 * 去掉字符串两端的引号,包括单引号、双引号、反引号（`)
	 * 
	 * @param name
	 * @return
	 */
	public static String unquote(String name) {
		name = name.trim();
		for (final char c : QUOTE_SIGNS) {
			if (name.charAt(0) == c) {
				name = name.substring(1);
			}
			final int len = name.length();
			if (name.charAt(len - 1) == c) {
				name = name.substring(0, len - 1);
			}
		}
		return name;
	}

	/**
	 * 用反引号括起字符串
	 * 
	 * @param name
	 * @return
	 */
	public static String quoteDbName(final String name) {
		return '`' + name + '`';
	}

	/**
	 * FIXME:注:此方法的实现与springJdbc耦合
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String namedParam(final String fieldName) {
		return ':' + fieldName;
	}

	/**
	 * 将value拼接到sb.如果是数字或布尔型,直接拼接;如果是时间类型,将其格式化为yyyy-MM-dd HH:mm:ss:SSS;其他类型当做字符串,加上引号
	 * 
	 * @param sb
	 * @param value
	 * @return
	 */
	public static StringBuilder formatSqlValue(final StringBuilder sb, final Object value) {
		if (value instanceof Number || value instanceof Boolean) {
			sb.append(value);
		} else if (value instanceof Date) {
			sb.append('\'').append(TimeFormatter.Y4_M2_D2_H2_MIN2_S2_MS3.format((Date) value)).append('\'');
		} else {
			sb.append('\'').append(value).append('\'');
		}
		return sb;
	}

	public static final char TABLE_NAME_COLUMN_NAME_SEPERATOR_CHAR = '$';
	public static final String TABLE_NAME_COLUMN_NAME_SEPERATOR = String.valueOf(TABLE_NAME_COLUMN_NAME_SEPERATOR_CHAR);

	public static void main(final String... args) {
		System.out.println("@@@@@@DbUtil.main():" + javaNameToDbName("device_aaa"));
	}
}
