package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductTypeRepo extends JpaRepository<ProductType, UUID> {
    ProductType findByNameAndCreatedBy(String name, String username);

    List<ProductType> findByCreatedByOrderByCreatedDateDesc(String username);

    Long countByCreatedBy(String createdBy);
}
