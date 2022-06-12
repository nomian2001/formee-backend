package dtapcs.springframework.Formee.dtos.mapper;

import dtapcs.springframework.Formee.dtos.model.FormDTO;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
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
    @Mapping(source = "formOrders",target = "formOrders", qualifiedByName = "formOrdersToFormOrders")
    FormDTO FormToFormDTO(Form form);
    @Named("formOrdersToFormOrders")
    public static Set<UUID> formOrdersToFormOrders(Set<FormOrder> formOrders)
    {
        return formOrders.stream().map(FormOrder::getUuid).collect(Collectors.toSet());
    }
}
