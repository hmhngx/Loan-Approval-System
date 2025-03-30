package com.example.demo.model.request;

import com.example.demo.model.detail.TransactionDetail;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Data
public class TransactionRequest {
    private Long tellerId;
    private Long customerId;
    private Long branchId;

    private String accountNumber;
    private BigDecimal amount;
    private String startDate; // format: dd/MM/yyyy HH:mm:ss
    private int termMonths;
    //list trans detail;
    private List<TransactionDetail> listDetail;

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getTellerId() {
        return tellerId;
    }

    public void setTellerId(Long tellerId) {
        this.tellerId = tellerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setTermMonths() {
        this.termMonths = termMonths;
    }
}