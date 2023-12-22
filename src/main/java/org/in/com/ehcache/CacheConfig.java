package org.in.com.ehcache;

import java.net.URL;

import org.in.com.ehcache.EhcacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CacheConfig {
 
	@Bean(name = "CacheManager")
	public String initializeEhCache() {
		URL url = CacheConfig.class.getResource("/ehcache.xml");
		EhcacheManager.InitCacheManager(url);
		return "";
	}
}