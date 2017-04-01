package com.whty.euicc.rsp.cache;

import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;
/**
 * http://developer.51cto.com/art/201411/456219.htm
 * 基于JVM的缓存处理
 * @author 11
 *
 */
public class JvmCacheUtil extends BaseCacheUtil {
	public static Map<String, Cache> cacheMap = Maps.newConcurrentMap();

	@Override
	public void put(String key, Object value) {
		Cache cache = new Cache.CacheBuilder(key).setValue(value)
				.setIsExpired(false).build();
		System.out.println(cache.toString());
		cacheMap.put(key, cache);
	}

	@Override
	public void put(String key, Object value, long millis) {
		Cache cache = new Cache.CacheBuilder(key).setValue(value)
				.setIsExpired(true)
				.setTimeOut(millis + System.currentTimeMillis()).build();
		System.out.println(cache.toString());
		cacheMap.put(key, cache);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> type) {
		Cache cache = cacheMap.get(key);
		
		if(cacheExpired(cache))return null;
		return (T) cache.getValue();
	}

	@Override
	public void remove(String key) {
		if(cacheMap.containsKey(key))cacheMap.remove(key);
	}

	@Override
	public boolean expire(String key, long millis) {
		Cache cache = cacheMap.get(key);
		cache.setExpired(true);
		cache.setTimeOut(cache.getTimeOut() + millis);
		return true;
	}

	@Override
	public boolean persist(String key) {
		Cache cache = cacheMap.get(key);
		if(cacheExpired(cache))return false;
		return cacheMap.containsKey(key);
	}

	@Override
	public String getString(String key) {
		Cache cache = cacheMap.get(key);
		if(cacheExpired(cache))return null;
		return String.valueOf(cache.getValue());
	}

	@Override
	public Integer getInteger(String key) {
		Cache cache = cacheMap.get(key);
		if(cacheExpired(cache))return null;
		return (Integer) cache.getValue();
	}

	@Override
	public Long getLong(String key) {
		Cache cache = cacheMap.get(key);
		if(cacheExpired(cache))return null;
		return (Long) cache.getValue();
	}

	@Override
	public Date getDate(String key) {
		Cache cache = cacheMap.get(key);
		if(cacheExpired(cache))return null;
		return (Date) cache.getValue();
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
