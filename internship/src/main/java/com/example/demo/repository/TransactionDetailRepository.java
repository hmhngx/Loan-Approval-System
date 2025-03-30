package com.example.demo.repository;

import com.example.demo.model.detail.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {

    // Find all TransactionDetails by Transaction ID
    List<TransactionDetail> findByTransactionId(Long transactionId);

    // Find all TransactionDetails by status
    List<TransactionDetail> findByStatus(String status);

    // Custom query to find TransactionDetails within a specific date range using JPQL
    @Query("SELECT td FROM TransactionDetail td WHERE td.startDate BETWEEN :startDate AND :endDate")
    List<TransactionDetail> findByStartDateBetween(Date startDate, Date endDate);

    // Custom query to find TransactionDetails by Transaction ID and Status using JPQL
    @Query("SELECT td FROM TransactionDetail td WHERE td.transactionId = :transactionId AND td.status = :status")
    List<TransactionDetail> findByTransactionIdAndStatus(Long transactionId, String status);

}
