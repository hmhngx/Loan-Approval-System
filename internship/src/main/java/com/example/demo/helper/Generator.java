package com.example.demo.helper;

import com.example.demo.AppProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Generator {

    @Autowired
    private AppProperties appProperties;

    public String newCustomerId() {
        return RandomStringUtils.randomAlphanumeric(appProperties.getLength().getCustomerId());
    }

    public String newStaffId() {
        return RandomStringUtils.randomAlphanumeric(appProperties.getLength().getStaffId());
    }

    public String newLoanFileId() {
        return RandomStringUtils.randomAlphanumeric(appProperties.getLength().getLoanFileId());
    }

    public String newCustomerPin() {
        return newPassword(appProperties.getLength().getCustomerPin());
    }

    public String newUsername() {
        return RandomStringUtils.randomAlphabetic(appProperties.getLength().getCustomerUsername());
    }

    public String newPassword(int length) {
        final String tmp = "qwertzuiopasdfghjklyxcvbnmQWERTZUIOPASDFGHJKLYXCVBNM1234567890!$%&/()=?#*.@";
        return RandomStringUtils.random(length, tmp);
    }

    public String newRandomString(int length) {
        final String tmp = "qwertzuiopasdfghjklyxcvbnmQWERTZUIOPASDFGHJKLYXCVBNM1234567890!$%&/()=?#+~*-.,<>@";
        return RandomStringUtils.random(length, tmp);
    }
}