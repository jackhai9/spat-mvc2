package cn.hofan.spat.mvc2.server;

import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.client.CookieHandler;

/**
 * 用于获取和设置与后台有关的信息
 * @author renjun
 *
 */
public class ServerContext {
	
	BeatContext beat;
	
	CookieHandler cookie;
	SessionHandler session;
	
	public ServerContext(BeatContext beat) {
		super();
		this.beat = beat;
	}
	
	/**
	 * session处理
	 * @return
	 */
	public SessionHandler getSessions(){
		if(session == null)
			session = new BaseSessionHandler(beat);
		return session;
	}
	
	/**
	 * 获得实际位置
	 * @return
	 */
	public String getRealPath(){
		return beat.getServletContext().getRealPath("/");
	}
	
	/**
	 * 获得部署路径
	 * @return
	 */
	public String getContextPath(){
		return beat.getServletContext().getContextPath();
	}

}
