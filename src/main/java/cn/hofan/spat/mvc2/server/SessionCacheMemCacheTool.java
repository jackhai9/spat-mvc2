package cn.hofan.spat.mvc2.server;
import java.io.File;

import cn.hofan.spat.memcached.SpatXmemcachedClient;
import cn.hofan.spat.mvc2.MVC;

public class SessionCacheMemCacheTool {

	private static SpatXmemcachedClient mc = null;
	
	/**
	 * 获得Cache对象
	 * @return
	 */
	public static SpatXmemcachedClient getCache() {
		
		if (mc  != null) return mc;
		
		String path = MVC.getNameSpaceFolderPath() + "/session_memcache.properties";

		File cacheFile = new File(path);
		
		if (!cacheFile.exists()) return null;
		
		synchronized (SessionCacheMemCacheTool.class){
			if (mc  != null) return mc;
			mc = SpatXmemcachedClient.getXmemcachedClient(path);
			return mc;
		}
	}
	
}
