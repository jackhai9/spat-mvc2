package cn.hofan.spat.mvc2.utils;

import java.io.File;

public class FileUtil {
	public static String getRootPath() {
		File file = new File(System.getProperty("user.dir"));
		String path = file.getAbsolutePath().replace('\\', '/');
		path = path.substring(0, path.indexOf("/"));
		return path;
	}
	
	public static void main(String[] args) {
		System.out.println(getRootPath());
	}
}
