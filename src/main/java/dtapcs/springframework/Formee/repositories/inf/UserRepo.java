package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.FormeeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<FormeeUser, String> {
    @Query(nativeQuery = true, value = "SELECT email FROM formee_user WHERE username = :username")
    String getEmailByUsername(String username);

    FormeeUser findByPhone(String phone);
}
