package br.com.barganhas.business.entities.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyField {
	boolean notNull() default false;
	boolean allowEmpty() default true;
	boolean unindexed() default false;
}
