package ma.enset.digitalbanking.services;

import ma.enset.digitalbanking.dtos.*;
import ma.enset.digitalbanking.exceptions.*;

import java.util.List;

public interface BankAccountService {

    // ===== CUSTOMER =====
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    List<CustomerDTO> searchCustomers(String keyword);

    // ===== BANK ACCOUNT =====
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
            throws CustomerNotFoundException;

    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
            throws CustomerNotFoundException;

    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccountDTO> listBankAccounts();
    List<BankAccountDTO> getCustomerAccounts(Long customerId);

    // ===== OPERATIONS =====
    void debit(String accountId, double amount, String description)
            throws BankAccountNotFoundException, BalanceNotSufficientException;

    void credit(String accountId, double amount, String description)
            throws BankAccountNotFoundException;

    void transfer(String accountIdSource, String accountIdDestination, double amount)
            throws BankAccountNotFoundException, BalanceNotSufficientException;

    // ===== OPERATIONS HISTORY =====
    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size)
            throws BankAccountNotFoundException;
}