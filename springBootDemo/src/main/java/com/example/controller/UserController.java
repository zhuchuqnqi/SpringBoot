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
	public void saveIntoRedis(UserInfo user) {
		if(user!=null){
			userService.saveIntoRedis(user);
		}
	}

	@RequestMapping("/saveIntoDb")
	public void saveIntoDB(UserInfo user) {
		if(user!=null){
			userService.saveIntoDB(user);
		}
	}
	
	@RequestMapping("/getUserFromRedis")
	public UserInfo getuser(int id){
		
		return userService.getUserFromRedis(id);
	}
	
	@RequestMapping("/getuserFromDb")
	public UserInfo getuserFromDb(int id){
		return userService.getUserFromDB(id);
	}
	
	@RequestMapping("/getAllUser")
	public List<UserInfo> getAllUser(){
		return  userService.getAllUser();
	}
}
