package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.FormOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FormOrderRepo extends JpaRepository<FormOrder, UUID> {
    List<FormOrder> findAllByFormId(String formId);

    List<FormOrder> findAllByCreatedByOrderByCreatedDateDesc(String username);
}
