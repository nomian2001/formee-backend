package dtapcs.springframework.Formee.services.impl;

import com.google.gson.Gson;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.repositories.inf.FormOrderRepo;
import dtapcs.springframework.Formee.repositories.inf.FormRepo;
import dtapcs.springframework.Formee.services.inf.FormOrderService;
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

    @Override
    public FormOrder createOrder(FormOrder order) {
        Form form = order.getFormId();
        if (form!=null) {
            //Form form = check.get();

            formRepo.save(form); // new last modified date
            if(order.getOrderName()==null || order.getOrderName()=="")
            {
                String orderName = form.getName() + " 1";
            }
            return formOrderRepo.save(order);
        }
        return null;
    }
    @Override
    public List<FormOrder> filterOrder(UUID formID, List<OrderStatus> orderStatus, LocalDateTime startDate, LocalDateTime endDate){
        Set<FormOrder> orderList = formRepo.findById(formID).get().getFormOrders();
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
            for(FormOrder order: orderRes){
                List<String> responseField = new Gson().fromJson(order.getResponse(), ArrayList.class);
                if(orderStatus.contains(responseField.get(4))){ //field 4
                    tempRes.add(order);
                }
            }
            orderRes = tempRes;
        }
        return orderRes;
    }
    @Override
    public FormOrder getById(UUID id) {
        return formOrderRepo.getById(id);
    }

    @Override
    public List<FormOrder> findAllByFormId(String formId) {
        return formOrderRepo.findAllByFormId(formId);
    }
    @Override
    public FormOrder updateOrder(FormOrder updatedOrder)
    {
        Optional<FormOrder> check = formOrderRepo.findById(updatedOrder.getUuid());
        if(check.isPresent())
        {
            return formOrderRepo.save(updatedOrder);
        }
        return null; //không tìm thấy form cần update
    }
    @Override
    public FormOrder duplicateOrder(UUID formOrderId){
        FormOrder order = formOrderRepo.getById(formOrderId);
        FormOrder newOrder = new FormOrder();
        newOrder.setFormId(order.getFormId());
        newOrder.setResponse(order.getResponse());
        formOrderRepo.save(newOrder);
        return newOrder;
    }
}
