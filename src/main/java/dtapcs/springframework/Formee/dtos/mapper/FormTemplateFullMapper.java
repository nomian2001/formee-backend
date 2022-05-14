package dtapcs.springframework.Formee.dtos.mapper;

import dtapcs.springframework.Formee.dtos.model.FormTemplateFullDTO;
import dtapcs.springframework.Formee.entities.FormTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FormTemplateFullMapper {
    FormTemplateFullMapper INSTANCE = Mappers.getMapper(FormTemplateFullMapper.class);

    FormTemplateFullDTO formTemplateToFormTemplateFullDTO(FormTemplate formTemplate);
}
