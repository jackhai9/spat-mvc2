package cn.hofan.spat.mvc2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hofan.spat.mvc2.annotation.Ignored;

/**
 * 所有的Controller基类
 * @author renjun
 *
 */
public abstract class MvcController {
	
	/**
	 * scf服务cache
	 */
//	private static Map<Class<?>, Object> scfCache = new HashMap<Class<?>, Object>();
//	
//	private String scfUrl = "";
	
	/**
	 * 日志系统
	 */
	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 在一个请求过程中的上下文
	 */
	@Autowired
	protected BeatContext beat;

	void setBeatContext(BeatContext beat){
		this.beat = beat;
	}
	
	@Ignored
	protected BeatContext getBeatContext() {
		return beat;
	}
	
	/**
	 * 提供scf服务
	 * TODO: 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	protected <T> T SysService(Class<T> clazz, String url){
//		T object = (T) scfCache.get(clazz);
//		if (object != null) return object;
//		object = ProxyFactory.create(clazz, url);
//		scfCache.put(clazz, object);
//		return object;
//	}
}
