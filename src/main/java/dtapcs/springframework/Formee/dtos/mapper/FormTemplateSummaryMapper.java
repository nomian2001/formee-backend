package dtapcs.springframework.Formee.dtos.mapper;

import dtapcs.springframework.Formee.dtos.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.entities.FormTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FormTemplateSummaryMapper {
    FormTemplateSummaryMapper INSTANCE = Mappers.getMapper(FormTemplateSummaryMapper.class);

    FormTemplateSummaryDTO formTemplateToFormTemplateSummaryDTO(FormTemplate formTemplate);
}
