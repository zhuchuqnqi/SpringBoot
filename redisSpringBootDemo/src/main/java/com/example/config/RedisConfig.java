package com.example.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@Component
@SpringBootConfiguration
public class RedisConfig {

	@Bean
	public JedisPoolConfig jedisPoolConfig(
			@Value("${spring.redis.pool.max-active}") int maxTotal,
			@Value("${spring.redis.pool.max-idle}") int maxIdle,
			@Value("${spring.redis.timeout}") int maxWaitMillis) {

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

		return jedisPoolConfig;
	}

	@Bean
	public JedisCluster jedisCluster(
			@Value("${JedisCluster.notes}") String jedisClusterNotes,
			JedisPoolConfig jedisPoolConfig) {

		String[] serverArray = jedisClusterNotes.split(",");
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();

		for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer
					.valueOf(ipPortPair[1].trim())));
		}

		JedisCluster jedisCluster = new JedisCluster(nodes, jedisPoolConfig);
		return jedisCluster;
	}

	@Bean
	public JedisShardInfo jedisShardInfo(
			@Value("${spring.redis.host}") String host,
			@Value("${spring.redis.port}") int port) {
		return new JedisShardInfo(host, port);
	}

	@Bean
	public ShardedJedisPool shardedJedisPool(JedisPoolConfig jedisPoolConfig,
			List<JedisShardInfo> jedisShardInfoList) {

		return new ShardedJedisPool(jedisPoolConfig, jedisShardInfoList);
	}

}
