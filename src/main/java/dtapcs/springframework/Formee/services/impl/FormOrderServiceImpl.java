package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.model.CommentDTO;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.dtos.request.FormOrderSearchRequest;
import dtapcs.springframework.Formee.entities.Customer;
import dtapcs.springframework.Formee.entities.Form;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.helper.ExcelHelper;
import dtapcs.springframework.Formee.repositories.inf.FormOrderRepo;
import dtapcs.springframework.Formee.repositories.inf.FormRepo;
import dtapcs.springframework.Formee.repositories.inf.UserRepo;
import dtapcs.springframework.Formee.services.inf.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FormOrderServiceImpl implements FormOrderService {
    @Autowired
    FormRepo formRepo;
    @Autowired
    FormOrderRepo formOrderRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    FormService formService;
    @Autowired
    CommentService commentService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ExcelService fileService;
    @Autowired
    ProductService productService;

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
            newOrder.setStatus(OrderStatus.PENDING);
            FormOrder result = formOrderRepo.save(newOrder);

            // add new comment
            CommentDTO comment = new CommentDTO();
            comment.setMessage("Đã tạo đơn hàng.");
            comment.setOrderId(result.getUuid());
            commentService.createComment(comment, false);

            // save customer info
            Customer customer = new Customer();
            JSONArray response = new JSONArray(newOrder.getResponse());
            customer.setPhone(response.getString(0));
            customer.setName(response.getString(1));
            customer.setAddress(response.get(2).toString());

            // update inventory
            JSONArray products = new JSONArray(response.get(4).toString());
            for (int i = 0; i < products.length(); ++i) {
                JSONObject obj = products.getJSONObject(i);
                productService.decreaseInventory(UUID.fromString(obj.getString("uuid")), obj.getInt("quantity"));
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = (UserDetails) principal;
            customer.setUserId(userDetails.getId());
            customerService.createCustomer(customer);

            return result;
        }
        return null;
    }

    @Override
    public List<FormOrder> filterOrder(List<OrderStatus> orderStatus, String start, String end, String keywords, UUID formId) {
        List<FormOrder> orderList = new ArrayList<>();
        if (formId != null) {
            Form form = formRepo.findById(formId).orElse(null);
            if (form != null) {
                orderList = findOrdersByFormId(form);
            }
            else {
                return null;
            }
        }
        else {
            orderList = findAllOrders();
        }
        Stream<FormOrder> orderStream = orderList.stream();

        if (StringUtils.hasText(keywords)) {
            orderStream = orderStream.filter(order -> {
                JSONArray response = new JSONArray(order.getResponse());
                return order.getOrderName().contains(keywords) || response.getString(0).contains(keywords)
                        || response.getString(1).contains(keywords) || response.getString(2).contains(keywords);
            });
        }
        if (StringUtils.hasText(start)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(start, formatter);
            orderStream = orderStream.filter(order -> order.getCreatedDate().isAfter(startDate));
        }
        if (StringUtils.hasText(end)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime endDate = LocalDateTime.parse(end, formatter);
            orderStream = orderStream.filter(order -> order.getCreatedDate().isBefore(endDate));
        }
        if (orderStatus != null && orderStatus.size() > 0) {
            orderStream = orderStream.filter(order -> orderStatus.contains(order.getStatus()));
        }
        return orderStream.collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Resource> export(FormOrderSearchRequest request) {
        String filename = "formResponse.xlsx";
        String formName = "";
        if (request.getFormId() != null) {
            Form form = formRepo.findById(request.getFormId()).orElse(null);
            if (form != null) {
                formName = form.getName();
            }
        }
        List<FormOrder> orderList = filterOrder(request.getOrderStatus(), request.getStartDate(),
                                                request.getEndDate(), request.getKeywords(), null);
        ByteArrayInputStream bais = ExcelHelper.FormResponseToExcel(orderList, formName, request.getStartDate(), request.getEndDate());
        InputStreamResource file = new InputStreamResource(bais);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @Override
    public FormOrder getById(UUID id) {
        return formOrderRepo.findById(id).orElse(null);
    }

    @Override
    public List<FormOrder> findRecentOrders() {
        List<FormOrder> result = findAllOrders();
        return result.subList(0, Math.min(result.size(), 5));
    }

    private List<FormOrder> findAllOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        return formOrderRepo.findAllByCreatedByOrderByCreatedDateDesc(userDetails.getUsername());
    }

    private List<FormOrder> findOrdersByFormId(Form formId) {
        return new ArrayList<>(formId.getFormOrders());
    }

    @Override
    public FormOrder updateOrder(FormOrderDTO dto, Boolean statusOnly) {
        Optional<FormOrder> check = formOrderRepo.findById(dto.getUuid());
        if (check.isPresent()) {
            FormOrder currentOrder = check.get();
            if (statusOnly) {
                currentOrder.setStatus(dto.getStatus());
            }
            else {
                currentOrder.UpdateFormOrder(dto);
            }
            return formOrderRepo.save(currentOrder);
        }
        return null; // không tìm thấy form cần update
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
