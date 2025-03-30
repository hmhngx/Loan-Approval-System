package com.example.demo.repository;

import com.example.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT t FROM Transaction t WHERE t.customerId = :customerId")
    List<Transaction> findByCustomerId(Long customerId);

    @Transactional(readOnly = true)
    @Query("SELECT t FROM Transaction t WHERE t.accountNumber = :accountNumber")
    List<Transaction> findByAccountNumber(String accountNumber);

    @Transactional(readOnly = true)
    @Query("SELECT t FROM Transaction t WHERE t.branchId = :branchId")
    List<Transaction> findByBranchId(Long branchId);

    @Transactional(readOnly = true)
    Optional<Transaction> findById(Long id);

    @Transactional(readOnly = true)
    @Query("SELECT t FROM Transaction t WHERE t.status = :status")
    List<Transaction> findByStatus(String status);

}
