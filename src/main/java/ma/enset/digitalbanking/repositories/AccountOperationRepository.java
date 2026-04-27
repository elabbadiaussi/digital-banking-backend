package ma.enset.digitalbanking.repositories;

import ma.enset.digitalbanking.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

    // Récupérer toutes les opérations d'un compte
    List<AccountOperation> findByBankAccountId(String accountId);

    // Récupérer les opérations avec PAGINATION
    Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);
}