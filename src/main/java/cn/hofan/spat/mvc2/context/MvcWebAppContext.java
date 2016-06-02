package cn.hofan.spat.mvc2.context;

import javax.servlet.ServletContext;

import org.springframework.web.context.support.XmlWebApplicationContext;


/**
 * 
 * 利用spring XmlWebApplicationContext管理所有的Controller
 * 可以替换
 * @author renjun
 *
 */
public class MvcWebAppContext extends XmlWebApplicationContext {
    public MvcWebAppContext(ServletContext servletContext, boolean refresh) {
        this.setServletContext(servletContext);
        if (refresh) {
            refresh();
        }
    }
}
