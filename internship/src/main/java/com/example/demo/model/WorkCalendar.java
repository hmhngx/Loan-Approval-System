package com.example.demo.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "SSCLDR")
public class WorkCalendar {

    @Id
    @Column(name = "CDATE", nullable = false)
    private Long calendarDate;

    @Column(name = "CWDAY", length = 9)
    private String calendarWeekDay;

    @Column(name = "CMONTH", length = 9)
    private String calendarMonth;

    @Column(name = "PRODAY", length = 2)
    private String isPublicHoliday;

    @Column(name = "BUSDAY", length = 2)
    private String isBusinessDay;

    @Column(name = "ENDCKD")
    private Long endCheckId;

    // Getters and Setters

    public Long getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(Long calendarDate) {
        this.calendarDate = calendarDate;
    }

    public String getCalendarWeekDay() {
        return calendarWeekDay;
    }

    public void setCalendarWeekDay(String calendarWeekDay) {
        this.calendarWeekDay = calendarWeekDay;
    }

    public String getCalendarMonth() {
        return calendarMonth;
    }

    public void setCalendarMonth(String calendarMonth) {
        this.calendarMonth = calendarMonth;
    }

    public String getIsPublicHoliday() {
        return isPublicHoliday;
    }

    public void setIsPublicHoliday(String isPublicHoliday) {
        this.isPublicHoliday = isPublicHoliday;
    }

    public String getIsBusinessDay() {
        return isBusinessDay;
    }

    public void setIsBusinessDay(String isBusinessDay) {
        this.isBusinessDay = isBusinessDay;
    }

    public Long getEndCheckId() {
        return endCheckId;
    }

    public void setEndCheckId(Long endCheckId) {
        this.endCheckId = endCheckId;
    }
}
