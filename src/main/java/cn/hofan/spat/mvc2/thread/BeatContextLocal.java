package cn.hofan.spat.mvc2.thread;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hofan.spat.mvc2.ActionAttribute;
import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.bind.BeatBindResults;
import cn.hofan.spat.mvc2.cache.CacheContext;
import cn.hofan.spat.mvc2.client.ClientContext;
import cn.hofan.spat.mvc2.server.ServerContext;
import cn.hofan.spat.mvc2.trace.TraceInfo;

public class BeatContextLocal implements BeatContext {

	private BeatContext getCurrent() {

		return BeatContextUtils.getCurrent();
	}


	@Override
	public HttpServletRequest getRequest() {
		return getCurrent().getRequest();
	}

	@Override
	public HttpServletResponse getResponse() {
		return getCurrent().getResponse();
	}


	@Override
	public Model getModel() {
		return getCurrent().getModel();
	}


	@Override
	public String getRelativeUrl() {
		return getCurrent().getRelativeUrl();
	}


	@Override
	public BeatBindResults getBindResults() {
		return getCurrent().getBindResults();
	}


	@Override
	public ClientContext getClient() {
		return getCurrent().getClient();
	}


	@Override
	public ServletContext getServletContext() {
		return getCurrent().getServletContext();
	}


	@Override
	public ServerContext getServer() {
		return getCurrent().getServer();
	}


	@Override
	public ActionAttribute getAction() {
		return getCurrent().getAction();
	}


	@Override
	public CacheContext getCache() {
		return getCurrent().getCache();
	}


	@Override
	public TraceInfo getTraceInfo() {
		return getCurrent().getTraceInfo();
	}


	@Override
	public void setParamWithoutValidate(String[] params) {
		getCurrent().setParamWithoutValidate(params);
	}


	@Override
	public String[] getParamWithoutValidate() {
		
		return getCurrent().getParamWithoutValidate();
	}


}
