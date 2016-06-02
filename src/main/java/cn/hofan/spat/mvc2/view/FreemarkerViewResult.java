package cn.hofan.spat.mvc2.view;


import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.thread.BeatContextBean;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreemarkerViewResult extends ActionResult{
	

	static Configuration cfg = null;
	final static String ftlViewPath = "ftlviews"; 
	final static String suffix = ".ftl";

	static{
		
		try{
			/* 在整个应用的生命周期中，这个工作你应该只做一次。 */ 
			/* 创建和调整配置。 */
			String webAppPath = BeatContextBean.servletContext.getRealPath("/");
		
			cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(
		                new File(webAppPath + "/" + ftlViewPath + "/"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setDefaultEncoding("UTF-8");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据viewName得到对应的ActionResult
	 * @param viewName
	 * @return
	 */
	public static ActionResult view(String viewName) {
		FreemarkerViewResult result = new FreemarkerViewResult(viewName);
		return result;
	}
	
	
	private String viewName;
	
	public FreemarkerViewResult(String viewName){
    	this.viewName = viewName;
    }
    
	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	@Override
	public void render(BeatContext beat) throws Exception {

		
		/* 在整个应用的生命周期中，这个工作你可以执行多次 */ 
		/* 获取或创建模板*/
		Template temp = cfg.getTemplate(viewName + suffix,"UTF-8");
		
        HttpServletResponse response = beat.getResponse();
        
		Writer out = new OutputStreamWriter(response.getOutputStream(),"UTF-8");
		
		temp.process(beat.getModel().getModel(), out);
		out.flush();
	}

}
