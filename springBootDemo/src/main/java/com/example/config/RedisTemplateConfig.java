package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@ConfigurationProperties
public class RedisTemplateConfig {
	@Bean
	@SuppressWarnings({"rawtypes","unchecked"})
	public RedisTemplate<Object, Object> redisTemplate(
			RedisConnectionFactory factory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<Object,Object>();
		template.setConnectionFactory(factory);
		
		
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(
				Object.class);
		
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		
		//设置值（value）的序列化采用jackson2JsonRedisSerializer
		template.setValueSerializer(jackson2JsonRedisSerializer);
		
		//设置键（key）的序列化采用StringRedisSerializer
		template.setKeySerializer(new StringRedisSerializer());
		
		template.afterPropertiesSet();
		return template;
	}
}
