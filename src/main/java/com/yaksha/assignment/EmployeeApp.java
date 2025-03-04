package com.yaksha.assignment;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.yaksha.assignment.config.AppConfig;
import com.yaksha.assignment.services.UserService;

public class EmployeeApp {

	public static void main(String[] args) {
		// Create an application context based on AppConfig
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Retrieve the UserService bean from the context
		UserService userService = context.getBean(UserService.class);

		// Create a user (this will be intercepted and logged by the AOP aspect)
		userService.createUser("John Doe", "john.doe@example.com");

		// Update a user (this will also be intercepted and logged)
//		userService.updateUser(1, "John Doe", "john.updated@example.com");

		// Delete a user
//		userService.deleteUser(1);

		// Close the context
		context.close();
	}
}
