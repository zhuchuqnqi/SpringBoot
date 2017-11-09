package com.example.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.event.MyEvent;

@RequestMapping("/MyEvent")
public class MyEventController implements ApplicationContextAware {

	private ApplicationContext context;

	@RequestMapping("/testMyEvent")
	public void publish(String msg) {

		MyEvent myEvent = new MyEvent(msg);
		// 发布事件
		context.publishEvent(myEvent);
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

}
