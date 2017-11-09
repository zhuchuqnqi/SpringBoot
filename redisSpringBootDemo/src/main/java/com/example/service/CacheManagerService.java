package com.example.service;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.example.constant.Constant;
import com.example.model.MarchantInfo;
import com.example.util.RedisUtil;

@Service
public class CacheManagerService implements
		ApplicationListener<ContextRefreshedEvent> {

	@Resource
	private RedisUtil redisUtil;

	@Resource
	private MarchantService marchantService;

	/**
	 * 容器加载完成后执行此方法
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			@SuppressWarnings("rawtypes")
			Class clazz = Class
					.forName("com.example.service.CacheManagerService");

			Method[] methods = clazz.getMethods();

			// 执行本类中所有以getCache开头的方法（无参）
			for (Method method : methods) {
				if (method.getName().startsWith("getCache")) {
					method.invoke(this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载商户限额信息
	 */
	public void getCacheMarchanrInfo() {
		System.out.println("加载缓存:商户限额信息");

		// 从DB获取商户限额信息并缓存到Redis
		List<MarchantInfo> marchList = marchantService.queryAllMarchInfo();

		if (!marchList.isEmpty()) {

			for (MarchantInfo march : marchList) {
				redisUtil.set(Constant.MARCH_LIMITED_PREFIX,
						march.getMarchCode(),
						String.valueOf(march.getDaySumLimited()));
			}
		}
	}

}
