package com.example.demo.controller;

import com.example.demo.dispatcher.TransactionDispatcher;
import com.example.demo.model.Transaction;
import com.example.demo.model.request.RepaymentRequest;
import com.example.demo.model.request.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {

    private final TransactionDispatcher transactionDispatcher;

    @Autowired
    public TransactionController(TransactionDispatcher transactionDispatcher) {
        this.transactionDispatcher = transactionDispatcher;
    }

    // Transaction Repository Methods

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionDispatcher.findById(id);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Transaction>> findByCustomerId(@PathVariable Long customerId) {
        List<Transaction> transactions = transactionDispatcher.findByCustomerId(customerId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Transaction>> findByBranchId(@PathVariable Long branchId) {
        List<Transaction> transactions = transactionDispatcher.findByBranchId(branchId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<List<Transaction>> findByAccountNumber(@PathVariable String accountNumber) {
        List<Transaction> transactions = transactionDispatcher.findByAccountNumber(accountNumber);
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @Valid @RequestBody Transaction transaction) {
        Optional<Transaction> existingTransaction = transactionDispatcher.findById(id);
        if (existingTransaction.isPresent()) {
            transaction.setId(id);  // Ensure the ID is set to the existing one
            Transaction updatedTransaction = transactionDispatcher.saveTransaction(transaction);
            return ResponseEntity.ok(updatedTransaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionDispatcher.findById(id);
        if (transaction.isPresent()) {
            transactionDispatcher.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Loan Processing Methods
    @PostMapping("/initiate-loan")
    public ResponseEntity<Transaction> initiateLoan(@Valid @RequestBody TransactionRequest request) {
        try {
            log.info("Start create trans...");

            Transaction transaction = transactionDispatcher.initiateLoan(request);
            log.info("end create trans..." + transaction);

            return ResponseEntity.ok(transaction);

        } catch (ParseException e) {
            log.error("ParseException trans..." + e.toString());
            return ResponseEntity.badRequest().build(); // Invalid date format
        } catch (Exception e) {
            log.error("Exception trans..." + e.toString());
            return ResponseEntity.status(500).build(); // Handle other exceptions
        }
    }

    @PostMapping("/approve-loan/{checkerId}/{transactionId}")
    public ResponseEntity<Transaction> approveLoan(@PathVariable Long checkerId, @PathVariable Long transactionId) {
        try {
            log.info("approveLoan by " + checkerId + " id loan: " + transactionId);
            Transaction transaction = transactionDispatcher.approveLoan(checkerId, transactionId);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            log.error("Error approving loan: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/reject-loan/{checkerId}/{transactionId}")
    public ResponseEntity<Void> rejectLoan(@PathVariable Long checkerId, @PathVariable Long transactionId, @RequestParam String reason) {
        try {
            transactionDispatcher.rejectLoan(checkerId, transactionId, reason);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/repayment")
    public ResponseEntity<Void> processRepayment(@Valid @RequestBody RepaymentRequest request) {
        try {
            Date paymentDate = parseDate(request.getPaymentDate(), "dd/MM/yyyy");
            transactionDispatcher.processRepayment(
                    request.getTransactionId(),
                    request.getPaymentAmount(),
                    paymentDate
            );
            return ResponseEntity.ok().build();

        } catch (ParseException e) {
            log.error("Error parsing payment date: " + e.getMessage());
            return ResponseEntity.badRequest().build(); // Invalid date format
        } catch (Exception e) {
            log.error("Error processing repayment: " + e.getMessage());
            return ResponseEntity.status(500).build(); // Handle other exceptions
        }
    }

    @GetMapping("/calculate-interest/{id}")
    public ResponseEntity<BigDecimal> processDailyUpdates(@PathVariable Long id) {
        BigDecimal res= transactionDispatcher.processDailyUpdates(id);
        return ResponseEntity.ok(res);
    }

    // Utility method to parse date
    private Date parseDate(String dateStr, String pattern) throws ParseException {
        if (dateStr != null && !dateStr.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.parse(dateStr);
        }
        return new Date();  // Default to the current date if not provided
    }
}

