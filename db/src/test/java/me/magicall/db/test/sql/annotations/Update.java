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
public @interface Update {

	/**
	 * 这个方法在下面的场景十分有用：当你只有bean的id，想把它的某个属性值改一下，其他属性值保持不变，那么将此方法的返回值改为false
	 * 
	 * @return
	 */
	boolean escapeNullField() default true;
}
