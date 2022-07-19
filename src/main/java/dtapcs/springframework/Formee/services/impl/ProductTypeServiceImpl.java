package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.entities.ProductType;
import dtapcs.springframework.Formee.helper.CommonHelper;
import dtapcs.springframework.Formee.repositories.inf.ProductTypeRepo;
import dtapcs.springframework.Formee.services.inf.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeRepo productTypeRepo;

    @Override
    public ProductType createOrUpdate(ProductType type) {
        UserDetails userDetails = CommonHelper.getCurrentUser();
        ProductType check = productTypeRepo.findByNameAndCreatedBy(type.getName(), userDetails.getUsername());
        if (check != null && !check.getUuid().equals(type.getUuid())) {
            return null;
        }
        return productTypeRepo.save(type);
    }

    @Override
    public ProductType findById(UUID typeId) {
        return productTypeRepo.findById(typeId).orElse(null);
    }

    @Override
    public List<ProductType> findAllTypes() {
        UserDetails userDetails = CommonHelper.getCurrentUser();
        return productTypeRepo.findByCreatedByOrderByCreatedDateDesc(userDetails.getUsername());
    }

    @Override
    public void deleteById(UUID typeId) {
        productTypeRepo.deleteById(typeId);
    }

    @Override
    public void createMultipleTypes(List<String> types) {
        List<String> colors = Arrays.asList("#60d7e4", "#e1879f", "#96d0a4", "#f4bc9c", "#ffd24d");
        List<String> backgroundColors = Arrays.asList("#d0f3f7", "#f6dbe3", "#e0f1e4", "#f8e7de", "#fdf5d1");
        for (int i = 0; i < types.size(); ++i) {
            ProductType newType = new ProductType();
            newType.setName(types.get(i));
            newType.setColor(colors.get(i));
            newType.setBackgroundColor(backgroundColors.get(i));
            productTypeRepo.save(newType);
        }
    }
}
