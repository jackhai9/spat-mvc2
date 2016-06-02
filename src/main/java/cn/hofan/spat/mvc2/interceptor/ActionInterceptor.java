package cn.hofan.spat.mvc2.interceptor;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;

/**
 * 拦截器需要实现的接口
 * @author renjun
 *
 */
public interface ActionInterceptor {
	
	/**
	 * 拦截当前请求
	 * @param beat 当前请求的上下文
	 * @return 
	 * null，进入下一个拦截或执行Action
	 * <BR/>
	 * 非空，直接显示，不进入下一个拦截或执行Action
	 */
	public ActionResult preExecute(BeatContext beat);
}
