package com.yaksha.assignment.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	// Pointcut expression to target methods in UserService class
	@Pointcut("execution(* com.yaksha.assignment.services.UserService.*(..))")
	public void userServiceMethods() {
		// Pointcut to match methods in UserService
	}

	// Before advice - logs method execution before the actual method runs
	@Before("userServiceMethods()")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println("Before method: " + joinPoint.getSignature().getName());
		System.out.println("Method Arguments: " + java.util.Arrays.toString(joinPoint.getArgs()));
	}

	// After advice - logs method execution after the actual method runs
	@After("userServiceMethods()")
	public void logAfter(JoinPoint joinPoint) {
		System.out.println("After method: " + joinPoint.getSignature().getName());
	}

	// Around advice - logs method execution time, result, and allows modifying the
	// method behavior
	@Around("userServiceMethods()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		// Proceed with the method execution
		Object result = joinPoint.proceed();

		long executionTime = System.currentTimeMillis() - startTime;
		System.out.println("Method execution time: " + executionTime + " ms");

		// Log result if the method has a result
		if (result != null) {
			System.out.println("Method result: " + result.toString());
		}

		return result; // Return the result of the method execution
	}
}
