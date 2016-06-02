package cn.hofan.spat.mvc2.route;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils.MethodFilter;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.annotation.Ignored;

//import javassist.Modifier;

public class RouteUtils {
	
	
	/**
	 * Pre-built MethodFilter that matches all non-bridge methods
	 * which are not declared on <code>java.lang.Object</code>.
	 */
	public static MethodFilter HANDLER_METHODS = new MethodFilter() {

		public boolean matches(Method method) {
	        if (AnnotationUtils.findAnnotation(method, Ignored.class) != null) return false;
	        
	        Class<?> returnType = method.getReturnType();
	        if (returnType == null) return false;
	        if (! ActionResult.class.isAssignableFrom(returnType)) return false;
	        
			return (!method.isBridge() && method.getDeclaringClass() != Object.class
					&& Modifier.isPublic(method.getModifiers()));
		}
	};
	




}
