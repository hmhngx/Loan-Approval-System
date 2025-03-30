package com.example.demo;

import java.util.Date;

public class Admin {
	private String name;
	private int id;

	public Admin(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// Methods

	public void addUser(int userId, String userName, String role) {
		// Logic to add a new user
		System.out.println("Adding new user ID: " + userId + ", Name: " + userName + ", Role: " + role);
	}

	public void removeUser(int userId) {
		// Logic to remove a user
		System.out.println("Removing user ID: " + userId);
	}

	public void generateTransactionReport(Date startDate, Date endDate) {
		// Logic to generate a transaction report
		System.out.println("Generating transaction report from " + startDate + " to " + endDate);
	}

	public void autoRunDailyProcessing() {
		// Daily system processing
		System.out.println("Running daily system processing...");
		// Get items with new adjustment period
		// Calculate costs of sub-periods
		// Update loan information
	}

	public void processMaturityLoans(Date currentDate) {
		// For maturity (current date is maturity date)
		System.out.println("Processing maturity loans for date: " + currentDate);
		// Calculate costs of the recently ended period
		// Update loan information for maturity
	}
}
