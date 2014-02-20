package com.jrey.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {
	
	public static enum Method {GET,POST,PUT,DELETE,HEAD,OPTIONS,TRACE,ANY};

	public String name();
	public Method method() default Method.ANY;
	public String url();
	
	
}
