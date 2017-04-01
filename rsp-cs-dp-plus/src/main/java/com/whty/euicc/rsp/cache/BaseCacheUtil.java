package com.whty.euicc.rsp.cache;

import java.util.Date;

public abstract class BaseCacheUtil {
	public abstract void put(String key, Object value);

	public abstract void put(String key, Object value, long millis);

	public abstract <T> T get(String key, Class<T> type);

	public abstract void remove(String key);

	public abstract boolean expire(String key, long millis);

	public abstract boolean persist(String key);

	public abstract String getString(String key);

	public abstract Integer getInteger(String key);

	public abstract Long getLong(String key);

	public abstract Date getDate(String key);
}
