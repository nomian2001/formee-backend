package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.Role;
import dtapcs.springframework.Formee.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(ERole name);
}
