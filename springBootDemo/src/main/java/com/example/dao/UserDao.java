package com.example.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.example.mapper.UserMapper;
import com.example.model.UserInfo;

@Repository
public class UserDao {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	// 操作字符串
	@Resource(name = "stringRedisTemplate")
	ValueOperations<String, String> valOpsStr;

	@Autowired
	RedisTemplate<Object, Object> redisTemplate;

	// 操作对象
	@Resource(name = "redisTemplate")
	ValueOperations<Object, Object> valOps;

	@Autowired
	private UserMapper userMapper;

	/**
	 * 通过set方法存储字符串类型
	 * 
	 * @param User
	 */
	public void stringRedisTemplatedDao() {
		valOpsStr.set("xx", "yy");
	}

	/**
	 * 通过set方法存储对象类型,保存到redis
	 * 
	 * @param User
	 */
	public void saveIntoRedis(UserInfo User) {
		valOps.set(User.getId(), User);
	}

	/**
	 * 通过set方法存储对象类型
	 * 
	 * @param User
	 */
	public void saveIntoDB(UserInfo User) {
		// 存MySql
		userMapper.insert(User);
	}

	/**
	 * 根据ID从Redis库获取UserInfo对象
	 */
	public UserInfo getUserFromRedis(int id) {

		// 取缓存
		return (UserInfo) valOps.get(id);

	}

	/**
	 * 根据ID从数据库获取UserInfo对象
	 */
	public UserInfo getUserFromDB(int id) {

		return userMapper.selectByPrimaryKey(id);
	}

	public List<UserInfo> getAllUser(){
		
		return userMapper.selectAll();
	}
}
