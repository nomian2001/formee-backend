package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.OrderTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderTrackingRepo extends JpaRepository<OrderTracking, UUID> {
}
