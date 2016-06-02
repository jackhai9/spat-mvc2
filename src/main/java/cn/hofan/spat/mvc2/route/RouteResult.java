package cn.hofan.spat.mvc2.route;

import java.util.HashMap;
import java.util.Map;



import cn.hofan.spat.mvc2.BeatContext;

/**
 * 封装有关路由的信息。
 * 
 * @author renjun (jun.ren@gmail.com)
 *
 */
public class RouteResult {
	public ActionInfo action;
	public String pattern;
	//public boolean isExactMatch;
	public Map<String, String> urlParams = new HashMap<String, String>();
	public BeatContext beat;
	
	/**
	 * 执行命令前的预处理
	 * 可以进行Sql注入检查等
	 * @author renjun
	 *
	 */
	public interface PreProcessor{
		void PreProcess(RouteResult mtachUrlResult);
		void Register(ActionInfo action);
	}
}