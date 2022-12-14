package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.dtos.model.FormDTO;
import dtapcs.springframework.Formee.dtos.model.FormTemplateSummaryDTO;
import dtapcs.springframework.Formee.entities.Form;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FormService {
    Form createForm(FormDTO formDTO);

    Form getFormById(UUID formId);

    List<Form> getRecentForms();

    List<Form> getFormsByUser();

    Boolean checkFormPermission(String userId,  UUID form);

    void updatePermission(UUID formId);
}
