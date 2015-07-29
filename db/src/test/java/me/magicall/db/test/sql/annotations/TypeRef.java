package me.magicall.db.test.sql.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果数据库的字段名与bean的字段名不一致，请使用本注解指定映射关系。
 * 本注解写在Dao接口上。
 * 
 * @author MaGiCalL
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TypeRef {

	/**
	 * 格式：{"a","b"}
	 * 注意：数据库字段名只有在这里出现，其余地方涉及字段名都是指bean的字段名
	 * 
	 * @return
	 */
	String[] dbFieldName();

	/**
	 * 格式：{"a1","b1"}
	 * 注意：注意与dbFieldName的一一对应关系。
	 * 
	 * @return
	 */
	String[] classFieldName();
}
