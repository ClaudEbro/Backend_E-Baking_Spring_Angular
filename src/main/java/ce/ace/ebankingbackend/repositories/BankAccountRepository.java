package ce.ace.ebankingbackend.repositories;

import ce.ace.ebankingbackend.entities.BankAccount;
import ce.ace.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
