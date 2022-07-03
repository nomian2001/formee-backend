package dtapcs.springframework.Formee.dtos.mapper;

import dtapcs.springframework.Formee.dtos.model.FormDTO;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FormMapper {
    FormMapper INSTANCE = Mappers.getMapper(FormMapper.class);

    @Named("formOrdersToFormOrders")
    static Set<UUID> formOrdersToFormOrders(Set<FormOrder> formOrders) {
        return formOrders.stream().map(FormOrder::getUuid).collect(Collectors.toSet());
    }

    @Mapping(source = "formOrders", target = "formOrders", qualifiedByName = "formOrdersToFormOrders")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "imagePath", target = "imagePath")
    FormDTO FormToFormDTO(Form form);
}
