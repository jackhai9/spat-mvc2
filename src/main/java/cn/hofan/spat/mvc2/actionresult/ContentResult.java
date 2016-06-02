package cn.hofan.spat.mvc2.actionresult;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;

public class ContentResult extends ActionResult {
	
	public String content = "";
	
	

	public ContentResult(String content) {
		super();
		this.content = content;
	}



	@Override
	public void render(BeatContext beat) throws Exception {
		beat.getResponse().getOutputStream().print(content);

	}

}
