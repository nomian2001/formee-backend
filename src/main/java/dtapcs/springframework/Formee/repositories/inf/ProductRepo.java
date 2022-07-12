package dtapcs.springframework.Formee.repositories.inf;

import dtapcs.springframework.Formee.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID> {

    List<Product> findAllByCreatedByOrderByCreatedDateDesc(String username);

    Long countByCreatedBy(String username);

    Long countByCreatedByAndInventory(String username, Long inventory);

//    @Query(nativeQuery = true, value = "SELECT SUM(inventory) FROM product WHERE created_by = :username")
//    Long countAllInventory(String username);
}
