package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {
    Product createProduct(Product product);

    Product findById(UUID productID);

    List<Product> findAllByUserId(String userId);

    List<Product> filterProduct(String keywords, List<String> types);

    void setImageName(String name, String imageList, UUID productID);

    void updateInventoryAndSales(UUID productID, int quantity);

    void deleteById(UUID productID);

    Map<String, String> getTotalStatistics(String username);
}
