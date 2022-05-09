package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.FormeeUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<FormeeUser, String> {
}
