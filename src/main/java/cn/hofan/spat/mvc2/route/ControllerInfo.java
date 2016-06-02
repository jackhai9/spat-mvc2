package cn.hofan.spat.mvc2.route;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import cn.hofan.spat.mvc2.MvcController;
import cn.hofan.spat.mvc2.annotation.GET;
import cn.hofan.spat.mvc2.annotation.Ignored;
import cn.hofan.spat.mvc2.annotation.POST;
import cn.hofan.spat.mvc2.annotation.Path;

class ControllerInfo {
	
	public Object controller;
	
	public Path path = null;
	
	public boolean isGet = false;
	public boolean isPost = false;

	public Set<ActionInfo> actions = new LinkedHashSet<ActionInfo>();
	
	/**
	 * 得到所有的Annotion
	 * 2010-12-7
	 */
	public Set<Annotation> annotations = new LinkedHashSet<Annotation>();
	
	public int size() {
		return actions.size();
	}
	
	public boolean hasTypePath(){
		return path == null ? false : true;
	}
	
	public String[] getUrlTypeLevelPatterns(){
		return path == null ? new String[0] : path.value();
	}
	
	private ControllerInfo(String beanName, ApplicationContext context) {
		Class<?> handlerType = context.getType(beanName);
	
        path = AnnotationUtils.findAnnotation(handlerType, Path.class);
        
		isGet = AnnotationUtils.findAnnotation(handlerType, GET.class) == null ?
				false : true;
		isPost = AnnotationUtils.findAnnotation(handlerType, POST.class) == null ?
				false : true;
		
		if(!isGet && !isPost) {
			isGet = true;
			isPost = true;
		}
		
		controller = context.getBean(beanName);
		
		// 2010-12-07
		for(Annotation ann : handlerType.getAnnotations())
			annotations.add(ann);
		
		
		// 处理，增加Action
		ReflectionUtils.doWithMethods(handlerType, new ReflectionUtils.MethodCallback() {
			public void doWith(Method method) {
				ActionInfo action = ActionInfo.Factory(ControllerInfo.this, method);
				actions.add(action);
			}
		}, RouteUtils.HANDLER_METHODS
		);		
	}
	
	static HashMap<String, ControllerInfo> classMaps = new LinkedHashMap<String, ControllerInfo>();
	
	public static ControllerInfo Factory(String beanName, ApplicationContext context) {
		Class<?> handlerType = context.getType(beanName);
		ControllerInfo c = classMaps.get(beanName);
		if (c != null) return c;
		
		// 查找合适的Ctroller作为mapping
		if (! handlerType.getName().endsWith("Controller")) return null;
		if (! ClassUtils.isAssignable(MvcController.class, handlerType)) return null;
		String packageName = ClassUtils.getPackageName(handlerType);
		if (! packageName.startsWith("cn.hofan.") ||!  packageName.endsWith("controllers"))
			return null;
        if (AnnotationUtils.findAnnotation(handlerType, Ignored.class) != null) return null;
        
        ControllerInfo ce = new ControllerInfo(beanName, context);
        classMaps.put(beanName, ce);
        
        return ce;
        
	}

}
