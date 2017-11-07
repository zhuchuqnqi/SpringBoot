package com.example.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = { "com.example.*" })
@MapperScan(basePackages = "com.example.mapper")
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(
				SpringBootDemoApplication.class);
		app.run(args);
	}

}
