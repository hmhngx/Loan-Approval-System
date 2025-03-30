package com.example.demo.dispatcher;

import com.example.demo.model.Customer;
import com.example.demo.model.MaritalStatus;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CustomerDispatcher {

    Customer saveOrUpdate(Customer customer);

    Optional<Customer> findById(Long id);

    void deleteById(Long id);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByUsername(String username);

    List<Customer> findByMaritalStatus(MaritalStatus maritalStatus);

    List<Customer> findByDateOfBirth(Date dateOfBirth);

    List<Customer> findByLastName(String lastName);

    List<Customer> findAll();
}
