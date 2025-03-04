package com.yaksha.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.yaksha.assignment.aspects.LoggingAspect;
import com.yaksha.assignment.services.UserService;

@Configuration
@EnableAspectJAutoProxy // Enable AOP proxying
@ComponentScan(basePackages = "com.yaksha.assignment") // Scan components under this base package
public class AppConfig {

	// Define UserService bean
	@Bean
	public UserService userService() {
		return new UserService();
	}

	// Define LoggingAspect bean
	@Bean
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}
}
