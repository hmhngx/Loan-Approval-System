package com.example.demo.repository;

import com.example.demo.model.WorkCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Calendar;

@Repository
public interface WorkCalendarRepository extends JpaRepository<WorkCalendar, Date> {

    Optional<WorkCalendar> findByCalendarDate(Long cdDate);

    // Check if a date is a holiday
    default boolean isHoliday(Long date) {
        return findByCalendarDate(date)
                .map(WorkCalendar::getIsPublicHoliday)
                .map("Y"::equalsIgnoreCase)
                .orElse(false);
    }

    // Check if a date is a business day
    default boolean isBusinessDay(Long date) {
        return findByCalendarDate(date)
                .map(WorkCalendar::getIsBusinessDay)
                .map("Y"::equalsIgnoreCase)
                .orElse(false);
    }

    // Find the next business day
    default Date getNextBusinessDay(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        do {
            //calendar.add(Calendar.DATE, 1); // Move to the next day
            Date nextDate = calendar.getTime();
            String pattern = "ddMMyy";
            DateFormat df = new SimpleDateFormat(pattern);
            String cdDate=df.format(nextDate);

            Long numberDate=Long.parseLong(cdDate);
            if (isBusinessDay(numberDate)) {
                return nextDate;
            }
        } while (true); // Loop until a valid business day is found
    }
}
