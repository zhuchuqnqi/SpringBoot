package com.example.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.model.UserInfo;

@Service
public class UserService {
	
	@Resource
	private UserDao userDao;

	/**
	 * 通过set方法存储对象类型,保存到redis
	 * 
	 * @param User
	 */
	public void saveIntoRedis(UserInfo User) {
		// 存redis
		userDao.saveIntoRedis(User);
	}

	/**
	 * 通过set方法存储对象类型
	 * 
	 * @param User
	 */
	public void saveIntoDB(UserInfo User) {
		// 存MySql
		userDao.saveIntoDB(User);
	}

	/**
	 * 根据ID从Redis库获取UserInfo对象
	 */
	public UserInfo getUserFromRedis(int id) {

		// 取缓存
		return (UserInfo) userDao.getUserFromRedis(id);

	}

	/**
	 * 根据ID从数据库获取UserInfo对象
	 */
	public UserInfo getUserFromDB(int id) {

		return userDao.getUserFromDB(id);
	}
	
	
	public List<UserInfo> getAllUser(){
		
		return userDao.getAllUser();
	}
	
	public UserInfo findByName(String name){
		return userDao.findByName(name);
	}
}
