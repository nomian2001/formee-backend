package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    List<Product> findAllByFormId(String formId);
}
