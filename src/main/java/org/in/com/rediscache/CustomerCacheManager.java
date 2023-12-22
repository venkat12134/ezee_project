package org.in.com.rediscache;

import org.in.com.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CustomerCacheManager {

	@Autowired
	private CacheManager cacheManager;

	public CustomerDTO getCustomerData(CustomerDTO customer) {
		Cache customerCache = cacheManager.getCache("customerCache");
		Cache.ValueWrapper cacheValue = customerCache.get(customer);
		if (cacheValue != null) {
			Object cachedData = cacheValue.get();
			System.out.println("Customer(redis) cache stored " + cachedData);
			if (cachedData instanceof CustomerDTO) {
				return (CustomerDTO) cachedData;
			}
		}
		return null;
	}

	public CustomerDTO putCustomerData(CustomerDTO customer, CustomerDTO customerdto) {
		Cache customerCache = cacheManager.getCache("customerCache");
		customerCache.put(customer, customerdto);
		return null;
	}
}