package com.whty.euicc.common.utils;

import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import redis.clients.jedis.ShardedJedis ;
import redis.clients.jedis.ShardedJedisPool ;

/**
 * 连接和使用redis资源的工具类
 * 
 * @author dengjun
 * @since 2014-9-25
 */
public class RedisUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class) ;

	/**
	 * 数据源 初始化spring注入
	 */
	private static ShardedJedisPool shardedJedisPool;

	public final static String KEY_PREFIX = "TMS_CACHE_";
	
	/**
	 * 获取数据库连接
	 * 
	 * @return conn
	 */
	public ShardedJedis getConnection() {
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
		} catch (Exception e) {
			logger.error("redis服务器异常");
		}
		return jedis;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param jedis
	 */
	public void closeConnection(ShardedJedis jedis) {
		if (null != jedis) {
			try {
				shardedJedisPool.returnResource(jedis);
			} catch (Exception e) {
				logger.error("redis服务器异常");
			}
		}
	}

	/**
	 * 设置数据
	 * 
	 * @param key
	 * @param value
	 */
	public static boolean setData(String key, String value) {
		try {
			key = getKey(key);
			ShardedJedis jedis = shardedJedisPool.getResource();
			jedis.set(key, value);
			shardedJedisPool.returnResource(jedis);
			return true;
		} catch (Exception e) {
			logger.error("redis服务器异常");

		}
		return false;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 */
	public static String getData(String key) {
		String value = null;
		try {
			ShardedJedis jedis = shardedJedisPool.getResource();
			key = getKey(key);
			value = jedis.get(key);
			shardedJedisPool.returnResource(jedis);
			return value;
		} catch (Exception e) {
			logger.error("redis服务器异常");

		}
		return value;
	}

	/**
	 * 删除数据
	 * 
	 * @param key
	 * @return
	 */
	public static boolean deleteData(String key) {
		try {
			key = getKey(key);
			ShardedJedis jedis = shardedJedisPool.getResource();
			jedis.del(key);
			shardedJedisPool.returnResource(jedis);
			return true;
		} catch (Exception e) {
			logger.error("redis服务器异常");

		}
		return false;
	}

	/**
	 * 获得对象 add by wgh
	 * 
	 * @param key
	 * @return
	 */
	public static Object getObject(String key) {
		Object value = null;
		try {
			ShardedJedis jedis = shardedJedisPool.getResource();
			key = getKey(key);
			value = ObjectTranscoder.deserialize(jedis.get(key.getBytes()));
			shardedJedisPool.returnResource(jedis);
			return value;
		} catch (Exception e) {
			logger.error("redis服务器异常");
		}
		return value;
	}

	/**
	 * 设置对象 add by wgh
	 * 
	 * @param key
	 * @return
	 */
	public static boolean setObject(String key, Object value) {
		try {
			key = getKey(key);
			ShardedJedis jedis = shardedJedisPool.getResource();
			jedis.set(key.getBytes(), ObjectTranscoder.serialize(value));
			shardedJedisPool.returnResource(jedis);
			return true;
		} catch (Exception e) {
			logger.error("redis服务器异常");

		}
		return false;
	}

	/**
	 * 设置连接池
	 * 
	 * @return 设置数据源
	 */
	@SuppressWarnings("static-access")
	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}


	/**
	 * 获取连接池
	 * 
	 * @return 数据源
	 */
	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	private static String getKey(String key) {
		return KEY_PREFIX + key;
	}





}
