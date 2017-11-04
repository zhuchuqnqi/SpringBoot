package com.example.dao;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.mapper.UserMapper;
import com.example.model.UserInfo;
import com.example.util.RedisUtil;

@Repository
public class UserDao {

	@Resource
	RedisUtil redisUtil;

	@Autowired
	private UserMapper userMapper;

	/**
	 * 通过set方法存储字符串类型
	 * 
	 * @param User
	 */
	public void stringRedisTemplatedDao() {
		redisUtil.set("xx", "yy");
	}

	/**
	 * 通过set方法存储对象类型,保存到redis
	 * 
	 * @param User
	 */
	public void saveIntoRedis(UserInfo user) {
		String key = user.getId()+"";
		
		String value = JSONObject.fromObject(user).toString();
		
		// 存redis
		redisUtil.set(key,value);
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

		JSONObject jsonObj = JSONObject.fromObject(redisUtil.get(id+""));

		if(jsonObj !=null){
			return (UserInfo) JSONObject.toBean(jsonObj,UserInfo.class);
		}
		
		return null;
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

	public UserInfo findByName(String name){
		return userMapper.findByName(name);
	}
}
