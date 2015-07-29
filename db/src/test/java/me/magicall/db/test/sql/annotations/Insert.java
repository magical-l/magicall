package me.magicall.db.test.sql.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@IsSql
public @interface Insert {
	/**
	 * 这个方法默认的效果是，数据库表字段有默认值，那么bean可以不用设置具体值。
	 * 如果将返回值改为false，将强制使用null插入数据库而非数据库的默认值，若数据库该字段非空，则会导致一个错误
	 * 
	 * @return
	 */
	boolean escapeNullField() default true;
}
