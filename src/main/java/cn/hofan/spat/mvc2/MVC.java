package cn.hofan.spat.mvc2;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import cn.hofan.spat.mvc2.utils.FileUtil;

public class MVC {
	
	private static String namespace;
	private static String configPath;
	static{
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/namespace.properties");
			if(is == null){
				throw new Exception("namespace.properties is not exist");
			}
			Properties p = new Properties();
			p.load(is);
			namespace = p.getProperty("namespace").trim();
			is.close();
			configPath = FileUtil.getRootPath()+"/opt/mvc";
			File f1 = new File(getNameSpaceFolderPath());
			f1.mkdirs();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static String getConfigFolderPath(){
		return configPath;
	}
	
	public static String getNameSpace(){
		return namespace;
	}
	
	public static String getNameSpaceFolderPath(){
		return getConfigFolderPath()+"/"+getNameSpace();
	}
	
	private static String realPath = null;
	public static void init(String str){
		realPath = str;
	}
	public static String getRealProjectFolder(){
		return realPath;
	}

}
