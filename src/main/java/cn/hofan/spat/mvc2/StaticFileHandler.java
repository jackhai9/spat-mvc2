package cn.hofan.spat.mvc2;



import java.io.IOException;





import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 处理静态文件请求
 * 
 * @author renjun (jun.ren@gmail.com)
 * 
 */
public class StaticFileHandler {
    
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    	// TODO: 是否是有路径注入..危险
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.substring(contextPath.length());
        if (url.toUpperCase().startsWith("/WEB-INF/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        int n = url.indexOf('?');
        if (n!=(-1))
            url = url.substring(0, n);
        n = url.indexOf('#');
        if (n!=(-1))
            url = url.substring(0, n);
             
        String path = MvcConstants.RESOURCES_PREFIX + url;
        request.getRequestDispatcher(path).forward(request, response);
       
    }

}
