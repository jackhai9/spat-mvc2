package cn.hofan.spat.mvc2.cache;
import java.io.File;

import cn.hofan.spat.memcached.SpatXmemcachedClient;
import cn.hofan.spat.mvc2.MVC;

public class PageCacheMemCacheTool {

	private static SpatXmemcachedClient mc = null;
	
	/**
	 * 获得Cache对象
	 * @return
	 */
	public static SpatXmemcachedClient getCache() {
		
		if (mc  != null) return mc;
		
		String path = MVC.getNameSpaceFolderPath() + "/pagecache_memcache.properties";

		File cacheFile = new File(path);
		
		if (!cacheFile.exists()) return null;
		
		synchronized (PageCacheMemCacheTool.class){
			if (mc  != null) return mc;
			mc = SpatXmemcachedClient.getXmemcachedClient(path);
			return mc;
		}
	}
	
}
