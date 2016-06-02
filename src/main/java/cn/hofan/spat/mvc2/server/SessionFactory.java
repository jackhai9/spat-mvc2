package cn.hofan.spat.mvc2.server;

import cn.hofan.spat.mvc2.BeatContext;



public interface SessionFactory {
	public static SessionFactory factory = null;
	
	SessionHandler get(BeatContext beat);
	
//	public static void setFactory(SessionFactory factory) {
////		SessionFactory.factory = factory;
//	}
}
