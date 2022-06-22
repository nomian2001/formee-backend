package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.dtos.request.FormOrderSearchRequest;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.enums.OrderStatus;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface FormOrderService {
    FormOrder createOrder(FormOrderDTO dto);

    FormOrder getById(UUID id);

    List<FormOrder> findRecentOrders();

    FormOrder updateOrder(FormOrderDTO dto, Boolean statusOnly);

    FormOrder duplicateOrder(UUID formOrderId);

    List<FormOrder> filterOrder(List<OrderStatus> orderStatus, String startDate, String endDate, String keywords, UUID formId);

    ResponseEntity<Resource> export(FormOrderSearchRequest request);
}
