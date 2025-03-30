package com.example.demo.model.detail;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "TRANSACTION_DETAIL")
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "INTEREST", precision = 19, scale = 2, nullable = false)
    private BigDecimal interest;

    @Column(name = "STATUS", length = 20, nullable = false)
    private String status;

    @Column(name = "TRANSACTION_ID", nullable = false)
    private Long transactionId;

    @Column(name = "TOTAL_CUSTOMER_INTEREST")
    private BigDecimal totalCustomerInterest;

    // Getters and Setters
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalCustomerInterest() {
        return totalCustomerInterest;
    }

    public void setTotalCustomerInterest(BigDecimal totalCustomerInterest) {
        this.totalCustomerInterest = totalCustomerInterest;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTransaction(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransaction() {
        return transactionId;
    }
}
