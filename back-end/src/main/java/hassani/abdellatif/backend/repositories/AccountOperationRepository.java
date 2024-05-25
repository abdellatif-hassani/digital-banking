package hassani.abdellatif.backend.repositories;

import hassani.abdellatif.backend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
  List<AccountOperation> findByBankAccountId(Long accountId);
  Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(Long accountId, Pageable pageable);
}
