package com.example.util;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedisPool;

/**
 * ShardedJedis 不能用于Redis集群，要用JedisCluster 代替ShardedJedis 
 * 
 * @author Administrator
 * 
 */
@Component
public class RedisUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);
	
	private static final String KEY_SPLIT = ":"; //用于隔开缓存前缀与缓存键值
	
	@Resource
	ShardedJedisPool shardedJedisPool;

	@Resource
	JedisCluster jedisCluster;

	public boolean set(String key, String value) {
		if (value == null) {
			return false;
		}

		// ShardedJedis shardedJedis = null;

		try {
			// shardedJedis = shardedJedisPool.getResource();
			// shardedJedis.set(key, value);

			jedisCluster.set(key, value);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}

	public String get(String key) {
		if (key == null) {
			return "";
		}

		// ShardedJedis shardedJedis = null;

		try {
			// shardedJedis = shardedJedisPool.getResource();
			// return shardedJedis.get(key);

			return jedisCluster.get(key);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "";
	}

	 
	/**
	 * 设置缓存
	 * @param prefix 缓存前缀（用于区分缓存，防止缓存键值重复）
	 * @param key    缓存key
	 * @param value  缓存value
	 */
	public void set(String prefix, String key, String value) {
	 
	    if(StringUtils.isBlank(prefix)) throw new IllegalArgumentException("prefix must not null!");
	    if(StringUtils.isBlank(key)) throw new IllegalArgumentException("key must not null!");
	 
	    jedisCluster.set(prefix + KEY_SPLIT + key, value);
	    LOGGER.debug("RedisUtil:set cache key={},value={}", prefix + KEY_SPLIT + key, value);
	}
	 
	/**
	 * 设置缓存，并且自己指定过期时间
	 * @param prefix
	 * @param key
	 * @param value
	 * @param expireTime 过期时间
	 */
	public void setWithExpireTime(String prefix, String key, String value, int expireTime) {
	 
	    if(StringUtils.isBlank(prefix)) throw new IllegalArgumentException("prefix must not null!");
	    if(StringUtils.isBlank(key)) throw new IllegalArgumentException("key must not null!");
	 
	    jedisCluster.setex(prefix + KEY_SPLIT + key, expireTime, value);
	    LOGGER.debug("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", prefix + KEY_SPLIT + key, value,
	        expireTime);
	}
	
	/**
	 * 获取指定key的缓存
	 * @param prefix
	 * @param key
	 */
	public String get(String prefix, String key) {
	    if(StringUtils.isBlank(prefix)) throw new IllegalArgumentException("prefix must not null!");
	    if(StringUtils.isBlank(key)) throw new IllegalArgumentException("key must not null!");
	 
	    String value = jedisCluster.get(prefix + KEY_SPLIT + key);
	    LOGGER.debug("RedisUtil:get cache key={},value={}", prefix + KEY_SPLIT + key, value);
	    return value;
	}
	 
	/**
	 * 删除指定key的缓存
	 * @param prefix
	 * @param key
	 */
	public void deleteWithPrefix(String prefix, String key) {
	    if(StringUtils.isBlank(prefix)) throw new IllegalArgumentException("prefix must not null!");
	    if(StringUtils.isBlank(key)) throw new IllegalArgumentException("key must not null!");
	 
	    jedisCluster.del(prefix + KEY_SPLIT + key);
	    LOGGER.debug("RedisUtil:delete cache key={}", prefix + KEY_SPLIT + key);
	}
	 
	public void delete(String key) {
	    if(StringUtils.isBlank(key)) throw new IllegalArgumentException("key must not null!");
	 
	    jedisCluster.del(key);
	    LOGGER.debug("RedisUtil:delete cache key={}", key);
	}

}
