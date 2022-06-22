package dtapcs.springframework.Formee.dtos.mapper;

import dtapcs.springframework.Formee.dtos.model.CustomerDTO;
import dtapcs.springframework.Formee.entities.Customer;
import dtapcs.springframework.Formee.entities.FormeeUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
//    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
//
//    @Named("customerToCustomerDTO")
//    static String customerToCustomerDTO(FormeeUser user) {
//        return user.getUuid();
//    }
//
//    @Mapping(source = "userId", target = "userId", qualifiedByName = "customerToCustomerDTO")
//    CustomerDTO customerToCustomerDTO(Customer customer);
}
