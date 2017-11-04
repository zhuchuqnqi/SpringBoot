package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.UserInfo;
import com.example.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping("/saveIntoRedis")
	public void saveIntoRedis(UserInfo User) {
		if(User!=null){
			userService.saveIntoRedis(User);
		}
	}

	
	@RequestMapping("/getUserFromRedis")
	public UserInfo getUserFromRedis(int id){
		
		return userService.getUserFromRedis(id);
	}
	
	@RequestMapping("/saveIntoDb")
	public void saveIntoDB(UserInfo user) {
		if(user!=null){
			userService.saveIntoDB(user);
		}
	}
	
	@RequestMapping("/getUserFromDb")
	public UserInfo getUserFromDb(int id){
		return userService.getUserFromDB(id);
	}
	
	@RequestMapping("/getAllUser")
	public List<UserInfo> getAllUser(){
		return  userService.getAllUser();
	}
	
	@RequestMapping("/getUserByName")
	public UserInfo findByName(String name){
		return userService.findByName(name);
	}
}
