package ce.ace.ebankingbackend;

import ce.ace.ebankingbackend.entities.*;
import ce.ace.ebankingbackend.enums.AccountStatus;
import ce.ace.ebankingbackend.enums.OperationType;
import ce.ace.ebankingbackend.repositories.AccountOperationRepository;
import ce.ace.ebankingbackend.repositories.BankAccountRepository;
import ce.ace.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository){
        return args -> {
            //Display account information
            BankAccount bankAccount =
                    bankAccountRepository.findById("02abf903-a1d4-44da-8661-d090bd639c45").orElse(null);

            if(bankAccount != null) {
                System.out.println("=======================");
                System.out.println(bankAccount.getId());
                System.out.println(bankAccount.getBalance());
                System.out.println(bankAccount.getStatus());
                System.out.println(bankAccount.getCreatedAt());
                System.out.println(bankAccount.getCustomer().getName());
                System.out.println(bankAccount.getClass().getSimpleName());
                if (bankAccount instanceof CurrentAccount) {
                    System.out.println("Over Draft=>"+((CurrentAccount) bankAccount).getOverDraft());
                } else if (bankAccount instanceof SavingAccount) {
                    System.out.println("Rate=>"+((SavingAccount) bankAccount).getInterestRate());
                }
                bankAccount.getAccountOperations().forEach(op -> {
                    System.out.println(op.getType() + "\t" + op.getOperationDate() + "\t" + op.getAmount());
                });
            }
        };
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            AccountOperationRepository accountOperationRepository,
                            BankAccountRepository bankAccountRepository){
        return  args -> {

            //Create customers
            Stream.of("Claudio","Tendresse","Michaela").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

            //Create accounts for each customer
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(10000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });

            //Create account operations
            bankAccountRepository.findAll().forEach(acc->{
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }
}
