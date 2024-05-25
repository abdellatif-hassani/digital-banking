package hassani.abdellatif.backend.repositories;

import hassani.abdellatif.backend.entities.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
    AuthEntity findByEmail(String email);
}
