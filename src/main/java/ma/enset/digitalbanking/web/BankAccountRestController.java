package ma.enset.digitalbanking.web;

import ma.enset.digitalbanking.dtos.*;
import ma.enset.digitalbanking.exceptions.BankAccountNotFoundException;
import ma.enset.digitalbanking.exceptions.BalanceNotSufficientException;
import ma.enset.digitalbanking.exceptions.CustomerNotFoundException;
import ma.enset.digitalbanking.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestController {

    private BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // ===== GET ACCOUNT BY ID =====
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId)
            throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    // ===== GET ALL ACCOUNTS =====
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.listBankAccounts();
    }

    // ===== GET ACCOUNTS BY CUSTOMER =====
    @GetMapping("/customers/{customerId}/accounts")
    public List<BankAccountDTO> getCustomerAccounts(
            @PathVariable Long customerId) {
        return bankAccountService.getCustomerAccounts(customerId);
    }

    // ===== GET OPERATIONS HISTORY =====
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(
            @PathVariable String accountId) {
        return bankAccountService.accountHistory(accountId);
    }

    // ===== GET OPERATIONS HISTORY WITH PAGINATION =====
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size)
            throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page, size);
    }

    // ===== DEBIT =====
    @PostMapping("/accounts/debit")
    public void debit(
            @RequestParam String accountId,
            @RequestParam double amount,
            @RequestParam String description)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.debit(accountId, amount, description);
    }

    // ===== CREDIT =====
    @PostMapping("/accounts/credit")
    public void credit(
            @RequestParam String accountId,
            @RequestParam double amount,
            @RequestParam String description)
            throws BankAccountNotFoundException {
        bankAccountService.credit(accountId, amount, description);
    }

    // ===== TRANSFER =====
    @PostMapping("/accounts/transfer")
    public void transfer(
            @RequestParam String accountSource,
            @RequestParam String accountDestination,
            @RequestParam double amount)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        bankAccountService.transfer(accountSource, accountDestination, amount);
    }

    // ===== CREATE CURRENT ACCOUNT =====
    @PostMapping("/accounts/current")
    public CurrentBankAccountDTO saveCurrentAccount(
            @RequestParam double initialBalance,
            @RequestParam double overDraft,
            @RequestParam Long customerId)
            throws CustomerNotFoundException {
        return bankAccountService.saveCurrentBankAccount(
                initialBalance, overDraft, customerId);
    }

    // ===== CREATE SAVING ACCOUNT =====
    @PostMapping("/accounts/saving")
    public SavingBankAccountDTO saveSavingAccount(
            @RequestParam double initialBalance,
            @RequestParam double interestRate,
            @RequestParam Long customerId)
            throws CustomerNotFoundException {
        return bankAccountService.saveSavingBankAccount(
                initialBalance, interestRate, customerId);
    }
}