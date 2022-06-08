package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FormOrderService {
    FormOrder createOrder(FormOrderDTO dto);

    FormOrder getById(UUID id);

    List<FormOrder> findAllByFormId(String formId);
    FormOrder updateOrder(FormOrderDTO dto);
    FormOrder duplicateOrder(UUID formOrderId);
    List<FormOrder> filterOrder(UUID formID, List<OrderStatus> orderStatus, LocalDateTime startDate, LocalDateTime endDate);
}
