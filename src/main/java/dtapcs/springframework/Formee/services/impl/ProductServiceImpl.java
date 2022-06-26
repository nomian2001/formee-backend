package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.Product;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.repositories.inf.ProductRepo;
import dtapcs.springframework.Formee.services.inf.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Product findById(UUID productID) {
        return productRepo.findById(productID).orElse(null);
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
    public void updateInventoryAndSales(UUID productId, int quantity) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            product.setInventory(product.getInventory() - quantity);
            product.setSales(product.getSales() + quantity);
            productRepo.save(product);
        }
    }

    @Override
    public Map<String, String> getTotalStatistics(String username) {
        Map<String, String> result = new HashMap<>();
        Long total = productRepo.countByCreatedBy(username);
        result.put("total", String.valueOf(total));
        Long outOfStock = productRepo.countByCreatedByAndInventory(username, 0L);
        result.put("outOfStock", String.valueOf(outOfStock));
        Long totalInventory = productRepo.countAllInventory(username);
        result.put("totalInventory", String.valueOf(totalInventory));
        return result;
    }
}
