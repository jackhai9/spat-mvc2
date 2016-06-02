package cn.hofan.spat.mvc2.server;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import cn.hofan.spat.mvc2.BeatContext;

public class BaseSessionHandler extends SessionHandler {
	
	BeatContext beat;
	
	HttpSession session;

	public BaseSessionHandler(BeatContext beat) {
		super();
		this.beat = beat;
		session = this.beat.getRequest().getSession();
	}
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#get(java.lang.String)
	 */
	@Override
	public Object get(String name){
		return session.getAttribute(name);
		
	}
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#getCreationTime()
	 */
	@Override
	public Object getCreationTime(){
		return session.getCreationTime();
	}
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#getNames()
	 */
	@Override
	public Enumeration<String> getNames(){
		return session.getAttributeNames();
	}
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#getId()
	 */
	@Override
	public String getId(){
		return session.getId();
	}
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#getLastAccessedTime()
	 */
	@Override
	public long getLastAccessedTime(){
		return session.getLastAccessedTime();
	}
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#getMaxInactiveInterval()
	 */
	@Override
	public int getMaxInactiveInterval(){
		return session.getMaxInactiveInterval();
	}
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#invalidate()
	 */
	@Override
	public void invalidate(){
		session.invalidate();
	}
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#isNew()
	 */
	@Override
	public boolean isNew(){
		return session.isNew();
	}
	
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#remove(java.lang.String)
	 */
	@Override
	public void remove(String name){
		session.removeAttribute(name);
	}
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public void set(String name, Object value){
		session.setAttribute(name, value);
	}
	
	/* (non-Javadoc)
	 * @see cn.hofan.spat.mvc2.server.Session#setMaxInactiveInterval(int)
	 */
	@Override
	public void setMaxInactiveInterval(int value){
		session.setMaxInactiveInterval(value);
	}

	@Override
	public void flush() {
	}
	
	
	
	
	
}
