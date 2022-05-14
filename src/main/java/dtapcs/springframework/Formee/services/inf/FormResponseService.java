package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.FormResponse;
import dtapcs.springframework.Formee.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FormResponseService {
    List<FormResponse> getFormResponseList(UUID formID, List<OrderStatus> orderStatus, List<String> customerName, LocalDateTime startDate, LocalDateTime endDate, int pageNumber, int rowPerPage);

    String exportFile(List<UUID> responseId, int exportType);

    String importFile(String formResponse);
}
