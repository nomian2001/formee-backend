package dtapcs.springframework.Formee.services;

import dtapcs.springframework.Formee.api.model.FormTemplateFullDTO;
import dtapcs.springframework.Formee.api.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.domain.FormTemplate;

import java.util.List;
import java.util.UUID;

public interface FormTemplateService {
    List<FormTemplateSummaryDTO> getAllFormTemplateSummary();
    FormTemplate getFormTemplateByID(UUID id);
    List<FormTemplateFullDTO> getAllFormTemplateFull();
    String createFormTemplate(FormTemplate formTemplate);
}
