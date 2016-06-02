package cn.hofan.spat.mvc2.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.hofan.spat.mvc2.interceptor.ActionInterceptor;


/**
 * 设置拦截器
 * @author renjun
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Interceptor {
	Class<? extends ActionInterceptor> value();
	InterceptorType type() default InterceptorType.ACTION;
	public enum InterceptorType {
		ACTION, 
		EXECEPTION,
		RESULT
	}
}
