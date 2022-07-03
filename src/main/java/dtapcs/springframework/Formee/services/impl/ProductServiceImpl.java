package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.entities.Product;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.helper.CommonHelper;
import dtapcs.springframework.Formee.repositories.inf.ProductRepo;
import dtapcs.springframework.Formee.repositories.inf.ProductTypeRepo;
import dtapcs.springframework.Formee.services.inf.ProductService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductTypeRepo typeRepo;

    @Override
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product findById(UUID productID) {
        return productRepo.findById(productID).orElse(null);
    }

    @Override
    public List<Product> findAllByUser() {
        UserDetails userDetails = CommonHelper.getCurrentUser();
        return productRepo.findAllByCreatedByOrderByCreatedDateDesc(userDetails.getUsername());
    }

    @Override
    public List<Product> filterProduct(String keywords, String typeId) {
        UserDetails userDetails = CommonHelper.getCurrentUser();
        List<Product> productList = productRepo.findAllByCreatedByOrderByCreatedDateDesc(userDetails.getUsername());
        Stream<Product> productStream = productList.stream();

        if (StringUtils.hasText(keywords)) {
            productStream = productStream.filter(product -> product.getName().toLowerCase().contains(keywords));
        }
        if (StringUtils.hasText(typeId)) {
            productStream = productStream.filter(product -> product.getTypeId().equals(typeId));
        }

        return productStream.collect(Collectors.toList());
    }

    @Override
    public void setImageName(String name, String imageList, UUID productId){
        Product product = productRepo.getById(productId);
        product.setImageName(name);
        product.setImageList(imageList);
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
    public Boolean checkInventory(UUID productId, int quantity) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            return quantity <= product.getInventory();
        }
        return false;
    }

    @Override
    public void deleteById(UUID productID) {
        productRepo.deleteById(productID);
    }

    @Override
    public Map<String, String> getTotalStatistics(String username) {
        Map<String, String> result = new HashMap<>();
        Long total = productRepo.countByCreatedBy(username);
        result.put("total", String.valueOf(total));
        Long outOfStock = productRepo.countByCreatedByAndInventory(username, 0L);
        result.put("outOfStock", String.valueOf(outOfStock));
        Long totalTypes = typeRepo.countByCreatedBy(username);
        result.put("totalTypes", totalTypes != null ? String.valueOf(totalTypes) : "0");
        return result;
    }
}
