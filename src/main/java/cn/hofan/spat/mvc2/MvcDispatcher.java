package cn.hofan.spat.mvc2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import cn.hofan.spat.mvc2.bind.BindAndValidate;
import cn.hofan.spat.mvc2.context.MvcWebAppContext;
import cn.hofan.spat.mvc2.invoke.ActionInvoker;
import cn.hofan.spat.mvc2.route.BeatRouter;
import cn.hofan.spat.mvc2.route.RouteResult;
import cn.hofan.spat.mvc2.route.RouterBuilder;
import cn.hofan.spat.mvc2.thread.BeatContextBean;
import cn.hofan.spat.mvc2.thread.BeatContextUtils;
import cn.hofan.spat.mvc2.view.VelocityTemplateFactory;

/**
 * 用于处理Rest请求调度的核心类
 * @author renjun (jun.ren@gmail.com)
 *
 * TODO: 启动时可以读web.xml中对spring的配置
 *
 */
class MvcDispatcher {
	
	/**
	 * 管理url模版与执行器间的对应关系
	 */
	private BeatRouter router = null;
	
	private ServletContext sc;
	
	private ActionInvoker handler = new ActionInvoker();

	/**
	 * 根据ServletContext建立调度
	 * 主要过程：建立url模版与执行器间的对应关系
	 * @param sc
	 * @throws ServletException
	 */
	public MvcDispatcher(ServletContext sc) throws ServletException {
		this.sc = sc;
		
		/**
		 * 初始化配置文件
		 */
		System.out.println("Starting Mvc Webapplication ...");
		System.out.println("namespace:" + MVC.getNameSpace());
		
		VelocityTemplateFactory.init(sc);
		
		MvcWebAppContext context = initWebApplicationtext();

		context.refresh();

		BindAndValidate.setApplicationContext(context);
		//actionMapping.setApplicationContext(context);
		
		router = RouterBuilder.build(context);
		
		BeatContextBean.servletContext = sc;
	}
	
	/**
	 * 根据http请求处理
	 * 
	 * 过程：
	 * 查找对应的处理器，执行处理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public boolean service(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
//		 TODO: 这个需要挪个位置
//	     set default character encoding to "utf-8" if encoding is not set:
	    if (request.getCharacterEncoding()==null)
	    	request.setCharacterEncoding("UTF-8");
//	    request.getParameterMap();
		
	    // 包装整个请求的上下文
		BeatContext beat = BeatContextUtils.BeatContextWapper(request, response);
		
		// 查找匹配的结果
		RouteResult match = router.route(beat);
		
		if (match == null) return removeCurrentThread(false);
		
		// 设置Action
		// TODO:硬编码
		((BeatContextBean) beat).setAction(match.action);
		
		// 执行Action
		Object result =  handler.invoke(match); //match.execute();
		
		ActionResult actionResult = (ActionResult) result;
		
		// TODO: 是否需要返回
		if (actionResult == null) return removeCurrentThread(false);
		
		actionResult.render(beat);
		
		
		Trace.wrapper(beat);	
		//TODO: Cache 2011-05-16
		beat.getCache().setCacheResult();

		
		
		return removeCurrentThread(true);
		
	}
	
    public void destroy() {
    	
    	System.out.println("end Mvc WebApplication");
//        log.info("Destroy Dispatcher...");
    }
    
    private boolean removeCurrentThread(boolean result){
    	BeatContextUtils.remove();
    	return result;
    }
	
    /**
     * 初始化WebApplicationcontext
     * @return
     */
	private MvcWebAppContext initWebApplicationtext() {
		
        ApplicationContext oldRootContext = (ApplicationContext) sc.getAttribute(
                MvcConstants.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        
        // 如果web.xml配置使用了spring装载root应用context ...... 不可以
        // mvcFilter可能因为启动失败，在请求的时候容器还会尝试重新启动，此时rootContext可能已经存在，不要简单地抛出异常
        // 同时这样留出了使用Listener作为init context的扩展机会
        if (oldRootContext != null) {
            if (oldRootContext.getClass() != MvcWebAppContext.class) {
                throw new IllegalStateException(
                        "Cannot initialize context because there is already a root application context present - "
                                + "check whether you have multiple ContextLoader* definitions in your web.xml!");
            }
            return (MvcWebAppContext) oldRootContext;
        }
        
        MvcWebAppContext context = new MvcWebAppContext(sc, false);
        
        String contextConfigLocation =MvcConstants.DEFAULT_CONFIG_LOCATION;
        System.out.println("-------------contextConfigLocation------------"+contextConfigLocation);
        context.setConfigLocation(contextConfigLocation);
        context.setId("cn.hofan.mvc2.applicationcontext");
        System.out.println("----MvcWebAppContext----" + context);
		sc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
		return context;
	}
}
