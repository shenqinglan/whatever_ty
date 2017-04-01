package com.whty.euicc.util;

import java.util.Map;

import com.google.common.collect.Maps;

public class CacheUtil {
	public static Map<String, Cache> cacheMap = Maps.newConcurrentMap();
	public static void put(String key, Object value) {
		Cache cache = new Cache.CacheBuilder(key).setValue(value)
				.setIsExpired(false).build();
		System.out.println(cache.toString());
		cacheMap.put(key, cache);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> type) {
		Cache cache = cacheMap.get(key);
		return (T) cache.getValue();
	}
	
	public static String getString(String key) {
		Cache cache = cacheMap.get(key);
		if(cacheExpired(cache))return null;
		return String.valueOf(cache.getValue());
	}
	/**
	 * 判断缓存是否失效
	 * @param cache
	 * @return true:失效 false:非失效
	 */
	private static boolean cacheExpired(Cache cache) {
		if (null == cache) { // 传入的缓存不存在
			System.out.println("传入的缓存不存在");
			return true;
		}
		
		if(!cache.isExpired())return false;//永不失效
		
		long nowDt = System.currentTimeMillis(); // 系统当前的毫秒数
		long cacheDt = cache.getTimeOut(); // 缓存内的过期毫秒数
		System.out.println(cacheDt);
		System.out.println(nowDt);
		boolean isExpired = cacheDt > nowDt ? false : true;
		if(isExpired)cacheMap.remove(cache.getKey());
		return isExpired;
	}


}
