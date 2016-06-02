package cn.hofan.spat.mvc2;
import javax.servlet.ServletContext;

/**
 * Wrapper interface for ServletConfig and FilterConfig.
 * 
 * @author renjun (jun.ren@gmail.com)
 * 
 */
public interface Config {

    /**
     * Get ServletContext object.
     */
    public ServletContext getServletContext();

    /**
     * Get init parameter value by name.
     */
    public String getInitParameter(String name);

}
