package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.Shop;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ShopRepository extends CrudRepository<Shop, UUID> {
}
