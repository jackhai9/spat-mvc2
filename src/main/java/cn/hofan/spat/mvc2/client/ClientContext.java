package cn.hofan.spat.mvc2.client;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.server.SessionHandler;

/**
 * 获得客户端信息
 * @author renjun
 *
 */
public class ClientContext {
	
	final BeatContext beat;
	
	CookieHandler cookie;
	SessionHandler session;
	
	public ClientContext(BeatContext beat) {
		super();
		this.beat = beat;
	}


	public CookieHandler getCookies(){
		if (cookie == null)
			cookie = new CookieHandler(beat);
		return cookie;
	}
	
//	/**
//	 * 已移到ServerContext中
//	 * @return
//	 */
//	@Deprecated
//	protected SessionHandler getSessions(){
//		if(session == null)
//			session = new SessionHandler(beat);
//		return session;
//	}
	
	public UploadRequest getUploads(){
		HttpServletRequest request = beat.getRequest();
		return  (request instanceof UploadRequest) 
			? (UploadRequest) beat.getRequest() 
			: null;
	}
	
	public boolean isUpload(){
		return getUploads() == null ? false : true;
	}
	
    /**
     * 得到当前请求的相对url,不含ServerContext路径和参数
     * @return
     */
    public String getRelativeUrl(){
    	HttpServletRequest request = beat.getRequest();
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String relativeUrl = uri.substring(contextPath.length());
        
        // TODO: 为什么是默认的/index.jsp
        if (StringUtils.equals("/index.jsp", relativeUrl)){
        	relativeUrl = "/";
        }
        
        return relativeUrl;
    }
}
