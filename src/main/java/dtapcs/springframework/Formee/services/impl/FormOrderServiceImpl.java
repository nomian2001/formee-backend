package dtapcs.springframework.Formee.services.impl;

import com.itextpdf.text.DocumentException;
import dtapcs.springframework.Formee.dtos.model.CommentDTO;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.dtos.model.UserDetails;
import dtapcs.springframework.Formee.dtos.request.ExportRequest;
import dtapcs.springframework.Formee.dtos.request.FormOrderSearchRequest;
import dtapcs.springframework.Formee.entities.*;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.enums.PeriodType;
import dtapcs.springframework.Formee.helper.CommonHelper;
import dtapcs.springframework.Formee.helper.ExcelHelper;
import dtapcs.springframework.Formee.helper.PDFHelper;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    UserRepo userRepo;

    @Autowired
    FormService formService;

    @Autowired
    CommentService commentService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductTypeService productTypeService;

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
            commentService.createComment(comment, false, false);

            JSONArray response = new JSONArray(newOrder.getResponse());

            // save customer info
            createCustomer(response);

            // update inventory
            JSONArray products = new JSONArray(response.get(4).toString());
            for (int i = 0; i < products.length(); ++i) {
                JSONObject obj = products.getJSONObject(i);
                productService.updateInventoryAndSales(UUID.fromString(obj.getString("uuid")), obj.getInt("quantity"));
            }

            return result;
        }
        return null;
    }

    private void createCustomer(JSONArray response) {
        Customer customer = new Customer();
        customer.setPhone(response.getString(0));
        customer.setName(response.getString(1));
        customer.setAddress(response.get(2).toString());

        UserDetails userDetails = CommonHelper.getCurrentUser();
        customer.setUserId(userDetails.getId());
        customerService.createCustomer(customer);
    }

    @Override
    public List<FormOrder> filterOrder(List<OrderStatus> orderStatus, String start, String end, String keywords, UUID formId) {
        List<FormOrder> orderList;
        if (formId != null) {
            Form form = formRepo.findById(formId).orElse(null);
            if (form != null) {
                orderList = findOrdersByFormId(form);
            } else {
                return null;
            }
        } else {
            orderList = findAllOrders();
        }
        Stream<FormOrder> orderStream = orderList.stream();

        if (StringUtils.hasText(keywords)) {
            orderStream = orderStream.filter(order -> {
                JSONArray response = new JSONArray(order.getResponse());
                return order.getOrderName().toLowerCase().contains(keywords) || response.getString(0).toLowerCase().contains(keywords)
                        || response.getString(1).toLowerCase().contains(keywords);
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
    public ResponseEntity<byte[]> exportInvoice(ExportRequest request) throws DocumentException, IOException, URISyntaxException {
        FormOrder order = this.getById(request.getOriginalId());
        byte[] bytes = PDFHelper.exportInvoice(order, request.getEncodedId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment");
        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
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
        UserDetails userDetails = CommonHelper.getCurrentUser();
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
                OrderStatus status = dto.getStatus();
                currentOrder.setStatus(status);
                if (status.equals(OrderStatus.CONFIRMED)) {
                    // create comment
                    CommentDTO comment = new CommentDTO();
                    comment.setMessage("Đã xác nhận đơn hàng.");
                    comment.setOrderId(currentOrder.getUuid());
                    commentService.createComment(comment, false, false);
                }
            } else {
                if (dto.getRequested()) {
                    CommentDTO comment = new CommentDTO();
                    comment.setMessage("Đã chỉnh sửa đơn hàng: " + dto.getMessage());
                    comment.setOrderId(currentOrder.getUuid());
                    commentService.createComment(comment, false, false);
                    dto.setStatus(OrderStatus.PENDING);
                }
                currentOrder.UpdateFormOrder(dto);
            }

            return formOrderRepo.save(currentOrder);
        }
        return null; // không tìm thấy form cần update
    }

    @Override
    public String duplicateOrder(UUID formOrderId) {
        FormOrder order = formOrderRepo.findById(formOrderId).orElse(null);
        if (order == null) {
            return "message.common.not.found";
        }
        JSONArray response = new JSONArray(order.getResponse());

        // check inventory
        JSONArray products = new JSONArray(response.get(4).toString());
        for (int i = 0; i < products.length(); ++i) {
            JSONObject obj = products.getJSONObject(i);
            UUID productId = UUID.fromString(obj.getString("uuid"));
            int quantity = obj.getInt("quantity");
            if (productService.checkInventory(productId, quantity)) {
                productService.updateInventoryAndSales(productId, quantity);
            } else {
                return "message.order.duplicate.not.enough.inventory";
            }
        }

        FormOrder newOrder = new FormOrder();
        newOrder.duplicate(order);
        newOrder.setStatus(OrderStatus.PENDING);
        FormOrder result = formOrderRepo.save(newOrder);

        // add new comment
        CommentDTO comment = new CommentDTO();
        comment.setMessage("Đã tạo đơn hàng.");
        comment.setOrderId(result.getUuid());
        commentService.createComment(comment, false, false);

        return "";
    }

    @Override
    public Map<String, String> getRevenueStatistics(String userName, int year, PeriodType type) {
        Map<String, String> result = new HashMap<>();
        JSONArray income = new JSONArray();
        JSONArray revenue = new JSONArray();

        Calendar calendar = Calendar.getInstance();
        int numOfWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        switch (type) {
            case WEEK:
                calendar.set(Calendar.DAY_OF_WEEK, 1);
                int startDate = calendar.get(Calendar.DAY_OF_MONTH) - 6;
                for (int day = startDate; day < (startDate + 7); ++day) {
                    List<FormOrder> orderOfCurrentWeek = formOrderRepo
                            .findOrderOfUserByDayOfWeek(userName, currentMonth, currentYear, day);
                    double weekSaleTotal = 0;
                    double weekCostTotal = 0;
                    for (FormOrder order : orderOfCurrentWeek) {
                        double total = 0;
                        double costTotal = 0;
                        JSONArray response = new JSONArray(order.getResponse());
                        JSONArray products = new JSONArray(response.get(4).toString()); // actual response
                        for (int i = 0; i < products.length(); ++i) {
                            JSONObject obj = products.getJSONObject(i);
                            if (obj.has("productPrice") && obj.has("quantity") && obj.has("costPrice")) {
                                total += obj.getInt("productPrice") * obj.getInt("quantity");
                                costTotal += obj.getInt("costPrice") * obj.getInt("quantity");
                            }
                        }
                        weekSaleTotal += ((total * (100 - order.getDiscount())) / 100);
                        weekCostTotal += costTotal;
                    }
                    income.put(weekSaleTotal);
                    revenue.put(weekSaleTotal - weekCostTotal);
                }
                JSONObject objWeek = new JSONObject().put("income", income).put("revenue", revenue);
                result.put(String.valueOf(year), objWeek.toString());
                break;
            case MONTH:
                for (int week = 1; week <= numOfWeeks; ++week) {
                    List<FormOrder> orderOfCurrentMonth = formOrderRepo
                            .findOrderOfUserByWeek(userName, currentMonth, currentYear, String.valueOf(week));
                    double weekSaleTotal = 0;
                    double weekCostTotal = 0;
                    for (FormOrder order : orderOfCurrentMonth) {
                        double total = 0;
                        double costTotal = 0;
                        JSONArray response = new JSONArray(order.getResponse());
                        JSONArray products = new JSONArray(response.get(4).toString()); // actual response
                        for (int i = 0; i < products.length(); ++i) {
                            JSONObject obj = products.getJSONObject(i);
                            if (obj.has("productPrice") && obj.has("quantity") && obj.has("costPrice")) {
                                total += obj.getInt("productPrice") * obj.getInt("quantity");
                                costTotal += obj.getInt("costPrice") * obj.getInt("quantity");
                            }
                        }
                        weekSaleTotal += ((total * (100 - order.getDiscount())) / 100);
                        weekCostTotal += costTotal;
                    }
                    income.put(weekSaleTotal);
                    revenue.put(weekSaleTotal - weekCostTotal);
                }
                JSONObject objMonth = new JSONObject().put("income", income).put("revenue", revenue);
                result.put(String.valueOf(year), objMonth.toString());
                break;
            case YEAR:
                for (int month = 1; month <= 12; ++month) {
                    List<FormOrder> orderOfCurrentMonth = formOrderRepo.findOrderOfUserByMonth(userName, month, year);
                    double monthSaleTotal = 0;
                    double monthCostTotal = 0;
                    for (FormOrder order : orderOfCurrentMonth) {
                        double total = 0;
                        double costTotal = 0;
                        JSONArray response = new JSONArray(order.getResponse());
                        JSONArray products = new JSONArray(response.get(4).toString()); // actual response
                        for (int i = 0; i < products.length(); ++i) {
                            JSONObject obj = products.getJSONObject(i);
                            if (obj.has("productPrice") && obj.has("quantity") && obj.has("costPrice")) {
                                total += obj.getInt("productPrice") * obj.getInt("quantity");
                                costTotal += obj.getInt("costPrice") * obj.getInt("quantity");
                            }
                        }
                        monthSaleTotal += ((total * (100 - order.getDiscount())) / 100);
                        monthCostTotal += costTotal;
                    }
                    income.put(monthSaleTotal);
                    revenue.put(monthSaleTotal - monthCostTotal);
                }
                JSONObject objYear = new JSONObject().put("income", income).put("revenue", revenue);
                result.put(String.valueOf(year), objYear.toString());
                break;
        }
        return result;
    }

    @Override
    public Map<String, String> getCategoryStatistics(String userName, PeriodType type) {
        Map<String, String> result = new HashMap<>();
        JSONObject sales = new JSONObject();
        JSONObject income = new JSONObject();
        JSONObject revenue = new JSONObject();

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        int currentWeek = calendar.get(Calendar.WEEK_OF_MONTH);
        List<FormOrder> orderList = new ArrayList<>();

        switch (type) {
            case WEEK:
                orderList = formOrderRepo.findOrderOfUserByWeek(userName, currentMonth, currentYear, String.valueOf(currentWeek));
                break;
            case MONTH:
                orderList = formOrderRepo.findOrderOfUserByMonth(userName, currentMonth, currentYear);
                break;
            case YEAR:
                orderList = formOrderRepo.findOrderOfUserByYear(userName, currentYear);
                break;
        }

        for (FormOrder order : orderList) {
            JSONArray response = new JSONArray(order.getResponse());
            JSONArray products = new JSONArray(response.get(4).toString()); // actual response
            for (int i = 0; i < products.length(); ++i) {
                JSONObject obj = products.getJSONObject(i);
                if (obj.has("typeId") && !obj.get("typeId").toString().equals("null")) {
                    ProductType productType = productTypeService.findById(UUID.fromString(obj.getString("typeId")));
                    if (productType != null) {
                        String typeName = productType.getName();
                        Integer quantity = Integer.parseInt(obj.get("quantity").toString());
                        Integer productPrice = Integer.parseInt(obj.get("productPrice").toString());
                        Integer costPrice = Integer.parseInt(obj.get("costPrice").toString());
                        if (sales.has(productType.getName())) {
                            sales.put(typeName, Integer.parseInt(sales.get(typeName).toString()) + quantity);
                            income.put(typeName, Integer.parseInt(income.get(typeName).toString()) + (quantity * productPrice));
                            revenue.put(typeName, Integer.parseInt(revenue.get(typeName).toString()) + (quantity * (productPrice - costPrice)));
                        } else {
                            sales.put(typeName, quantity);
                            income.put(typeName, quantity * productPrice);
                            revenue.put(typeName, quantity * (productPrice - costPrice));
                        }
                    }
                }
            }
        }

        result.put("sales", sales.toString());
        result.put("income", income.toString());
        result.put("revenue", revenue.toString());
        return result;
    }

    @Override
    public Map<String, String> getProductStatistics(String userName) {
        Map<String, String> result = new HashMap<>();
        List<FormOrder> orderOfCurrentMonth = formOrderRepo.findAllByCreatedByOrderByCreatedDateDesc(userName);
        for (FormOrder order : orderOfCurrentMonth) {
            JSONArray response = new JSONArray(order.getResponse());
            JSONArray products = new JSONArray(response.get(4).toString()); // actual response
            for (int i = 0; i < products.length(); ++i) {
                JSONObject obj = products.getJSONObject(i);
                if (obj.has("uuid")) {
                    Product product = productService.findById(UUID.fromString(obj.getString("uuid")));
                    if (product != null) {
                        if (!result.containsKey(product.getName())) {
                            result.put(product.getName(), String.valueOf(product.getSales()));
                        }
                    }
                }
            }
        }
        return result.entrySet().stream()
                .sorted(Comparator.comparingLong(e -> Long.parseLong(e.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    @Override
    public Map<String, String> getCustomerStatistics(String userName, int year, PeriodType type) {
        Map<String, String> result = new HashMap<>();
        JSONArray data = new JSONArray();

        Calendar calendar = Calendar.getInstance();
        int numOfWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        switch (type) {
            case WEEK:
                calendar.set(Calendar.DAY_OF_WEEK, 1);
                int startDate = calendar.get(Calendar.DAY_OF_MONTH) - 6;
                for (int day = startDate; day < (startDate + 7); ++day) {
                    List<String> phoneList = new ArrayList<>();
                    List<FormOrder> orderOfCurrentWeek = formOrderRepo.findOrderOfUserByDayOfWeek(userName, currentMonth, currentYear, day);
                    for (FormOrder order : orderOfCurrentWeek) {
                        JSONArray response = new JSONArray(order.getResponse());
                        String phone = response.get(0).toString();
                        if (!phoneList.contains(phone)) {
                            phoneList.add(phone); // phone number is unique to each customer
                        }
                    }
                    data.put(phoneList.size());
                }
                break;
            case MONTH:
                for (int week = 1; week <= numOfWeeks; ++week) {
                    List<String> phoneList = new ArrayList<>();
                    List<FormOrder> orderOfCurrentMonth = formOrderRepo.findOrderOfUserByWeek(userName, currentMonth, currentYear, String.valueOf(week));
                    for (FormOrder order : orderOfCurrentMonth) {
                        JSONArray response = new JSONArray(order.getResponse());
                        String phone = response.get(0).toString();
                        if (!phoneList.contains(phone)) {
                            phoneList.add(phone); // phone number is unique to each customer
                        }
                    }
                    data.put(phoneList.size());
                }
                break;
            case YEAR:
                for (int month = 1; month <= 12; ++month) {
                    List<String> phoneList = new ArrayList<>();
                    List<FormOrder> orderOfCurrentYear = formOrderRepo.findOrderOfUserByMonth(userName, month, year);
                    for (FormOrder order : orderOfCurrentYear) {
                        JSONArray response = new JSONArray(order.getResponse());
                        String phone = response.get(0).toString();
                        if (!phoneList.contains(phone)) {
                            phoneList.add(phone); // phone number is unique to each customer
                        }
                    }
                    data.put(phoneList.size());
                }
                break;
        }

//        for (int month = 1; month <= 12; ++month) {
//            List<String> phoneList = new ArrayList<>();
//            List<FormOrder> orderOfCurrentMonth = formOrderRepo.findAllByCreatedByOrderByCreatedDateDesc(userName);
//            for (FormOrder order : orderOfCurrentMonth) {
//                JSONArray response = new JSONArray(order.getResponse());
//                String phone = response.get(0).toString();
//                if (!phoneList.contains(phone)) {
//                    phoneList.add(phone); // phone number is unique to each customer
//                }
//            }
//            data.put(phoneList.size());
//        }
        result.put(String.valueOf(year), data.toString());
        return result;
    }

    @Override
    public Map<String, String> getTotalStatistics(String username, PeriodType type) {
        Map<String, String> result = new HashMap<>();
        String interval = type.getName();
        Long total = formOrderRepo.countByCreatedBy(username, interval);
        result.put("total", String.valueOf(total));
        Long completed = formOrderRepo.countByCreatedByAndStatus(username, interval, OrderStatus.valueOf("COMPLETED").ordinal());
        result.put("completed", String.valueOf(completed));
        Long pending = formOrderRepo.countByCreatedByAndStatus(username, interval, OrderStatus.valueOf("PENDING").ordinal());
        result.put("pending", String.valueOf(pending));
        return result;
    }
}
