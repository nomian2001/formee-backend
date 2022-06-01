package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.dtos.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.entities.Form;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FormService {
    Form createForm(Form form);

    Optional<Form> getFormById(UUID formId);

    List<FormTemplateSummaryDTO> getRecentForms(String userId);
}
