package cn.hofan.spat.mvc2.utils;

import cn.hofan.spat.mvc2.BeatContext;

public class Toolbox {

	public Toolbox(BeatContext beat) {
		super();
		this.browser = new Browser(beat);
		this.cookie = new CookieHandler(beat);
	}
	
	public final Browser browser;
	public final CookieHandler cookie;

}
