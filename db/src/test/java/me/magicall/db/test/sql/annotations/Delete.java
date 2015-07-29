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
public @interface Delete {

	/**
	 * 条件语句
	 * 
	 * @return
	 */
	String value() default "";
}
