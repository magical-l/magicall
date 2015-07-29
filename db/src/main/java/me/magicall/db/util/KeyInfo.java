package me.magicall.db.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KeyInfo {

	String value() default DbUtil.COMMON_ID_FIELD_NAME;

	Class<?> type() default Integer.class;
}
