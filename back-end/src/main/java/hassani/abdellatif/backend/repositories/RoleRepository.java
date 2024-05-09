package hassani.abdellatif.backend.repositories;

import hassani.abdellatif.backend.entities.ERole;
import hassani.abdellatif.backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
