package cn.hofan.spat.mvc2.thread;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hofan.spat.mvc2.BeatContext;


public class BeatContextUtils {
	
	/**
	 * 确保线程安全
	 */
	static ThreadLocal<BeatContext> beatThreadLocal = new ThreadLocal<BeatContext>();
	
	public static void bindBeatContextToCurrentThread(BeatContextLocal beat){
		beatThreadLocal.set(beat);
	}
	
	public static BeatContext getCurrent(){
		BeatContext beat = beatThreadLocal.get();
		if (beat == null) throw new IllegalStateException("BeatContext");
		
		return beat;
	}
	
	public static BeatContext BeatContextWapper(HttpServletRequest request, 
			HttpServletResponse response) {
		

		
		BeatContext beat = new BeatContextBean(request, response);
		
		// 设置系统性变量
		beat.getModel().add("__beat", beat);
		
		// TODO: 当前线程中如果已经存在Beat， 如何处理？
		beatThreadLocal.set(beat);
		
		return beat;
		
	}
	
	/*
	 * 移除当前线程的变量
	 */
	public static void remove(){
		beatThreadLocal.remove();
	}
	
	

}
