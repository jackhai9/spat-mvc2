package cn.hofan.spat.mvc2.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hofan.spat.mvc2.BeatContext;
/**
 * Cookie的操作类
 * @author shy.qiu
 *
 */
public class CookieHandler {
	
	HttpServletResponse response;
	HttpServletRequest request;

	public CookieHandler(BeatContext beat) {
		super();
		response = beat.getResponse();
		request = beat.getRequest();
	}
	
	/**
	 * 通过name和value，增加一个cookie
	 * </br>
	 * 建议用set来代替
	 * @see #set(String, String)
	 * 
	 * @param name
	 * @param value
	 */
	public void addCookie(String name, String value){
		Cookie cookie = new Cookie(name, value);
		// 设置路径（默认）
		cookie.setPath("/");
		// 把cookie放入响应中
		response.addCookie(cookie);
	}
	
	/**
	 * 通过name和value,时间，增加一个cookie
	 * @param name
	 * @param value
	 * @param time
	 */
	public void addCookie(String name, String value, int time){
		Cookie cookie = new Cookie(name, value);
		// 设置有效日期
		cookie.setMaxAge(time);
		// 设置路径（默认）
		cookie.setPath("/");

		addCookie(cookie);
	}
	
	/**
	 * 增加Cookie
	 * @param cookie
	 */
	public void addCookie(Cookie cookie){
		response.addCookie(cookie);
	}
	
	/**
	 * 根据名称， 得到cookie的值
	 * @param name
	 * @return
	 */
	public String get(String name){
		Cookie cookie = getCookie(name);
		return cookie == null?  null : cookie.getValue();
	}
	
	/**
	 * 根据名称， 得到cookie对象
	 * @param name
	 * @return
	 */
	public Cookie getCookie(String name){
		Cookie[] cookies = request.getCookies();
		
		for(Cookie cookie : cookies){
			if(name.equalsIgnoreCase(cookie.getName()))
				return cookie;
		}
		
		return null;
	}
	
	/**
	 * 设置cookie的值
	 * 如果该cookie已经存在，则修改，
	 * 否则新增一个cookie
	 * @param name
	 * @param value
	 */
	public void set(String name, String value){
		Cookie cookie = getCookie(name);
		
		if (cookie == null){
			addCookie(name, value);
			return;
		}
		
		cookie.setValue(value);
	}
	
	/**
	 * 设置cookie的值
	 * 如果该cookie已经存在，则修改，
	 * @param name
	 * @param value
	 * @param time
	 */
	public void set(String name, String value, int time){
		Cookie cookie = getCookie(name);
		
		if (cookie == null){
			addCookie(name, value, time);
			return;
		}
		
		cookie.setValue(value);
		cookie.setMaxAge(time);
	}
	
	/**
	 * 删除一个cookie
	 * @param name
	 */
	public void delete(String name){
		Cookie cookie = getCookie(name);
		
		if (cookie == null) return;
		
		// 销毁
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}
