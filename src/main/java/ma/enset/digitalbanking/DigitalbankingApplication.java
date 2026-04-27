package ma.enset.digitalbanking;

import ma.enset.digitalbanking.entities.*;
import ma.enset.digitalbanking.enums.AccountStatus;
import ma.enset.digitalbanking.enums.OperationType;
import ma.enset.digitalbanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalbankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalbankingApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            CustomerRepository customerRepository,
            BankAccountRepository bankAccountRepository,
            AccountOperationRepository accountOperationRepository
    ) {
        return args -> {

            // ===== ETAPE 1 : Créer des Customers =====
            Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name.toLowerCase() + "@gmail.com");
                customerRepository.save(customer);
            });

            System.out.println("====== Customers créés ======");
            customerRepository.findAll().forEach(c ->
                    System.out.println("Customer : " + c.getId() + " | " + c.getName() + " | " + c.getEmail())
            );

            // ===== ETAPE 2 : Créer des BankAccounts =====
            customerRepository.findAll().forEach(customer -> {

                // Compte Courant
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCreatedAt(new Date());
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setStatus(AccountStatus.ACTIVATED);
                currentAccount.setOverDraft(9000);
                currentAccount.setCustomer(customer);
                bankAccountRepository.save(currentAccount);

                // Compte Epargne
                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCreatedAt(new Date());
                savingAccount.setBalance(Math.random() * 90000);
                savingAccount.setStatus(AccountStatus.ACTIVATED);
                savingAccount.setInterestRate(5.5);
                savingAccount.setCustomer(customer);
                bankAccountRepository.save(savingAccount);
            });

            System.out.println("====== Comptes créés ======");
            bankAccountRepository.findAll().forEach(b -> {
                if (b instanceof CurrentAccount ca) {
                    System.out.println("CurrentAccount | ID: " + ca.getId() +
                            " | Balance: " + ca.getBalance() +
                            " | OverDraft: " + ca.getOverDraft() +
                            " | Client: " + ca.getCustomer().getName());
                } else if (b instanceof SavingAccount sa) {
                    System.out.println("SavingAccount  | ID: " + sa.getId() +
                            " | Balance: " + sa.getBalance() +
                            " | InterestRate: " + sa.getInterestRate() +
                            " | Client: " + sa.getCustomer().getName());
                }
            });

            // ===== ETAPE 3 : Créer des Opérations =====
            bankAccountRepository.findAll().forEach(bankAccount -> {
                for (int i = 0; i < 5; i++) {

                    // CREDIT
                    AccountOperation creditOp = new AccountOperation();
                    creditOp.setOperationDate(new Date());
                    creditOp.setAmount(Math.random() * 12000);
                    creditOp.setType(OperationType.CREDIT);
                    creditOp.setDescription("Credit operation " + i);
                    creditOp.setBankAccount(bankAccount);
                    accountOperationRepository.save(creditOp);

                    // DEBIT
                    AccountOperation debitOp = new AccountOperation();
                    debitOp.setOperationDate(new Date());
                    debitOp.setAmount(Math.random() * 9000);
                    debitOp.setType(OperationType.DEBIT);
                    debitOp.setDescription("Debit operation " + i);
                    debitOp.setBankAccount(bankAccount);
                    accountOperationRepository.save(debitOp);
                }
            });

            System.out.println("====== Opérations créées ======");
            System.out.println("Total opérations : " + accountOperationRepository.count());
            System.out.println("=========== DATA LOADED SUCCESSFULLY ===========");
        };
    }
}