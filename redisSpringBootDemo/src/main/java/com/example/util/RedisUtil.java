package com.example.util;

import javax.annotation.Resource;

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

			// jedisCluster.set(key, value);
			jedisCluster.setex(key, 180, value); //设置有效时间,单位:秒
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

}
