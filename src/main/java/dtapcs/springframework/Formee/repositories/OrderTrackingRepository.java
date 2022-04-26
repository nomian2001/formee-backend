package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.OrderTracking;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderTrackingRepository extends CrudRepository<OrderTracking, UUID> {
}
