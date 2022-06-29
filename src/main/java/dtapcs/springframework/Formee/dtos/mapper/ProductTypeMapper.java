package dtapcs.springframework.Formee.dtos.mapper;

import dtapcs.springframework.Formee.dtos.model.ProductTypeDTO;
import dtapcs.springframework.Formee.entities.ProductType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductTypeMapper INSTANCE = Mappers.getMapper(ProductTypeMapper.class);

    @Mapping(source = "backgroundColor", target = "backgroundColor")
    ProductTypeDTO typeToTypeDTO(ProductType type);
}
