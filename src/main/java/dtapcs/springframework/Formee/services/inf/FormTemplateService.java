package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.dtos.model.FormTemplateFullDTO;
import dtapcs.springframework.Formee.dtos.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.entities.FormTemplate;

import java.util.List;
import java.util.UUID;

public interface FormTemplateService {
    List<FormTemplateSummaryDTO> getRecentTemplates(String username);

    List<FormTemplateSummaryDTO> getAllFormTemplateSummary();

    FormTemplate getFormTemplateByID(UUID id);

    List<FormTemplateFullDTO> getAllFormTemplateFull();

    void createFormTemplate(FormTemplate formTemplate);
}
