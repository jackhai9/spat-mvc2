package cn.hofan.spat.mvc2;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 用户获得Controller的属性
 * @author renjun
 *
 */
public interface ControllerAttribute {
	
	/**
	 * 得到所有的注解
	 * @return
	 */
	public Set<Annotation> getAnnotations();
	
	/**
	 * 得到控制类的类型
	 * @return
	 */
	public Class<?> getControllerClass();

}
