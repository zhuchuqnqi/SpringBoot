package com.example.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements ApplicationListener<ApplicationEvent>{

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof MyEvent){
			MyEvent myEvent = (MyEvent)event;
			
			myEvent.run();
			
			System.out.println("监听到MyEvent事件！！！！");
		}
		
	}

}
