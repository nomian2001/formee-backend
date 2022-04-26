package dtapcs.springframework.Formee.repositories;

import dtapcs.springframework.Formee.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
}
