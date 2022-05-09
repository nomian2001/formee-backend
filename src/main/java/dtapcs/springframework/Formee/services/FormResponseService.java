package dtapcs.springframework.Formee.services;

import dtapcs.springframework.Formee.domain.FormResponse;
import dtapcs.springframework.Formee.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface FormResponseService {
    List<FormResponse> getFormResponseList(UUID formID, List<OrderStatus> orderStatus, List<String> customerName, LocalDateTime startDate, LocalDateTime endDate, int pageNumber, int rowPerPage);
    String exportFile(List<UUID> responseId, int exportType);
    String importFile(String formResponse);
}
