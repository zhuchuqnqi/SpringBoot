package com.example.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "com.example.*" })
@MapperScan(basePackages = "com.example.mapper")
@SpringBootApplication
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(
				SpringBootDemoApplication.class);
		app.run(args);
	}
}
