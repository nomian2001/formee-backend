package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(Product product);

    List<Product> findAllByFormId(String formId);
    void setImageName(String name, UUID productID);
}
