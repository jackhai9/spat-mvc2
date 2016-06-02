package cn.hofan.spat.mvc2.route.interceptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.annotation.Interceptor;
import cn.hofan.spat.mvc2.annotation.Interceptor.InterceptorType;
import cn.hofan.spat.mvc2.interceptor.ActionInterceptor;
import cn.hofan.spat.mvc2.route.ActionInfo;

public class InterceptorHandler {
	
	/**
	 * 前置拦截器容器
	 */
	List<OrderActionInterceptor> actionInterceptors =  new ArrayList<OrderActionInterceptor>();
	
	/**
	 * 后置拦截器容器
	 */
	List<OrderActionInterceptor> resultInterceptors =  new ArrayList<OrderActionInterceptor>();
	
	/**
	 * 异常拦截器
	 */
	List<OrderActionInterceptor> exceptionInterceptors =  new ArrayList<OrderActionInterceptor>();
	
	/**
	 * 按顺序执行前置拦截器
	 * @param beat
	 * @return
	 */
	public ActionResult preExecute(BeatContext beat){
		for(OrderActionInterceptor oai : actionInterceptors){
			
			ActionResult result = oai.interceptor.preExecute(beat);
			if (result != null) return result;			
		}
		
		return null;
	}
	
	/**
	 * 按顺序执行后置拦截器
	 * @param beat
	 * @return
	 */
	public ActionResult afterExecute(BeatContext beat){
		for(OrderActionInterceptor oai : resultInterceptors){
			ActionResult result = oai.interceptor.preExecute(beat);
			if (result != null) return result;
		}
		
		return null;
	}
	
	/**
	 * 按顺序执行异常拦截器
	 * @param beat
	 * @return
	 */
	public ActionResult exceptionExecute(BeatContext beat){
		for(OrderActionInterceptor oai : exceptionInterceptors){
			ActionResult result = oai.interceptor.preExecute(beat);
			if (result != null) return result;
		}
		
		return null;
	}
	/**
	 * 
	 * 增加一个拦截器
	 * @param ai
	 * @param order
	 */
	public void add(ActionInterceptor ai, int order, InterceptorType type){
		
		if(type == InterceptorType.ACTION){
			int index = 0;
			for(; index < actionInterceptors.size(); index++)
				if (order < actionInterceptors.get(index).order)
					break;
			
			actionInterceptors.add(index, new OrderActionInterceptor(order, ai));	
		}
		
		if(type == InterceptorType.RESULT){
			int index = 0;
			for(; index < resultInterceptors.size(); index++)
				if (order < resultInterceptors.get(index).order)
					break;
			
			resultInterceptors.add(index, new OrderActionInterceptor(order, ai));	
		}
		
		if(type == InterceptorType.EXECEPTION){
			int index = 0;
			for(; index < exceptionInterceptors.size(); index++)
				if (order < exceptionInterceptors.get(index).order)
					break;
			
			exceptionInterceptors.add(index, new OrderActionInterceptor(order, ai));	
		}
	}
	
	/**
	 * 根据ActionInfo获得方法的所有拦截器
	 * @param actionInfo
	 */
	public void build(ActionInfo actionInfo){

		for(Annotation ann : actionInfo.getAnnotations()){
			// 判断ann是否是拦截器
			Class<?> clazz = ann.getClass();
			Interceptor icp = AnnotationUtils.findAnnotation(clazz, Interceptor.class);
			if (icp == null) continue;
			
			
			InterceptorType type = icp.type();
			Class<? extends ActionInterceptor> aiClazz = icp.value();
			ActionInterceptor interceptor = ActionInterceptorFactory(aiClazz);
			Object orderObject = AnnotationUtils.getValue(ann, "order");
			int order = orderObject == null ? 1
					: (Integer)orderObject;   // TODO: maybe throw exception.

				add(interceptor, order, type);

		}
	}
	
	/**
	 * 有序的前置拦截器
	 * @author liuzw
	 *
	 */
	class OrderActionInterceptor{
		int order;
		ActionInterceptor interceptor;
		public OrderActionInterceptor(int order, ActionInterceptor interceptor) {
			super();
			this.order = order;
			this.interceptor = interceptor;
		}		
	}
	
	private static Map<Class<?>, ActionInterceptor> actionInterceptorMap = new HashMap<Class<?>, ActionInterceptor>();
	
	private static <T extends ActionInterceptor>  ActionInterceptor ActionInterceptorFactory(Class<T> clazz){
		ActionInterceptor actionInterceptor = actionInterceptorMap.get(clazz);
		if (actionInterceptor == null){
			actionInterceptor = BeanUtils.instantiate(clazz);
			actionInterceptorMap.put(clazz, actionInterceptor);
		}
		return actionInterceptor;
	}

}
