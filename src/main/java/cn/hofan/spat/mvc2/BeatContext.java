package cn.hofan.spat.mvc2;


import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import cn.hofan.spat.mvc2.bind.BeatBindResults;
import cn.hofan.spat.mvc2.cache.CacheContext;
import cn.hofan.spat.mvc2.client.ClientContext;
import cn.hofan.spat.mvc2.server.ServerContext;
import cn.hofan.spat.mvc2.trace.TraceInfo;




/**
 * 管理一个客户端请求的生命周期
 * @author renjun
 *
 */
public interface BeatContext {
	
	/**
	 * MVC 中的Model, 以key,value形式存放，可以由Controller传个View
	 * @return
	 */
	Model getModel();

    /**
     * 返回本次调用的 {@link HttpServletRequest}对象
     * 
     * @return
     */
    HttpServletRequest getRequest();

    /**
     * 返回本次调用的 {@link HttpServletResponse}对象
     * 
     * @return
     */
    HttpServletResponse getResponse();
    
    /**
     * 得到ServletContext信息
     * @return
     */
    ServletContext getServletContext();
    
    /**
     * 得到当前请求的相对url,不含ServerContext路径和参数
     * 已移至ClientContext
     * @return
     */
	@Deprecated
    String getRelativeUrl();
    
    /**
     * 得到绑定和校验的结果
     * @return
     */
    BeatBindResults getBindResults();
    
    ClientContext getClient();
    
    ServerContext getServer();
    
    ActionAttribute getAction();

    CacheContext getCache();
    
    TraceInfo getTraceInfo();
    
    void setParamWithoutValidate(String[] params);
    
    String[] getParamWithoutValidate();
    
    /**
     * MVC 中的Model, 以key,value形式存放，可以由Controller传个View
     * @author renjun
     *
     */
    public class Model {
    	
    	/** Model Map */
    	private ModelMap model;

    	/**
    	 * 已过期，请参考{@link #add(Object)}
    	 * 增加一个属性
    	 * @param attributeValue 属性值
    	 */
    	@Deprecated
    	public void addAttribute(Object attributeValue) {
    		getModelMap().addAttribute(attributeValue);

    	}
    	
    	/**
    	 * 增加一个属性
    	 * @param attributeValue 属性值
    	 */
    	public void add(Object attributeValue) {
    		getModelMap().addAttribute(attributeValue);

    	}

    	/**
    	 * 增加一个属性
    	 * @param attributeName 属性名称
    	 * @param attributeValue 属性值
    	 */
    	@Deprecated
    	public void addAttribute(String attributeName, Object attributeValue) {
    		getModelMap().addAttribute(attributeName, attributeValue);

    	}
    	
    	/**
    	 * 已过期，请参考{@link #add(String, Object)}
    	 * 增加一个属性
    	 * @param attributeName 属性名称
    	 * @param attributeValue 属性值
    	 */
    	public void add(String attributeName, Object attributeValue) {
    		getModelMap().addAttribute(attributeName, attributeValue);

    	}

    	/**
    	 * 根据属性名得到属性值
    	 * @param attributeName 属性名称
    	 * @return 对应的属性值
    	 */
    	public Object getAttributeValue(String attributeName) {
    		return getModelMap().get(attributeName);
    	}
    	
    	/**
    	 * 根据属性名得到属性值
    	 * @param attributeName 属性名称
    	 * @return 对应的属性值
    	 */
    	@Deprecated
    	public Object get(String attributeName) {
    		return getModelMap().get(attributeName);
    	}
    	


    	/**
    	 * Return the model map. Never returns <code>null</code>.
    	 * To be called by application code for modifying the model.
    	 */
    	public Map<String, Object> getModel() {
    		return getModelMap();
    	}


    	/**
    	 * 已过期，请参考{@link #addAll(Map)}
    	 * 批量增加属性
    	 * @param attributes
    	 */
    	@Deprecated
    	public void addAllAttributes(Map<String, ?> attributes) {
    		getModelMap().addAllAttributes(attributes);
    	}
    	

    	/**
    	 * 批量增加属性
    	 * @param attributes
    	 */
    	public void addAll(Map<String, ?> attributes) {
    		getModelMap().addAllAttributes(attributes);
    	}

    	/**
    	 * 已过期，请参考{@link #add(Object)}
    	 * 判断是否包含属性名
    	 * @param attributeName 需要查找的属性
    	 * @return
    	 */
    	@Deprecated
    	public boolean containsAttribute(String attributeName) {
    		return getModelMap().containsAttribute(attributeName);
    	}
    	
    	/**
    	 * 判断是否包含属性名
    	 * @param attributeName 需要查找的属性
    	 * @return
    	 */
    	public boolean contains(String attributeName) {
    		return getModelMap().containsAttribute(attributeName);
    	}


    	/**
    	 * 合并属性
    	 * @param attributes
    	 */
    	public void merge(Map<String, ?> attributes) {
    		getModelMap().mergeAttributes(attributes);
    	}
    	
    	
    	/**
    	 * Return the underlying <code>ModelMap</code> instance (never <code>null</code>).
    	 */
    	private ModelMap getModelMap() {
    		if (this.model == null) {
    			this.model = new ModelMap();
    		}
    		return this.model;
    	}
    	
    }

}