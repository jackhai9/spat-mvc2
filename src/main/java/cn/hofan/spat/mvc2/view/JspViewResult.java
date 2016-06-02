package cn.hofan.spat.mvc2.view;





import javax.servlet.http.HttpServletRequest;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;
import cn.hofan.spat.mvc2.MvcConstants;
import cn.hofan.spat.mvc2.BeatContext.Model;

public class JspViewResult extends ActionResult {
	
	private String prefix = MvcConstants.VIEW_PREFIX;
	private String suffix = ".jsp";
	
	public JspViewResult(String viewName) {
		this.viewName = viewName;
	}

	private String viewName;

	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	@Override
	public void render(BeatContext beat) throws Exception {
		String path = prefix + viewName + suffix;
		
		HttpServletRequest request = beat.getRequest();
		Model model = beat.getModel();
		for(String key : model.getModel().keySet())
			request.setAttribute(key, model.getAttributeValue(key));
		
		request.setAttribute("errors", beat.getBindResults().getErrors());
		
		request.getRequestDispatcher(path).forward(request, beat.getResponse());
		
	}
}