package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {
    Product createProduct(Product product);

    Product findById(UUID productID);

    List<Product> findAllByUser();

    List<Product> filterProduct(String keywords, String typeId);

    void setImageName(String name, String imageList, UUID productID);

    void updateInventoryAndSales(UUID productID, int quantity);

    Boolean checkInventory(UUID productID, int quantity);

    void deleteById(UUID productID);

    Map<String, String> getTotalStatistics(String username);
}
