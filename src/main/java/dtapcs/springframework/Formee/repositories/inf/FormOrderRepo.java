package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.FormOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FormOrderRepo extends JpaRepository<FormOrder, UUID> {
    List<FormOrder> findAllByFormId(String formId);

    List<FormOrder> findAllByCreatedByOrderByCreatedDateDesc(String username);
    @Query(nativeQuery = true,
            value = "SELECT *" +
            "FROM form_order" +
            "WHERE form_order.created_by =:userName AND EXTRACT(MONTH FROM form_order.created_date) =:inMonth AND EXTRACT (YEAR FROM form_order.created_date)=:inYear")
    List<FormOrder> findOrderOfUserByMonth(@Param("userName") String userName,@Param("inMonth") int month, @Param("inYear") int year);
}
