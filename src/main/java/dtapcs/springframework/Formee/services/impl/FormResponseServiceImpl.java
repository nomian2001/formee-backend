package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.FormResponse;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.repositories.inf.FormRepo;
import dtapcs.springframework.Formee.repositories.inf.FormResponseRepo;
import dtapcs.springframework.Formee.services.inf.FormResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FormResponseServiceImpl implements FormResponseService {
    @Autowired
    private FormResponseRepo formResponseRepo;
    @Autowired
    private FormRepo formRepo;

    @Override
    public List<FormResponse> getFormResponseList(UUID formID, List<OrderStatus> orderStatus, List<String> customerName, LocalDateTime startDate, LocalDateTime endDate, int pageNumber, int rowPerPage) {
        Set<FormResponse> orderList = formRepo.findById(formID).get().getFormResponses();
        Stream<FormResponse> orderStream = orderList.stream();
        if (customerName != null) {
            orderStream = orderStream.filter(order -> customerName.contains(order.getCustomerName()));
        }
        if (orderStatus != null) {
            orderStream = orderStream.filter(order -> orderStatus.contains(order.getOrderStatus()));
        }
        if (startDate != null) {
            orderStream = orderStream.filter(order -> order.getCreatedDate().isAfter(startDate));
        }
        if (endDate != null) {
            orderStream = orderStream.filter(order -> order.getCreatedDate().isBefore(startDate));
        }
        List<FormResponse> orderRes = orderStream.collect(Collectors.toList());
        int startIndex = (pageNumber - 1) * rowPerPage;
        int endIndex = startIndex + rowPerPage - 1;
        return orderRes.subList(startIndex, endIndex);
    }

    @Override
    public String exportFile(List<UUID> responseId, int exportType) {
        //code export file
        return null;
    }

    @Override
    public String importFile(String formResponse) {
        //code import file
        return null;
    }
}
