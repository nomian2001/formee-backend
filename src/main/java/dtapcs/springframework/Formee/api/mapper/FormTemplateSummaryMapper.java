package dtapcs.springframework.Formee.api.mapper;

import dtapcs.springframework.Formee.api.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.domain.FormTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FormTemplateSummaryMapper {
    FormTemplateSummaryMapper INSTANCE = Mappers.getMapper(FormTemplateSummaryMapper.class);
    FormTemplateSummaryDTO formTemplateToFormTemplateSummaryDTO(FormTemplate formTemplate);
}
