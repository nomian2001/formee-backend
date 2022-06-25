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
    public List<Product> findAllByUserId(String userId) {
        return productRepo.findAllByUserId(userId);
    }

    @Override
    public void setImageName(String name, UUID productId){
        Product product = productRepo.getById(productId);
        product.setImageName(name);
        productRepo.save(product);
    }

    @Override
    public void decreaseInventory(UUID productId, int quantity) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            product.setInventory(product.getInventory() - quantity);
            productRepo.save(product);
        }
    }
}
