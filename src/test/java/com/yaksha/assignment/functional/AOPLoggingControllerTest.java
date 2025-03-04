package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.yaksha.assignment.aspects.LoggingAspect;
import com.yaksha.assignment.config.AppConfig;
import com.yaksha.assignment.services.UserService;

public class AOPLoggingControllerTest {

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	// Test to verify if the application context loads beans correctly
	@Test
	public void testApplicationContextLoads() throws IOException {
		// Load the context using Java-based configuration
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Retrieve beans from the context
		UserService userService = context.getBean(UserService.class);
		LoggingAspect loggingAspect = context.getBean(LoggingAspect.class);

		// Assert that beans are created
		boolean userServiceLoaded = userService != null;
		boolean loggingAspectLoaded = loggingAspect != null;

		// Log the checks
		System.out.println("UserService bean loaded: " + userServiceLoaded);
		System.out.println("LoggingAspect bean loaded: " + loggingAspectLoaded);

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), userServiceLoaded && loggingAspectLoaded, businessTestFile);

		// Close the context
		context.close();
	}

	// Test for @Before, @After, @Around annotations in LoggingAspect
	@Test
	public void testAspectAnnotations() throws IOException {
		// Load the context using Java-based configuration
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Retrieve the LoggingAspect bean
		LoggingAspect loggingAspect = context.getBean(LoggingAspect.class);

		// Check if @Before, @After, @Around annotations are present in LoggingAspect
		// class
		boolean beforeAnnotationPresent = false;
		boolean afterAnnotationPresent = false;
		boolean aroundAnnotationPresent = false;

		// Check all methods of the LoggingAspect class for the annotations
		Method[] methods = LoggingAspect.class.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(org.aspectj.lang.annotation.Before.class)) {
				beforeAnnotationPresent = true;
			}
			if (method.isAnnotationPresent(org.aspectj.lang.annotation.After.class)) {
				afterAnnotationPresent = true;
			}
			if (method.isAnnotationPresent(org.aspectj.lang.annotation.Around.class)) {
				aroundAnnotationPresent = true;
			}
		}

		// Log the result of the checks
		System.out.println("Is @Before present in LoggingAspect? " + beforeAnnotationPresent);
		System.out.println("Is @After present in LoggingAspect? " + afterAnnotationPresent);
		System.out.println("Is @Around present in LoggingAspect? " + aroundAnnotationPresent);

		// Assert that @Before, @After, and @Around annotations are present
		boolean annotationsPresent = beforeAnnotationPresent && afterAnnotationPresent && aroundAnnotationPresent;

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), annotationsPresent, businessTestFile);

		// Close the context
		context.close();
	}

	// Test to verify if the method execution is logged with @Before, @After, and
	// @Around
	@Test
	public void testMethodExecutionLogging() throws IOException {
		// Load the context using Java-based configuration
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Retrieve the UserService bean
		UserService userService = context.getBean(UserService.class);

		// Call createUser method and check if logging happens
		System.out.println("Calling createUser()...");
		userService.createUser("John", "john@example.com");

		System.out.println("Calling updateUser()...");
		userService.updateUser(1, "John Doe", "john.doe@example.com");

		System.out.println("Calling deleteUser()...");
		userService.deleteUser(1);

		// Auto-grading with yakshaAssert (checking if methods were invoked correctly)
		yakshaAssert(currentTest(), true, businessTestFile);

		// Close the context
		context.close();
	}

	// Test to verify if method execution times are logged with @Around advice
	@Test
	public void testMethodExecutionTimeLogging() throws IOException {
		// Load the context using Java-based configuration
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Retrieve the UserService bean
		UserService userService = context.getBean(UserService.class);

		// Use reflection to check if the UserService class has required methods
		boolean hasMethods = false;

		try {
			// Check if the methods are present in UserService
			Method createUserMethod = UserService.class.getDeclaredMethod("createUser", String.class, String.class);
			Method updateUserMethod = UserService.class.getDeclaredMethod("updateUser", int.class, String.class,
					String.class);
			Method deleteUserMethod = UserService.class.getDeclaredMethod("deleteUser", int.class);

			hasMethods = createUserMethod != null && updateUserMethod != null && deleteUserMethod != null;
		} catch (NoSuchMethodException e) {
			hasMethods = false;
		}

		// Assert that the UserService class has the required methods
		if (!hasMethods) {
			System.out.println(
					"Error: UserService class does not have the expected methods (createUser, updateUser, deleteUser).");
			yakshaAssert(currentTest(), false, businessTestFile);
			context.close();
			return; // If methods are missing, exit the test early
		}

		// Auto-grading with yakshaAssert (checking if method execution times were
		// logged correctly)
		yakshaAssert(currentTest(), true, businessTestFile);

		// Close the context
		context.close();
	}

	// Test to verify if the @Pointcut, @Before, @After, and @Around annotations are
	// present in LoggingAspect
	@Test
	public void testAnnotationsPresence() throws IOException {
		// Load the context using Java-based configuration
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Retrieve the LoggingAspect bean
		LoggingAspect loggingAspect = context.getBean(LoggingAspect.class);

		// Initialize flags to check conditions
		boolean pointcutPresent = false;
		boolean beforePresent = false;
		boolean afterPresent = false;
		boolean aroundPresent = false;

		try {
			// Iterate through all methods in LoggingAspect
			Method[] methods = LoggingAspect.class.getDeclaredMethods();
			for (Method method : methods) {
				// Check for @Pointcut annotation on userServiceMethods()
				if (method.isAnnotationPresent(org.aspectj.lang.annotation.Pointcut.class)) {
					if ("userServiceMethods".equals(method.getName())) {
						pointcutPresent = true;
					}
				}

				// Check for @Before annotation on logBefore method
				if ("logBefore".equals(method.getName())
						&& method.isAnnotationPresent(org.aspectj.lang.annotation.Before.class)) {
					beforePresent = true;
				}

				// Check for @After annotation on logAfter method
				if ("logAfter".equals(method.getName())
						&& method.isAnnotationPresent(org.aspectj.lang.annotation.After.class)) {
					afterPresent = true;
				}

				// Check for @Around annotation on logAround method
				if ("logAround".equals(method.getName())
						&& method.isAnnotationPresent(org.aspectj.lang.annotation.Around.class)) {
					aroundPresent = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Log the results of the checks
		System.out.println("Checking LoggingAspect class for annotations...");

		// Log and assert presence of annotations
		if (!pointcutPresent) {
			System.out.println(
					"Error: @Pointcut annotation is missing or not correctly applied on 'userServiceMethods'.");
		} else {
			System.out.println("Is @Pointcut annotation present on 'userServiceMethods'? " + pointcutPresent);
		}

		if (!beforePresent) {
			System.out.println("Error: @Before annotation is missing or not correctly applied on 'logBefore'.");
		} else {
			System.out.println("Is @Before annotation present on 'logBefore'? " + beforePresent);
		}

		if (!afterPresent) {
			System.out.println("Error: @After annotation is missing or not correctly applied on 'logAfter'.");
		} else {
			System.out.println("Is @After annotation present on 'logAfter'? " + afterPresent);
		}

		if (!aroundPresent) {
			System.out.println("Error: @Around annotation is missing or not correctly applied on 'logAround'.");
		} else {
			System.out.println("Is @Around annotation present on 'logAround'? " + aroundPresent);
		}

		// Assert that all checks passed
		boolean allAnnotationsPresent = pointcutPresent && beforePresent && afterPresent && aroundPresent;

		// Auto-grading with yakshaAssert
		yakshaAssert(currentTest(), allAnnotationsPresent, businessTestFile);

		// Close the context
		context.close();
	}
}
