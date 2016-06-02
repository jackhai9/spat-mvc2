package cn.hofan.spat.mvc2.view;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.runtime.RuntimeInstance;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.MvcConstants;

public class VelocityViewResult extends ActionResult {
	
	private String prefix = MvcConstants.VIEW_PREFIX;
	private String suffix = ".html";
	private String viewName;
   
    private static RuntimeInstance rtInstance = new RuntimeInstance();
    
    public VelocityViewResult(String viewName){
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
		
		String path = prefix  +"\\"+ viewName + suffix;

		Template template =  Velocity.getTemplate(path);
		

        HttpServletResponse response = beat.getResponse();
        response.setContentType("text/html;charset=\"UTF-8\"");
        response.setCharacterEncoding("UTF-8");
        // init context:
        Context context = new VelocityContext(beat.getModel().getModel());
        // render:
        VelocityWriter vw = new VelocityWriter(response.getWriter());
        try {
            template.merge(context, vw);
            vw.flush();
        }
        finally {
            vw.recycle(null);
        }		
	}

}
