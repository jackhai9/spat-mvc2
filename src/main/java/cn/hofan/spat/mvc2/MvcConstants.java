package cn.hofan.spat.mvc2;

import org.springframework.web.context.WebApplicationContext;

import cn.hofan.spat.mvc2.context.MvcWebAppContext;

public abstract class MvcConstants {
	
	/**
	 * Application Context的记录名次
	 * @author renjun
	 */
    public static final String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;

    /** Default config location for the root context */
    public static final String DEFAULT_CONFIG_LOCATION = "classpath*:MvcApplicationContext.xml"; // "/WEB-INF/MvcApplicationContext.xml";
    
    public static final String APPLICATION_CONTEXT_ID = MvcWebAppContext.class.getName() + "_CONTEXT_ID";
;
	
	public static final String VIEW_PREFIX = "views";
	
	public static final String RESOURCES_PREFIX = "/resources";
	
	public static final String VIEW_ENGINE = "vm"; // view引擎 jsp, vm
}
