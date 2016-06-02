package cn.hofan.spat.mvc2.thread;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UrlPathHelper;

import cn.hofan.spat.mvc2.ActionAttribute;
import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.Trace;
import cn.hofan.spat.mvc2.MVCHttpServletRequestWrapper;
import cn.hofan.spat.mvc2.bind.BeatBindResults;
import cn.hofan.spat.mvc2.cache.CacheContext;
import cn.hofan.spat.mvc2.client.ClientContext;
import cn.hofan.spat.mvc2.client.UploadRequest;
import cn.hofan.spat.mvc2.server.ServerContext;
import cn.hofan.spat.mvc2.trace.TraceInfo;


public class BeatContextBean implements BeatContext {
	
	public static final String BEAT_MODEL_ATTRIBUTE = BeatContextBean.class.getName() + ".MODEL";
	
	public static ServletContext servletContext = null;
	
	private final BeatContext.Model model = new BeatContext.Model();
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private String relativeUrl = null;
	
	private String[] paramWithoutValidate = null;
	
	private static UrlPathHelper urlPathHelper= new UrlPathHelper(); // TODO: 是否有线程问题
	
	ClientContext client;
	ServerContext server;
	CacheContext cache;
	
	private TraceInfo traceInfo;
	
	ActionAttribute action;
	
	private BeatBindResults bindResults = new BeatBindResults();

	public BeatContextBean(HttpServletRequest request, HttpServletResponse response) {
        
		
		HttpServletRequest convertRequest = new MVCHttpServletRequestWrapper(request, this);
		
		request = UploadRequest.wrapper(convertRequest);

		setRequest(request);
		
        setResponse(response);
        
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        relativeUrl = uri.substring(contextPath.length());        
        
        // TODO: 为什么是默认的/index.jsp
        if (StringUtils.equals("/index.jsp", relativeUrl)){
        	relativeUrl = "/";
        }

        client = new ClientContext(this);
        server = new ServerContext(this);
        cache = new CacheContext(this);

//        Trace.init(this);

    }

	private void setRequest(HttpServletRequest request) {
		this.request = request;		
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public HttpServletRequest getRequest() {
		return request;
	}
//	/**
//	 * 封装一个请求，暂时对其中的请求进行过滤。
//	 */
//	@Override
//	public HttpServletRequest getRequest() {
//		
//		return new ConvertHttpServletRequestWrapper(request);
//	}

	@Override
	public HttpServletResponse getResponse() {
		return response;
	}


	/* (non-Javadoc)
	 * @see cn.hofan.mvc2.BeatContext#getModel()
	 */
	@Override
	public Model getModel() {
		return model;
	}


	@Override
	public String getRelativeUrl() {
		return this.relativeUrl;
	}


	@Override
	public BeatBindResults getBindResults() {
		return this.bindResults;
	}

	@Override
	public ClientContext getClient() {
		return client;
	}


	@Override
	public ServletContext getServletContext() {
//		if(servletContext == null)
//			servletContext = this.getRequest().getServletContext();
		return servletContext;
	}


	@Override
	public ServerContext getServer() {
		return server;
	}


	@Override
	public ActionAttribute getAction() {
		return action;
	}
	
	public void setAction(ActionAttribute action){
		this.action = action;
	}


	@Override
	public CacheContext getCache() {
		return cache;
	}

	@Override
	public TraceInfo getTraceInfo() {
		
		return traceInfo;
	}
	public void setTraceInfo(TraceInfo traceInfo) {
		
		this.traceInfo = traceInfo;
	}

	@Override
	public void setParamWithoutValidate(String[] params) {
		
		this.paramWithoutValidate = params;
	}

	@Override
	public String[] getParamWithoutValidate() {
		
		return this.paramWithoutValidate;
	}

}
