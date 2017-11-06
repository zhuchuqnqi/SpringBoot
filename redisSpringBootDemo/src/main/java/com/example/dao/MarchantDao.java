package com.example.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.example.mapper.MarchantMapper;
import com.example.util.RedisUtil;

@Repository
public class MarchantDao {
	
	@Resource
	private RedisUtil redisUtil;

	@Resource
	private MarchantMapper marchantMapper;
	
	

}
