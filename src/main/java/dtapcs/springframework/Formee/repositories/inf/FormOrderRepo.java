package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FormOrderRepo extends JpaRepository<FormOrder, UUID> {

    List<FormOrder> findAllByCreatedByOrderByCreatedDateDesc(String username);

    @Query(nativeQuery = true,
            value = "SELECT * FROM form_order " +
                    "WHERE created_by = :userName " +
                    "AND EXTRACT (MONTH FROM created_date) = :inMonth " +
                    "AND EXTRACT (YEAR FROM created_date) = :inYear ")
    List<FormOrder> findOrderOfUserByMonth(@Param("userName") String userName, @Param("inMonth") int month, @Param("inYear") int year);

    Long countByCreatedBy(String createdBy);

    Long countByCreatedByAndStatus(String createdBy, OrderStatus status);
}
