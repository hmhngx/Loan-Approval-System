package com.example.demo.domain;

import com.example.demo.dispatcher.TransactionDispatcher;
import com.example.demo.model.*;
import com.example.demo.model.detail.TransactionDetail;
import com.example.demo.model.request.TransactionRequest;
import com.example.demo.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class TransactionDomain implements TransactionDispatcher {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private WorkCalendarRepository workCalendarRepository;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Override
    @Transactional
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Transaction> findByCustomerId(Long customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional
    public List<Transaction> findByAccountNumber(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    @Override
    @Transactional
    public List<Transaction> findByBranchId(Long branchId) {
        return transactionRepository.findByBranchId(branchId);
    }

    @Override
    @Transactional
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Transaction initiateLoan(TransactionRequest request) throws Exception {
        // Step 1: Validate the teller's role
        boolean isTeller = validateStaffRole(request.getTellerId(), "TELLER");
        if (!isTeller) {
            throw new Exception("Teller not found or does not have the correct role.");
        }

        // Step 2: Fetch and validate customer details
        log.info("customerId..." + request.getCustomerId());
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new Exception("Customer not found"));
        validateCustomerEligibility(customer);
        log.info("customer" + customer);

        // Step 3: Validate loan start date and maturity date
        Date startDate = parseDate(request.getStartDate(), "dd/MM/yyyy");
        validateLoanDates(startDate, request.getTermMonths());

        // Step 4: Fetch applicable price list
        PriceList priceList = fetchApplicablePriceList(request.getStartDate());

        // Step 5: Calculate maturity date
        Date maturityDate = calculateMaturityDate(startDate, request.getTermMonths());
        log.info("maturityDate" + maturityDate);

        // Step 6: Validate transaction details
        for (TransactionDetail detail : request.getListDetail()) {
            // Ensure detail startDate is within the loan period
            if (detail.getStartDate().before(startDate)) {
                throw new Exception("Transaction detail start date must be on or after the loan start date.");
            }

            // Ensure detail endDate is within the loan period or is null (which means it lasts until maturity)
            if (detail.getEndDate() != null && detail.getEndDate().after(maturityDate)) {
                throw new Exception("Transaction detail end date must be on or before the loan maturity date.");
            }

            // Calculate the term for this transaction detail
            int detailTermMonths = calculateTermMonths(detail.getStartDate(), detail.getEndDate(), maturityDate);
            log.info("detailTermMonths" + detailTermMonths);

            // Determine the expected interest rate based on the price list and term
            BigDecimal expectedRate = determineInterestRate(priceList, detailTermMonths);
            log.info("expectedRate" + expectedRate);

            // Compare the expected interest rate with the rate in the transaction detail
            if (detail.getInterest().compareTo(expectedRate) != 0) {
                throw new Exception("Transaction detail interest rate does not match the expected rate for the given period.");
            }
        }

        // Step 7: Create the transaction
        Transaction transaction = createTransaction(request.getCustomerId(), request.getBranchId(), request.getTellerId(), request.getAccountNumber(),
                request.getAmount(), startDate, maturityDate, priceList, request.getTermMonths());

        // Step 8: Save the transaction
        log.info("save trans");
        Transaction result = saveTransaction(transaction);
        log.info("end save trans");

        // Step 9: Save transaction details if the transaction was successfully saved
        if (result != null) {
            for (TransactionDetail detail : request.getListDetail()) {
                detail.setTransactionId(result.getId());
            }
            transactionDetailRepository.saveAll(request.getListDetail());
        }

        return result;
    }

    @Override
    @Transactional
    public Transaction approveLoan(Long checkerId, Long transactionId) throws Exception {
        // Step 1: Validate the checker's role
        boolean checker = validateStaffRole(checkerId, "CHECKER");

        // Step 2: Fetch the transaction
        Transaction transaction = findById(transactionId)
                .orElseThrow(() -> new Exception("Transaction not found"));

        // Step 3: Ensure the approval date is a valid business day
        //Date approvalDate = adjustToNextBusinessDay(new Date());

        // Step 4: Approve the transaction
        transaction.setStatus("APPROVED");
        transaction.setCheckerId(checkerId);
        transaction.setApprovalDate(new Date());

        // Step 5: Save and return the approved transaction
        return saveTransaction(transaction);
    }

    @Override
    @Transactional
    public void rejectLoan(Long checkerId, Long transactionId, String reason) throws Exception {
        // Step 1: Validate the checker's role
        boolean checker = validateStaffRole(checkerId, "CHECKER");

        // Step 2: Fetch the transaction
        Transaction transaction = findById(transactionId)
                .orElseThrow(() -> new Exception("Transaction not found"));

        // Step 3: Reject the transaction
        transaction.setStatus("REJECTED");
        transaction.setCheckerId(checkerId);
        transaction.setReason(reason);

        // Step 4: Save the rejected transaction
        saveTransaction(transaction);
    }

    @Override
    @Transactional
    public void processRepayment(Long transactionId, BigDecimal paymentAmount, Date paymentDate) throws Exception {
        // Step 1: Fetch the transaction
        Transaction transaction = findById(transactionId)
                .orElseThrow(() -> new Exception("Transaction not found"));

        // Step 2: Validate that the loan has not yet matured
        if (paymentDate.after(transaction.getMaturityDate())) {
            throw new Exception("Repayment date cannot be after the maturity date.");
        }

        // Step 3: Check the account number
        if (!transaction.getAccountNumber().equals(transaction.getAccountNumber())) {
            throw new Exception("Account number mismatch.");
        }

        // Step 4: Validate that the payment amount equals the remaining balance
        if (paymentAmount.compareTo(transaction.getCbal()) != 0) {
            throw new Exception("Payment amount does not match the current balance.");
        }
//        List<TransactionDetail> listDetail=transactionDetailRepository.findByTransactionId(transactionId);
//        BigDecimal totalInter=new BigDecimal(0);
//        for (TransactionDetail detail : listDetail) {
//            if(detail.getTotalCustomerInterest()!=null)
//            {
//                totalInter.add(detail.getTotalCustomerInterest());
//            }
//        }


//        // Step 5: Adjust the payment date to the next business day if necessary
//        Date adjustedPaymentDate = adjustToNextBusinessDay(paymentDate);
//
//        // Step 6: Calculate interest from the last repayment date to the payment date
//        calculateInterestForSubPeriod(transaction, adjustedPaymentDate);
//
//        // Step 7: If applicable, calculate the penalty from the repayment date to the maturity date
//        if (adjustedPaymentDate.before(transaction.getMaturityDate())) {
//            calculatePenalty(transaction, adjustedPaymentDate);
//        }
//
//
//        // Step 8: Calculate the total loan fee (interest + penalty)
//        BigDecimal totalLoanFee = transaction.getTotalCustomerInterest().add(transaction.getAdditionalCost());
//        transaction.setTotalCustomerInterest(totalLoanFee);
//
//        // Step 9: Confirm the payment with the user
//        // (Assume we have a mechanism to confirm with the user, e.g., via a UI interaction)
//        boolean userConfirmed = true; // Placeholder for user confirmation logic
//        if (!userConfirmed) {
//            throw new Exception("User did not confirm the repayment.");
//        }

        // Step 10: If confirmed, update the transaction status and repayment details
        transaction.setCbal(BigDecimal.ZERO); // Set the balance to 0 after full repayment
        transaction.setLastRepaymentDate(new Date());
        transaction.setStatus("PAID_OFF");

//        // Step 11: Push to Checker for approval
//        // (Assume we have a mechanism to handle Checker approval, e.g., via a UI interaction)
//        boolean checkerApproved = true; // Placeholder for checker approval logic
//        if (!checkerApproved) {
//            throw new Exception("Checker rejected the repayment.");
//        }
//
//        // Step 12: Finalize the transaction for maturity if approved
//        finalizeTransactionForMaturity(transaction);

        // Step 13: Save the transaction with updated details
        saveTransaction(transaction);
    }


    @Override
    @Transactional
    public BigDecimal processDailyUpdates(Long transId) {
        Optional<Transaction> transactions = transactionRepository.findById(transId);
        if(transactions.isPresent())
        {
            return calculateFinalInterest(transactions.get());
        }

        return new BigDecimal(0);
    }

    private boolean validateStaffRole(Long staffId, String roleName) {
        Optional<Staff> staffOptional = staffRepository.findById(staffId);

        // If staff not found or role does not match, return false
        if (staffOptional.isEmpty() || staffOptional.get().getRole() == null) {
            return false;
        }

        // Get the role of the staff member
        Role role = staffOptional.get().getRole();

        // Validate the role name
        return roleName.equals(role.getName());
    }

    private int calculateTermMonths(Date startDate, Date endDate, Date maturityDate) {
        Date actualEndDate = (endDate != null) ? endDate : maturityDate;
        long diffInMillies = actualEndDate.getTime() - startDate.getTime();
        long diffInMonths = diffInMillies / (1000L * 60 * 60 * 24 * 30); // Approximate month calculation
        return (int) diffInMonths + 1; // Adding 1 to include the current month
    }

    private void validateCustomerEligibility(Customer customer) throws Exception {
        BigDecimal minimumIncome = BigDecimal.valueOf(10000000); // Define the minimum income threshold

        if (customer.getIncome() == null || BigDecimal.valueOf(customer.getIncome()).compareTo(minimumIncome) < 0) {
            throw new Exception("Customer does not meet the income eligibility requirements.");
        }
    }

    private PriceList fetchApplicablePriceList(String startDate) throws Exception {
        List<PriceList> priceLists = priceListRepository.findByStartDate(startDate);

        if (priceLists.isEmpty()) {
            throw new Exception("Price list not found for the given start date.");
        }

        // Assuming you need the first PriceList or implement your own selection logic
        return priceLists.get(0);
    }

    private Date calculateMaturityDate(Date startDate, int termMonths) {
        log.info("calculateMaturityDate" + termMonths);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, termMonths);
        return calendar.getTime();
    }

    private Transaction createTransaction(Long customerId, Long branchId, Long tellerId, String accountNumber, BigDecimal amount, Date startDate, Date maturityDate, PriceList priceList, int termMonths) {
        Transaction transaction = new Transaction();

        transaction.setCustomerId(customerId);
        transaction.setBranchId(branchId);
        transaction.setAccountNumber(accountNumber);
        transaction.setCbal(amount);
        transaction.setCurrency("VND"); // Ensure currency is set to VND
        transaction.setOpenDate(startDate);
        transaction.setMaturityDate(maturityDate);
        log.info("amount.." + amount + "..." + transaction.getRate());
        transaction.setRate(determineInterestRate(priceList, termMonths));
        transaction.setBaseCost(calculateBaseCost(amount, transaction.getRate()));
        transaction.setStatus("PENDING");
        transaction.setTellerId(tellerId);
        return transaction;
    }

    private BigDecimal determineInterestRate(PriceList priceList, int termMonths) {
        switch (termMonths) {
            case 1:
                return priceList.getOneMonth();
            case 2:
                return priceList.getTwoMonths();
            case 3:
                return priceList.getThreeMonths();
            case 6:
                return priceList.getSixMonths();
            default:
                throw new IllegalArgumentException("Unsupported loan term.");
        }
    }

    private BigDecimal calculateBaseCost(BigDecimal amount, BigDecimal interestRate) {
        return amount.multiply(interestRate);
    }

    private void validateRepayment(Transaction transaction, BigDecimal paymentAmount, Date paymentDate) throws Exception {
        if (paymentAmount.compareTo(transaction.getCbal()) > 0) {
            throw new Exception("Payment amount exceeds the current balance.");
        }
        if (paymentDate.after(transaction.getMaturityDate())) {
            throw new Exception("Repayment date cannot be after the maturity date.");
        }
    }

    private BigDecimal calculateFinalInterest(Transaction transaction) {
        log.info("transaction id "+transaction.getId());
        List<TransactionDetail> listDetail=transactionDetailRepository.findByTransactionId(transaction.getId());
        BigDecimal totalInter=new BigDecimal(0);
        log.info("list deatil.."+listDetail.size());
        for (TransactionDetail detail : listDetail) {
            if(detail.getTotalCustomerInterest()!=null)
            {
                log.info("list detail.getTotalCustomerInterest().."+detail.getTotalCustomerInterest());
                totalInter=totalInter.add(detail.getTotalCustomerInterest());
            }
        }
        return totalInter;
    }

    private void calculatePenalty(Transaction transaction, Date paymentDate) {
        BigDecimal penaltyRate = BigDecimal.valueOf(0.01);
        int gracePeriodDays = 5;
        BigDecimal maxPenalty = BigDecimal.valueOf(50000);

        long daysLate = calculateDaysBetween(transaction.getMaturityDate(), paymentDate);

        if (daysLate <= gracePeriodDays) {
            log.info("No penalty applied. Payment is within the grace period.");
            return;
        }

        long penaltyDays = daysLate - gracePeriodDays;

        BigDecimal dailyPenalty = transaction.getCbal().multiply(penaltyRate);
        BigDecimal totalPenalty = dailyPenalty.multiply(BigDecimal.valueOf(penaltyDays));

        if (totalPenalty.compareTo(maxPenalty) > 0) {
            totalPenalty = maxPenalty;
        }

        transaction.setAdditionalCost(transaction.getAdditionalCost().add(totalPenalty));
        transaction.setTotalCustomerInterest(transaction.getTotalCustomerInterest().add(totalPenalty));

        log.info("Penalty applied: " + totalPenalty + " for " + penaltyDays + " days late.");
    }

    private void calculateInterestForSubPeriod(Transaction transaction, Date paymentDate) throws Exception {
        // Get the applicable price list for the current sub-period
        PriceList priceList = fetchApplicablePriceList(new SimpleDateFormat("yyyy-MM-dd").format(transaction.getStartDate()));

        // Calculate interest based on the period between the last repayment date and the payment date
        BigDecimal dailyRate = transaction.getRate().divide(BigDecimal.valueOf(365), BigDecimal.ROUND_HALF_UP);
        long daysBetween = calculateDaysBetween(transaction.getLastRepaymentDate(), paymentDate);
        BigDecimal interest = dailyRate.multiply(BigDecimal.valueOf(daysBetween)).multiply(transaction.getCbal());

        // Update transaction with calculated interest
        transaction.setAdditionalInterest(transaction.getAdditionalInterest().add(interest));
        transaction.setTotalCustomerInterest(transaction.getTotalCustomerInterest().add(interest));
    }

    private long calculateDaysBetween(Date startDate, Date endDate) {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return diffInMillies / (1000L * 60 * 60 * 24); // Convert milliseconds to days
    }

    private void finalizeTransactionForMaturity(Transaction transaction) throws Exception {
        // Calculate the cost of the recently ended period (final maturity calculation)
        BigDecimal finalCost = calculateFinalPeriodCost(transaction);
        transaction.setTotalCustomerInterest(transaction.getTotalCustomerInterest().add(finalCost));

        // Update the transaction status to reflect that the loan has matured and been paid off
        transaction.setStatus("MATURED_PAID_OFF");
    }

    private BigDecimal calculateFinalPeriodCost(Transaction transaction) {
        // Assuming this method calculates the final cost for the maturity period
        // Calculation logic would depend on business rules
        BigDecimal dailyRate = transaction.getRate().divide(BigDecimal.valueOf(365), BigDecimal.ROUND_HALF_UP);
        long daysInFinalPeriod = calculateDaysBetween(transaction.getLastRepaymentDate(), transaction.getMaturityDate());
        return dailyRate.multiply(BigDecimal.valueOf(daysInFinalPeriod)).multiply(transaction.getCbal());
    }

    // New methods for handling business days and holidays
    private void validateLoanDates(Date startDate, int termMonths) throws Exception {
        Date adjustedStartDate = adjustToNextBusinessDay(startDate);
        Date maturityDate = calculateMaturityDate(adjustedStartDate, termMonths);
        Date adjustedMaturityDate = adjustToNextBusinessDay(maturityDate);

        if (!startDate.equals(adjustedStartDate)) {
            throw new Exception("Loan start date is a holiday or non-business day, adjusted to " + adjustedStartDate);
        }
        if (!maturityDate.equals(adjustedMaturityDate)) {
            throw new Exception("Loan maturity date is a holiday or non-business day, adjusted to " + adjustedMaturityDate);
        }
    }

    private Date adjustToNextBusinessDay(Date date) {
        return workCalendarRepository.getNextBusinessDay(date);
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
