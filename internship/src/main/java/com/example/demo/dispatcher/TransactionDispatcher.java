package com.example.demo.dispatcher;

import com.example.demo.model.Transaction;
import com.example.demo.model.request.TransactionRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionDispatcher {

    // Transaction Repository Methods
    Optional<Transaction> findById(Long id);

    List<Transaction> findByCustomerId(Long customerId);

    List<Transaction> findByAccountNumber(String accountNumber);

    List<Transaction> findByBranchId(Long branchId);

    Transaction saveTransaction(Transaction transaction);

    void deleteById(Long id);

    // Loan Processing Methods
    Transaction initiateLoan(TransactionRequest request) throws Exception;

    Transaction approveLoan(Long checkerId, Long transactionId) throws Exception;

    void rejectLoan(Long checkerId, Long transactionId, String reason) throws Exception;

    @Transactional
    void processRepayment(Long transactionId, BigDecimal paymentAmount, Date paymentDate) throws Exception;

    @Transactional
    BigDecimal processDailyUpdates(Long transId);

}
