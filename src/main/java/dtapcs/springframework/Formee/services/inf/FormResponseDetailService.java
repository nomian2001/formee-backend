package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.FormResponseDetails;
import dtapcs.springframework.Formee.entities.FormResponseDetailsId;

public interface FormResponseDetailService {
    FormResponseDetails getFormResponseDetail(FormResponseDetailsId id);

    String updateFormResponseDetail(FormResponseDetails details);
}
