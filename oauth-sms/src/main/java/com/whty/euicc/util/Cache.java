package com.whty.euicc.util;


public class Cache {
	private Object key;// 缓存ID
	private Object value;// 缓存数据
	private long timeOut;// 失效时间(毫秒)
	private boolean isExpired; //是否失效 （true:看失效时间、false:永不失效）
	
	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	public Object getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public long getTimeOut() {
		return timeOut;
	}

	private Cache(CacheBuilder builder){
		this.key = builder.key;
		this.value = builder.value;
		this.timeOut = builder.timeOut;
		this.isExpired = builder.isExpired;
	}
	
	@Override
	public String toString() {
		return "Cache [key=" + key + ", value=" + value + ", timeOut="
				+ timeOut + ", isExpired=" + isExpired + "]";
	}

	public static class CacheBuilder{
		private Object key;// 缓存ID
		private Object value;// 缓存数据
		private long timeOut;// 失效时间
		private boolean isExpired; //是否失效 
		public CacheBuilder(Object key) {
			this.key = key;
		}
		
		public CacheBuilder setValue(Object value){
			this.value = value;
			return this;
		}
		
		public CacheBuilder setTimeOut(long timeOut){
			this.timeOut = timeOut;
			return this;
		}
		
		public CacheBuilder setIsExpired(boolean isExpired){
			this.isExpired = isExpired;
			return this;
		}
		
		public Cache build(){
            return new Cache(this);
        }
	}
}
