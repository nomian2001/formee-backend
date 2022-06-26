package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, UUID> {
    List<Customer> findAllByUserIdOrderByCreatedDateDesc(String userId);

    Customer findByPhoneAndUserId(String phone, String userId);

    Long countByCreatedBy(String username);

    Long countByCreatedByAndTotalOrders(String username, Long totalOrders);

    Long countByCreatedByAndTotalOrdersGreaterThan(String username, Long totalOrders);
}
