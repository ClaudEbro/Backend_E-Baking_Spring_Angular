package ce.ace.ebankingbackend.services;

import ce.ace.ebankingbackend.entities.BankAccount;
import ce.ace.ebankingbackend.entities.CurrentAccount;
import ce.ace.ebankingbackend.entities.SavingAccount;
import ce.ace.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
   @Autowired
   private BankAccountRepository bankAccountRepository;
   public void Consult(){

            //Display account information and operations
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
   }
}
