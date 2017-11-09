package com.example.event;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public MyEvent(String  source) {
		super(source);
	}

	public void run(){
		System.out.println("MyEvent 运行！！！");
	}
	
}
