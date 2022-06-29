package dtapcs.springframework.Formee.dtos.mapper;

import dtapcs.springframework.Formee.dtos.model.ProductDTO;
import dtapcs.springframework.Formee.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "inventory", target = "inventory")
    ProductDTO productToProductDTO(Product product);
}
