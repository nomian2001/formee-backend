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
            value = "SELECT DISTINCT (form_id), created_date " +
                    "FROM form_order " +
                    "WHERE created_by = :username " +
                    "ORDER BY created_date DESC LIMIT 6")
    List<Object[]> getRecentResponses(String username);
}
