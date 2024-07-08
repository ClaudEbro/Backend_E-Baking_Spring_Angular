package ce.ace.ebankingbackend.repositories;

import ce.ace.ebankingbackend.entities.AccountOperation;
import ce.ace.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
}
