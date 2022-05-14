package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.FormeeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<FormeeUser, String> {
}
