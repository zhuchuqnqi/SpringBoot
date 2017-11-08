package com.example.cache;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.constant.Constant;
import com.example.model.MarchantInfo;
import com.example.service.MarchantService;
import com.example.util.RedisUtil;

@Component
public class InitCache implements ApplicationListener<ContextRefreshedEvent> {

	@Resource
	private RedisUtil redisUtil;
	
	@Resource
	private MarchantService marchantService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		init();
	}
	
	public void init(){
		List<MarchantInfo> marchList = marchantService.queryAllMarchInfo();
		
		if(!marchList.isEmpty()){
			
			for(MarchantInfo march:marchList){
				redisUtil.set(Constant.MARCH_LIMITED_PREFIX, march.getMarchCode(), String.valueOf(march.getDaySumLimited()));
			}
		}
	}

}