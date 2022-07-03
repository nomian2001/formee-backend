package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FormRepo extends JpaRepository<Form, UUID> {
    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM form " +
                    "WHERE user_id = :userId " +
                    "ORDER BY last_modified_date DESC LIMIT 6")
    List<Form> getRecentForms(String userId);

    List<Form> findAllByUserId(String userId);

    List<Form> findAllByCreatedByOrderByCreatedDateDesc(String username);
}
