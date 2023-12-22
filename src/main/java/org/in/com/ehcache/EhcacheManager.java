package org.in.com.ehcache;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

public class EhcacheManager {

	public static CacheManager cachemanager;
	public static Cache usercache;

	public static void InitCacheManager(URL url) {
		if (cachemanager == null) {
			cachemanager = new CacheManager(url);
		}
	}

	public void Usercache() {
		Ehcache cache = cachemanager.getCache("users");
		cache.removeAll();
		System.out.println("cache removed");
	}

	public static CacheManager getcachemanager() {
		return cachemanager;
	}

	public static Cache getUserCache() {
		if (usercache == null) {
			usercache = getCacheManager().getCache("users");
		}
		return usercache;
	}

	public static CacheManager getCacheManager() {
		return cachemanager;
	}

}