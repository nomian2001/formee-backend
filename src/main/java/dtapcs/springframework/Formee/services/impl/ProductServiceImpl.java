package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.Product;
import dtapcs.springframework.Formee.repositories.inf.ProductRepo;
import dtapcs.springframework.Formee.services.inf.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepo productRepo;

    @Override
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public List<Product> findAllByFormId(String formId) {
        return productRepo.findAllByFormId(formId);
    }
    @Override
    public void setImageName(String name, UUID productId){
        Product product = productRepo.getById(productId);
        product.setImageName(name);
        productRepo.save(product);
    }
}
