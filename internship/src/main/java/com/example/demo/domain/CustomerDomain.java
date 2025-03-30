package com.example.demo.domain;

import com.example.demo.dispatcher.CustomerDispatcher;
import com.example.demo.model.Customer;
import com.example.demo.model.MaritalStatus;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerDomain implements CustomerDispatcher {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public Customer saveOrUpdate(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findByMaritalStatus(MaritalStatus maritalStatus) {
        return customerRepository.findByMaritalStatus(maritalStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findByDateOfBirth(Date dateOfBirth) {
        return customerRepository.findByDateOfBirth(dateOfBirth);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findByLastName(String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAllWithFetch();
    }
}
