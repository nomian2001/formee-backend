package dtapcs.springframework.Formee.api.mapper;

import dtapcs.springframework.Formee.api.model.FormTemplateFullDTO;
import dtapcs.springframework.Formee.domain.FormTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FormTemplateFullMapper {
    FormTemplateFullMapper INSTANCE = Mappers.getMapper(FormTemplateFullMapper.class);
    FormTemplateFullDTO formTemplateToFormTemplateFullDTO(FormTemplate formTemplate);
}
