package cn.hofan.spat.mvc2.client;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


public class UploadRequest extends HttpServletRequestWrapper {
	
	MultipartHttpServletRequest springRquest;
	
	public UploadRequest(HttpServletRequest request) {
		super(request);
		
		CommonsMultipartResolver commonsMultipartResolver;
		// 创建一个通用的多部分解析器.
		commonsMultipartResolver = new CommonsMultipartResolver(request
				.getSession().getServletContext());
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		
		// 转换成多部分request
		springRquest = commonsMultipartResolver
				.resolveMultipart(request);
		
		//springRquest = new DefaultMultipartHttpServletRequest(request);
	}
	
	
	@Override
	public Enumeration<String> getParameterNames() {
		return springRquest.getParameterNames();
	}
	
	@Override
	public String getParameter(String name) {
		return springRquest.getParameter(name);
	}
	
	@Override
	public String[] getParameterValues(String name) {
		return springRquest.getParameterValues(name);
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		return springRquest.getParameterMap();
	}
	

	public Iterator<String> getFileNames() {
		return springRquest.getFileNames();
	}

	public RequestFile getFile(String name) {
		return new RequestFile(springRquest.getFile(name));
	}

	public List<RequestFile> getFiles(String name) {
		List<RequestFile> result = new ArrayList<RequestFile>();
		for (MultipartFile f : springRquest.getFiles(name)) {
			result.add(new RequestFile(f));
		}

		return result;
	}
	
	
	public static boolean isMultipart(HttpServletRequest request) {
		return (request != null && ServletFileUpload.isMultipartContent(request));
	}
	
	public static HttpServletRequest wrapper(HttpServletRequest request){
		return isMultipart(request) ? new UploadRequest(request) : request;
	}
	
}
