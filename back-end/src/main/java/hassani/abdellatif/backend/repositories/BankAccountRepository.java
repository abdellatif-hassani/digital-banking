package hassani.abdellatif.backend.repositories;

import hassani.abdellatif.backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
