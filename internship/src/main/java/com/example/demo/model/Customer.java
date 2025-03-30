package com.example.demo.model;

import com.example.demo.adapter.jpa.MaritalStatusConverter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "BRANCH_ID")
	private Long branchId;

	@Column(name = "DATE_OF_BIRTH")
	@Basic
	private Date dateOfBirth;

	@Column(unique = true)
	private String username;

	@Column
	private String email;

	@Basic
	private String firstName;

	@Basic
	private Double income;

	@Basic
	private String lastName;

	@Column
	@Convert(converter = MaritalStatusConverter.class)
	private MaritalStatus maritalStatus;

	@Basic
	private String mobilePhone;

	@Basic
	private Integer numberOfChildren;

	@Column(name = "PERSONAL_ID")
	@Basic
	private Long personalId;

	@Basic
	private String password;

	@Basic
	private String title;

	@Embedded
	private Address address;

	public Customer() {
	}

	public Long getId() {
		return id;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public Long getPersonalId() {
		return personalId;
	}

	public void setPersonalId(Long personalId) {
		this.personalId = personalId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", personalId='" + personalId + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", income=" + income +
				", maritalStatus=" + maritalStatus +
				", numberOfChildren=" + numberOfChildren +
				", mobilePhone='" + mobilePhone + '\'' +
				", title='" + title + '\'' +
				", address=" + address +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return id.equals(customer.id) &&
				Objects.equals(dateOfBirth, customer.dateOfBirth) &&
				Objects.equals(username, customer.username) &&
				Objects.equals(email, customer.email) &&
				Objects.equals(personalId, customer.personalId) &&
				Objects.equals(branchId, customer.branchId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, dateOfBirth, username, email, personalId);
	}
}