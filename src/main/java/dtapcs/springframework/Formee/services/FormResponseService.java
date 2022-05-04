package dtapcs.springframework.Formee.services;

import dtapcs.springframework.Formee.domain.FormResponse;

import java.util.List;
import java.util.UUID;

public interface FormResponseService {
    List<FormResponse> getFormResponseList(int filterValue, int pageNumber, int rowPerPage);
    String exportFile(List<UUID> responseId, int exportType);
    String importFile(String formResponse);
}
