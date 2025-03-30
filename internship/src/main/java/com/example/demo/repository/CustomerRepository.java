package com.example.demo.repository;

import com.example.demo.model.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.example.demo.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Transactional(readOnly = true)
    Optional<Customer> findById(@Nullable Long id);

    @Transactional(readOnly = true)
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.address")
    List<Customer> findAllWithFetch();

    @Transactional(readOnly = true)
    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Optional<Customer> findByEmail(String email);

    @Transactional(readOnly = true)
    @Query("SELECT c FROM Customer c WHERE c.username = :username")
    Optional<Customer> findByUsername(String username);

    @Transactional(readOnly = true)
    @Query("SELECT c FROM Customer c WHERE c.maritalStatus = :maritalStatus")
    List<Customer> findByMaritalStatus(MaritalStatus maritalStatus);

    @Transactional(readOnly = true)
    @Query("SELECT c FROM Customer c WHERE c.dateOfBirth = :dateOfBirth")
    List<Customer> findByDateOfBirth(Date dateOfBirth);

    @Transactional(readOnly = true)
    @Query("SELECT c FROM Customer c WHERE c.lastName = :lastName")
    List<Customer> findByLastName(String lastName);
}
