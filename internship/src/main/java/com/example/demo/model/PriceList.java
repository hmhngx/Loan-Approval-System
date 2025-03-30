package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "PRICE_LIST")
public class PriceList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "TWO_WEEKS", precision = 20, scale = 7)
    private BigDecimal twoWeeks;

    @Column(name = "ONE_MONTH", precision = 20, scale = 7)
    private BigDecimal oneMonth;

    @Column(name = "TWO_MONTHS", precision = 20, scale = 7)
    private BigDecimal twoMonths;

    @Column(name = "THREE_MONTHS", precision = 20, scale = 7)
    private BigDecimal threeMonths;

    @Column(name = "SIX_MONTHS", precision = 20, scale = 7)
    private BigDecimal sixMonths;

    @Column(name = "STATUS")
    private String status;

    // Default constructor (required by JPA)
    public PriceList() {
    }

    // Parameterized constructor
    public PriceList(Long itemId, Date startDate, String itemName, BigDecimal twoWeeks,
                     BigDecimal oneMonth, BigDecimal twoMonths, BigDecimal threeMonths,
                     BigDecimal sixMonths, String status) {
        this.itemId = itemId;
        this.startDate = startDate;
        this.itemName = itemName;
        this.twoWeeks = twoWeeks;
        this.oneMonth = oneMonth;
        this.twoMonths = twoMonths;
        this.threeMonths = threeMonths;
        this.sixMonths = sixMonths;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getTwoWeeks() {
        return twoWeeks;
    }

    public void setTwoWeeks(BigDecimal twoWeeks) {
        this.twoWeeks = twoWeeks;
    }

    public BigDecimal getOneMonth() {
        return oneMonth;
    }

    public void setOneMonth(BigDecimal oneMonth) {
        this.oneMonth = oneMonth;
    }

    public BigDecimal getTwoMonths() {
        return twoMonths;
    }

    public void setTwoMonths(BigDecimal twoMonths) {
        this.twoMonths = twoMonths;
    }

    public BigDecimal getThreeMonths() {
        return threeMonths;
    }

    public void setThreeMonths(BigDecimal threeMonths) {
        this.threeMonths = threeMonths;
    }

    public BigDecimal getSixMonths() {
        return sixMonths;
    }

    public void setSixMonths(BigDecimal sixMonths) {
        this.sixMonths = sixMonths;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Equals and HashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceList priceList = (PriceList) o;
        return Objects.equals(id, priceList.id) &&
                Objects.equals(itemId, priceList.itemId) &&
                Objects.equals(startDate, priceList.startDate) &&
                Objects.equals(itemName, priceList.itemName) &&
                Objects.equals(twoWeeks, priceList.twoWeeks) &&
                Objects.equals(oneMonth, priceList.oneMonth) &&
                Objects.equals(twoMonths, priceList.twoMonths) &&
                Objects.equals(threeMonths, priceList.threeMonths) &&
                Objects.equals(sixMonths, priceList.sixMonths) &&
                Objects.equals(status, priceList.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, startDate, itemName, twoWeeks, oneMonth, twoMonths, threeMonths, sixMonths, status);
    }

    // toString method
    @Override
    public String toString() {
        return "PriceList{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", startDate=" + startDate +
                ", itemName='" + itemName + '\'' +
                ", twoWeeks=" + twoWeeks +
                ", oneMonth=" + oneMonth +
                ", twoMonths=" + twoMonths +
                ", threeMonths=" + threeMonths +
                ", sixMonths=" + sixMonths +
                ", status='" + status + '\'' +
                '}';
    }
}
