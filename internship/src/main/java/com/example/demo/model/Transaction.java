package com.example.demo.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANS_SEQ")
    @SequenceGenerator(name = "TRANS_SEQ", sequenceName = "TRANS_SEQ", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID")
    private long id;

    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "TELLER_ID")
    private Long tellerId;

    @Column(name = "CHECKER_ID")
    private Long checkerId;

    @Column(name = "ACCOUNT_NUMBER", length = 50)
    private String accountNumber;

    @Column(name = "CBAL", precision = 20, scale = 2)
    private BigDecimal cbal;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "OPEN_DATE")
    private Date openDate;

    @Column(name = "MATURITY_DATE")
    private Date maturityDate;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "REAL_ESTATE_COST", precision = 20, scale = 7)
    private BigDecimal realEstateCost;

    @Column(name = "OPTION_ID")
    private Long optionId;

    @Column(name = "E0", precision = 20, scale = 2)
    private BigDecimal e0;

    @Column(name = "BASE_COST", precision = 20, scale = 7)
    private BigDecimal baseCost;

    @Column(name = "RATE", precision = 20, scale = 7)
    private BigDecimal rate;

    @Column(name = "MAX_COST", precision = 20, scale = 7)
    private BigDecimal maxCost;

    @Column(name = "MARGIN_OPTION", precision = 20, scale = 7)
    private BigDecimal marginOption;

    @Column(name = "MARGIN", precision = 20, scale = 7)
    private BigDecimal margin;

    @Column(name = "PREPAYMENT_BALANCE", precision = 20, scale = 7)
    private BigDecimal prepaymentBalance;

    @Column(name = "EARLY_REPAYMENT_DATE")
    private Date earlyRepaymentDate;

    @Column(name = "LAST_REPAYMENT_DATE")
    private Date lastRepaymentDate;

    @Column(name = "APPROVAL_DATE")
    private Date approvalDate;

    @Column(name = "PREPAYMENT_COST", precision = 20, scale = 7)
    private BigDecimal prepaymentCost;

    @Column(name = "ET", precision = 20, scale = 2)
    private BigDecimal et;

    @Column(name = "ADDITIONAL_INTEREST", precision = 20, scale = 7)
    private BigDecimal additionalInterest;

    @Column(name = "ADDITIONAL_COST", precision = 20, scale = 7)
    private BigDecimal additionalCost;

    @Column(name = "FI_COST", precision = 20, scale = 7)
    private BigDecimal fiCost;

    @Column(name = "TOTAL_CUSTOMER_INTEREST", precision = 20, scale = 7)
    private BigDecimal totalCustomerInterest;

    @Column(name = "WORKORDERID_TTTH", precision = 20, scale = 7)
    private BigDecimal workOrderIdTtth;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CURRENCY")
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Long checkerId) {
        this.checkerId = checkerId;
    }

    public Long getTellerId() {
        return tellerId;
    }

    public void setTellerId(Long tellerId) {
        this.tellerId = tellerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getCbal() {
        return cbal;
    }

    public void setCbal(BigDecimal cbal) {
        this.cbal = cbal;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getRealEstateCost() {
        return realEstateCost;
    }

    public void setRealEstateCost(BigDecimal realEstateCost) {
        this.realEstateCost = realEstateCost;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public BigDecimal getE0() {
        return e0;
    }

    public void setE0(BigDecimal e0) {
        this.e0 = e0;
    }

    public BigDecimal getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(BigDecimal baseCost) {
        this.baseCost = baseCost;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(BigDecimal maxCost) {
        this.maxCost = maxCost;
    }

    public BigDecimal getMarginOption() {
        return marginOption;
    }

    public void setMarginOption(BigDecimal marginOption) {
        this.marginOption = marginOption;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }

    public BigDecimal getPrepaymentBalance() {
        return prepaymentBalance;
    }

    public void setPrepaymentBalance(BigDecimal prepaymentBalance) {
        this.prepaymentBalance = prepaymentBalance;
    }

    public Date getEarlyRepaymentDate() {
        return earlyRepaymentDate;
    }

    public void setEarlyRepaymentDate(Date earlyRepaymentDate) {
        this.earlyRepaymentDate = earlyRepaymentDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Date getLastRepaymentDate() {
        return lastRepaymentDate;
    }

    public void setLastRepaymentDate(Date lastRepaymentDate) {
        this.lastRepaymentDate = lastRepaymentDate;
    }

    public BigDecimal getPrepaymentCost() {
        return prepaymentCost;
    }

    public void setPrepaymentCost(BigDecimal prepaymentCost) {
        this.prepaymentCost = prepaymentCost;
    }

    public BigDecimal getEt() {
        return et;
    }

    public void setEt(BigDecimal et) {
        this.et = et;
    }

    public BigDecimal getAdditionalInterest() {
        return additionalInterest;
    }

    public void setAdditionalInterest(BigDecimal additionalInterest) {
        this.additionalInterest = additionalInterest;
    }

    public BigDecimal getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(BigDecimal additionalCost) {
        this.additionalCost = additionalCost;
    }

    public BigDecimal getFiCost() {
        return fiCost;
    }

    public void setFiCost(BigDecimal fiCost) {
        this.fiCost = fiCost;
    }

    public BigDecimal getTotalCustomerInterest() {
        return totalCustomerInterest;
    }

    public void setTotalCustomerInterest(BigDecimal totalCustomerInterest) {
        this.totalCustomerInterest = totalCustomerInterest;
    }

    public BigDecimal getWorkOrderIdTtth() {
        return workOrderIdTtth;
    }

    public void setWorkOrderIdTtth(BigDecimal workOrderIdTtth) {
        this.workOrderIdTtth = workOrderIdTtth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(branchId, that.branchId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(cbal, that.cbal) &&
                Objects.equals(openDate, that.openDate) &&
                Objects.equals(maturityDate, that.maturityDate) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(realEstateCost, that.realEstateCost) &&
                Objects.equals(optionId, that.optionId) &&
                Objects.equals(e0, that.e0) &&
                Objects.equals(baseCost, that.baseCost) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(maxCost, that.maxCost) &&
                Objects.equals(marginOption, that.marginOption) &&
                Objects.equals(margin, that.margin) &&
                Objects.equals(prepaymentBalance, that.prepaymentBalance) &&
                Objects.equals(earlyRepaymentDate, that.earlyRepaymentDate) &&
                Objects.equals(prepaymentCost, that.prepaymentCost) &&
                Objects.equals(et, that.et) &&
                Objects.equals(additionalInterest, that.additionalInterest) &&
                Objects.equals(additionalCost, that.additionalCost) &&
                Objects.equals(fiCost, that.fiCost) &&
                Objects.equals(totalCustomerInterest, that.totalCustomerInterest) &&
                Objects.equals(workOrderIdTtth, that.workOrderIdTtth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branchId, customerId, accountNumber, cbal, openDate, maturityDate, startDate, endDate, realEstateCost, optionId, e0, baseCost, rate, maxCost, marginOption, margin, prepaymentBalance, earlyRepaymentDate, prepaymentCost, et, additionalInterest, additionalCost, fiCost, totalCustomerInterest, workOrderIdTtth);
    }

}
