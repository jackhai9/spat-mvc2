/**
 * 
 */
package cn.hofan.spat.mvc2.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注资源类或方法的相对路径
 * 
 * @author renjun
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Path {
	
	/**
	 * 对应的url相对路径
	 * 可以使用正则表达式
	 * @return
	 */
	String[] value() default {};
}
