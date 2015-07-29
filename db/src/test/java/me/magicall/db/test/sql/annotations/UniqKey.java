package me.magicall.db.test.sql.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UniqKey {

	/**
	 * 逻辑上可以唯一标识一条记录的字段或字段组合，即主键（在数据库里可能没有被设为主键）。
	 * 注意：字段使用的是bean的字段而非数据库的字段。参考TypeRef。
	 * 
	 * @return
	 */
	String[] value();
}
