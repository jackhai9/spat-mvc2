package cn.hofan.spat.mvc2;



import cn.hofan.spat.mvc2.view.JspViewResult;
import cn.hofan.spat.mvc2.view.RedirectResult;
import cn.hofan.spat.mvc2.view.VelocityViewResult;


/**
 * 所有Action的返回结果
 * @author renjun
 *
 */
public abstract class ActionResult {
	
	/**
	 * 用视图显示
	 * @param viewName
	 * @return
	 */
	public static ActionResult view(String viewName) {
		// TODO: 这是一个硬编码
		if (MvcConstants.VIEW_ENGINE.equals("vm"))
			return new VelocityViewResult(viewName);
		else
			return new JspViewResult(viewName);		
	}
	
	/**
	 * 跳转到一个新页面
	 * @param redirectUrl
	 * @return
	 */
	public static ActionResult redirect(String redirectUrl){
		return new RedirectResult(redirectUrl);
	}
	
	/**
	 * 在服务器的Context，中跳转
	 * @param redirectRelativeUrl
	 * @return
	 */
	public static ActionResult redirectContext(final String redirectRelativeUrl){
		return new ActionResult() {

			@Override
			public void render(BeatContext beat) throws Exception {
				ActionResult result = redirect(beat.getServer().getContextPath() + redirectRelativeUrl);
				result.render(beat);
			}
			
		};
	}
	
	/**
	 * 返回一个文件
	 * @param fileName
	 * @return
	 */
	public static ActionResult Stream(String fileName){
		return null;
	}
	
	/**
	 * 用于生成显示页面
	 * @param beat
	 * @throws Exception
	 */
	public abstract void render(BeatContext beat)  throws Exception;

}
