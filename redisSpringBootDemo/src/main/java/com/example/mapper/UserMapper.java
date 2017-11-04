package com.example.mapper;

import com.example.model.UserInfo;
import com.example.util.MyMapper;

public interface UserMapper extends MyMapper<UserInfo>{
	
	public UserInfo findByName(String name);
}
