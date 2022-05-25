package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.entities.FormOrder;

import java.util.List;
import java.util.UUID;

public interface FormOrderService {
    FormOrder createOrder(FormOrder order);

    FormOrder getById(UUID id);

    List<FormOrder> findAllByUserName(String userId);
}
