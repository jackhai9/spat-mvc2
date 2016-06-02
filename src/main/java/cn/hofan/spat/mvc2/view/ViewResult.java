package cn.hofan.spat.mvc2.view;







import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;


public class ViewResult extends ActionResult {
	
	
	public ViewResult(String viewName) {
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
		
	}
}