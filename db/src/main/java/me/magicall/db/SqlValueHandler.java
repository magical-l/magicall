package me.magicall.db;

/**
 * 根据底层框架的规则,将值拼接到StringBuilder上.
 * 与底层的框架相关.比如SpringJDBC的NamedParameterJdbcOptions,需要以":"开头.
 * 
 * @author MaGiCalL
 */
@FunctionalInterface
public interface SqlValueHandler {

	/**
	 * @param sb 用来装sql语句的StringBuilder
	 * @param conditionOperator 条件操作符
	 * @param fieldName 参数名(数据库列名/model的字段名)
	 * @param refedValueIndex 参数序号
	 * @param refedValue 参数值
	 */
	String handle(final StringBuilder sb, final String fieldName, int refedValueIndex, Object refedValue);
}
