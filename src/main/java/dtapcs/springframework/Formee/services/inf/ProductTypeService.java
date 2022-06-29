package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.ProductType;

import java.util.List;
import java.util.UUID;

public interface ProductTypeService {
    ProductType createOrUpdate(ProductType type);

    ProductType findById(UUID typeId);

    List<ProductType> findAllTypes();

    void deleteById(UUID typeId);
}
