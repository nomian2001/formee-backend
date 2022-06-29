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
}
