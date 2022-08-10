package dtapcs.springframework.Formee.services.inf;

import com.itextpdf.text.DocumentException;
import dtapcs.springframework.Formee.dtos.model.FormOrderDTO;
import dtapcs.springframework.Formee.dtos.model.OrderStatisticsDTO;
import dtapcs.springframework.Formee.dtos.request.ExportRequest;
import dtapcs.springframework.Formee.dtos.request.FormOrderSearchRequest;
import dtapcs.springframework.Formee.entities.FormOrder;
import dtapcs.springframework.Formee.enums.OrderStatus;
import dtapcs.springframework.Formee.enums.PeriodType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FormOrderService {
    FormOrder createOrder(FormOrderDTO dto);

    FormOrder getById(UUID id);

    List<FormOrder> findRecentOrders();

    FormOrder updateOrder(FormOrderDTO dto, Boolean statusOnly);

    String duplicateOrder(UUID formOrderId);

    List<FormOrder> filterOrder(List<OrderStatus> orderStatus, String startDate, String endDate, String keywords, UUID formId);

    ResponseEntity<Resource> export(FormOrderSearchRequest request);

    ResponseEntity<byte[]> exportInvoice(ExportRequest request) throws DocumentException, IOException, URISyntaxException;

    Map<String, String> getRevenueStatistics(String userName, int year, PeriodType type);

    Map<String, String> getCategoryStatistics(String userName, PeriodType type);

    Map<String, String> getProductStatistics(String userName);

    Map<String, String> getCustomerStatistics(String userName, int year, PeriodType type);

    Map<String, String> getTotalStatistics(String username, PeriodType type);
}
