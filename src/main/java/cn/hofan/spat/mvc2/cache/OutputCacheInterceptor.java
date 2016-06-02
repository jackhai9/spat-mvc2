package cn.hofan.spat.mvc2.cache;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.annotation.AnnotationUtils;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.annotation.OutputCache;
import cn.hofan.spat.mvc2.interceptor.ActionInterceptor;

/**
 * 实现的一个方法拦截器
 * @author renjun
 *
 */
public class OutputCacheInterceptor implements ActionInterceptor {
	
	private Map<Method, Integer> methodMap =new ConcurrentHashMap<Method, Integer>();
	

	@Override
	public ActionResult preExecute(BeatContext beat) {
		int duration = -1;
		Method method = beat.getAction().getActionMethod();
		
		Integer intDuration = methodMap.get(method);
		
		duration = intDuration == null ? -1 : intDuration;
		if (duration == -1) {

			OutputCache oc = AnnotationUtils.findAnnotation(method, OutputCache.class);
			duration = oc.duration();
			if (duration < 0)  duration = 60;
			methodMap.put(method, Integer.valueOf(duration));
		}
		
		CacheContext cache = beat.getCache();
		
		ActionResult result = cache.getCacheResult();
		if (result == null) {
			cache.setExpiredTime(duration);
			cache.needCache();
		}
		
		return result;
	}


}