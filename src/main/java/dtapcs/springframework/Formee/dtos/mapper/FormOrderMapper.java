package dtapcs.springframework.Formee.dtos.mapper;

import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface FormOrderMapper {
    FormOrderMapper INSTANCE = Mappers.getMapper(FormOrderMapper.class);
    @Mapping(source = "formId",target = "formId", qualifiedByName = "formToFormId")
    FormOrderDTO formOrderToFormOrderDTO(FormOrder formOrder);
    @Named("formToFormId")
    public static UUID formToFormId(Form form)
    {
        return form.getUuid();
    }
}
