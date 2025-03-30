package com.example.demo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Teller {
	private long idNumber;
	private String name;
	private	BigDecimal amount;
	private String branch;
	private String password;


	public Teller (long idNumber, String name, BigDecimal amount, String branch, String password) {
		this.idNumber = idNumber;
		this.name = name;
		this.amount = amount;
		this.branch = branch;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(long idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "Teller [id:" + idNumber + ", username:" + name + ", total amount:" + amount
				+ ", branch:" + branch;
	}

	// Methods:

	public boolean verifyLogicViolations(Date loanDate, int termDays, double balance, boolean isMinorTerm, List<Date> holidays) {
		// Logic to verify if there are any logic violations
		// Consider holidays, number of days of term, limit balance, minor term
		// This is a placeholder for logic
		System.out.println("Verifying logic violations...");
		return true; // Return true if no violations, otherwise false
	}

	public boolean checkLoanAccountExists(int accountId) {
		// Logic to check if the entered Loan Account exists
		System.out.println("Checking if loan account exists for account ID: " + accountId);
		return true; // Return true if exists, otherwise false
	}

	public void processLoanRequest(int transactionId, int accountId, double amount, Date loanDate, int termDays, List<Date> holidays) {
		// Divide into small time periods, ignoring holidays
		System.out.println("Processing loan request ID: " + transactionId);

		// Calculate sub-periods up to current period if start date is in past
		// Send confirmation of recorded request to Checker for approval
		sendForApproval(transactionId);
	}

	public void sendForApproval(int transactionId) {
		// Send confirmation of recorded request to Checker for approval
		System.out.println("Sending loan request ID: " + transactionId + " to Checker for approval");
	}

	public void updateAndResubmitLoanRequest(int transactionId) {
		// Update information and resubmit the request to Checker
		System.out.println("Updating and resubmitting loan request ID: " + transactionId + " to Checker");
	}

	public void notifyLoanRejected(int transactionId) {
		// Notify that the loan is completely rejected
		System.out.println("Loan request ID: " + transactionId + " has been rejected");
	}

	public void makeFullRepayment(int accountId, double amount, Date repaymentDate) {
		// User enters account information, amount to be paid, default payment date is current date
		// The system automatically finds the loan and checks details
		System.out.println("Making full repayment for account ID: " + accountId);

		// Calculate interest, penalties, and total fees if applicable
		// Send for approval
		sendRepaymentForApproval(accountId, amount, repaymentDate);
	}

	public void sendRepaymentForApproval(int accountId, double amount, Date repaymentDate) {
		// Send repayment request to Checker for approval
		System.out.println("Sending repayment for account ID: " + accountId + " to Checker for approval");
	}

}
