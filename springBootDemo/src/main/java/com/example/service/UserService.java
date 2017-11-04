package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.model.UserInfo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	/**
	 * 通过set方法存储对象类型,保存到redis
	 * 
	 * @param User
	 */
	public void saveIntoRedis(UserInfo user) {
		// 存redis
		userDao.saveIntoRedis(user);
	}

	/**
	 * 通过set方法存储对象类型
	 * 
	 * @param User
	 */
	public void saveIntoDB(UserInfo user) {
		// 存MySql
		userDao.saveIntoDB(user);
	}

	/**
	 * 根据ID从Redis库获取UserInfo对象
	 */
	public UserInfo getUserFromRedis(int id) {

		return userDao.getUserFromRedis(id);

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
	
}
