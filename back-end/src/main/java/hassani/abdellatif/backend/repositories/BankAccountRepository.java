package hassani.abdellatif.backend.repositories;

import hassani.abdellatif.backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {

    List<BankAccount> findByCustomerId(Long customerId);
}
