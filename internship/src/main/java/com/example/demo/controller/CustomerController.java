package com.example.demo.controller;

import com.example.demo.dispatcher.CustomerDispatcher;
import com.example.demo.model.Customer;
import com.example.demo.model.MaritalStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerDispatcher customerDispatcher;

    @Autowired
    public CustomerController(CustomerDispatcher customerDispatcher) {
        this.customerDispatcher = customerDispatcher;
    }

    @PostMapping
    public ResponseEntity<Customer> createOrUpdateCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerDispatcher.saveOrUpdate(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerDispatcher.findById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerDispatcher.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        Optional<Customer> customer = customerDispatcher.findByEmail(email);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username")
    public ResponseEntity<Customer> getCustomerByUsername(@RequestParam String username) {
        Optional<Customer> customer = customerDispatcher.findByUsername(username);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/maritalStatus")
    public ResponseEntity<List<Customer>> getCustomersByMaritalStatus(@RequestParam MaritalStatus maritalStatus) {
        List<Customer> customers = customerDispatcher.findByMaritalStatus(maritalStatus);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/dateOfBirth")
    public ResponseEntity<List<Customer>> getCustomersByDateOfBirth(@RequestParam Date dateOfBirth) {
        List<Customer> customers = customerDispatcher.findByDateOfBirth(dateOfBirth);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/lastName")
    public ResponseEntity<List<Customer>> getCustomersByLastName(@RequestParam String lastName) {
        List<Customer> customers = customerDispatcher.findByLastName(lastName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerDispatcher.findAll();
        return ResponseEntity.ok(customers);
    }
}
