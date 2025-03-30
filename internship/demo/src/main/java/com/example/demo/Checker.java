package com.example.demo;

import java.util.Date;

public class Checker {
	private String name;
	private int id;

	public Checker(String name, int id) {
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

	// Methods:

	public void reviewTransaction(int transactionId) {
		// Logic to review a transaction
		System.out.println("Reviewing transaction ID: " + transactionId);
	}

	public void approveTransaction(int transactionId) {
		// Logic to approve a transaction
		System.out.println("Approving transaction ID: " + transactionId);
		// If approved, officially record the loan
		recordLoan(transactionId);
	}

	public void rejectTransaction(int transactionId) {
		// Logic to reject a transaction
		System.out.println("Rejecting transaction ID: " + transactionId);
		// Notify Teller that the loan is not confirmed
	}

	public void recordLoan(int transactionId) {
		// Officially record the loan
		System.out.println("Recording loan for transaction ID: " + transactionId);
	}

	public void approveRepayment(int accountId, double amount, Date repaymentDate) {
		// Approve the repayment, record details
		System.out.println("Approving repayment for account ID: " + accountId);
		recordRepayment(accountId, amount, repaymentDate);
	}

	public void rejectRepayment(int accountId) {
		// Reject the repayment request completely
		System.out.println("Rejecting repayment for account ID: " + accountId);
	}

	private void recordRepayment(int accountId, double amount, Date repaymentDate) {
		// Record repayment details
		System.out.println("Recording repayment for account ID: " + accountId);
	}
}

