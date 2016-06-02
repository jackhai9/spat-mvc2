package cn.hofan.spat.mvc2.view;

import java.io.IOException;

import cn.hofan.spat.mvc2.ActionResult;
import cn.hofan.spat.mvc2.BeatContext;

public class RedirectResult extends ActionResult {
	public RedirectResult(String url) {
		this.url = url;
	}

	private String url;

	/**
	 * @return the viewName
	 */
	public String getUrl() {
		return url;
	}

	@Override
	public void render(BeatContext beat) throws IOException {
		beat.getResponse().sendRedirect(url);
		
	}
}