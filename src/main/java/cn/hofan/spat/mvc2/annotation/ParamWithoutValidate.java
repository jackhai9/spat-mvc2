package cn.hofan.spat.mvc2.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.hofan.spat.mvc2.interceptor.ParamValidateInterceptor;

@Interceptor(ParamValidateInterceptor.class)
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamWithoutValidate {

	String[] value() default {};
	
}
