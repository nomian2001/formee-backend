package dtapcs.springframework.Formee.services.impl;

import com.google.gson.Gson;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.repositories.inf.FormOrderRepo;
import dtapcs.springframework.Formee.repositories.inf.FormRepo;
import dtapcs.springframework.Formee.services.inf.FormOrderService;
import dtapcs.springframework.Formee.services.inf.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FormOrderServiceImpl implements FormOrderService {
    @Autowired
    FormRepo formRepo;
    @Autowired
    FormOrderRepo formOrderRepo;
    @Autowired
    FormService formService;

    @Override
    public FormOrder createOrder(FormOrderDTO dto) {
        Optional<Form> check = formRepo.findById(dto.getFormId());
        if (check.isPresent()) {
            Form form = check.get();
            if (dto.getOrderName().equals("")) {
                Long count = form.getUntitledCount();
                dto.setOrderName(String.join(" ", form.getName(), String.valueOf(count)));
                form.setUntitledCount(count + 1);
            }
            FormOrder newOrder = new FormOrder();
            newOrder.UpdateFormOrder(dto, form);
            form.AddOrder(newOrder);
            formRepo.save(form); // new last modified date
            return formOrderRepo.save(newOrder);
        }
        return null;
    }

    @Override
    public List<FormOrder> filterOrder(UUID formID, List<OrderStatus> orderStatus, LocalDateTime startDate, LocalDateTime endDate) {
        Form form = formService.getFormById(formID);
        if (form != null) {
            try {
                Set<FormOrder> orderList = form.getFormOrders();
                Stream<FormOrder> orderStream = orderList.stream();

                if (startDate != null) {
                    orderStream = orderStream.filter(order -> order.getCreatedDate().isAfter(startDate));
                }
                if (endDate != null) {
                    orderStream = orderStream.filter(order -> order.getCreatedDate().isBefore(startDate));
                }
                List<FormOrder> orderRes = orderStream.collect(Collectors.toList());
                if (orderStatus != null) {
                    List<FormOrder> tempRes = new ArrayList<>();
                    for (FormOrder order : orderRes) {
                        List<String> responseField = new Gson().fromJson(order.getResponse(), ArrayList.class);
                        if (orderStatus.contains(responseField.get(4))) { //field 4
                            tempRes.add(order);
                        }
                    }
                    orderRes = tempRes;
                }
                return orderRes;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public FormOrder getById(UUID id) {
        return formOrderRepo.findById(id).orElse(null);
    }

    @Override
    public List<FormOrder> findAllByFormId(String formId) {
        return formOrderRepo.findAllByFormId(formId);
    }

    @Override
    public FormOrder updateOrder(FormOrderDTO dto) {
        Optional<FormOrder> check = formOrderRepo.findById(dto.getUuid());
        if (check.isPresent()) {
            FormOrder currentOrder = check.get();
            currentOrder.UpdateFormOrder(dto);
            return formOrderRepo.save(currentOrder);
        }
        return null; //không tìm thấy form cần update
    }

    @Override
    public FormOrder duplicateOrder(UUID formOrderId) {
        FormOrder order = formOrderRepo.getById(formOrderId);
        FormOrder newOrder = new FormOrder();
        newOrder.setFormId(order.getFormId());
        newOrder.setResponse(order.getResponse());
        formOrderRepo.save(newOrder);
        return newOrder;
    }
}
