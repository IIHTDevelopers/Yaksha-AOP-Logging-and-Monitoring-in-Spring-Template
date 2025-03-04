package com.yaksha.assignment.services;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	// Method to create a user
	public void createUser(String name, String email) {
		System.out.println("Creating user: " + name + ", Email: " + email);
		// Simulate some logic (e.g., saving user to the database)
	}

	// Method to update a user
	public void updateUser(int userId, String name, String email) {
		System.out.println("Updating user with ID: " + userId + ", New Name: " + name + ", New Email: " + email);
		// Simulate some logic (e.g., updating user in the database)
	}

	// Method to delete a user
	public void deleteUser(int userId) {
		System.out.println("Deleting user with ID: " + userId);
		// Simulate some logic (e.g., deleting user from the database)
	}
}
