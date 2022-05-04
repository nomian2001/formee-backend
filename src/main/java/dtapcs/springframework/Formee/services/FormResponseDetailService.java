package dtapcs.springframework.Formee.services;

import dtapcs.springframework.Formee.domain.FormResponseDetails;
import dtapcs.springframework.Formee.domain.FormResponseDetailsId;

import java.util.UUID;

public interface FormResponseDetailService {
    FormResponseDetails getFormResponseDetail(FormResponseDetailsId id);
    String updateFormResponseDetail(FormResponseDetails details);
}
