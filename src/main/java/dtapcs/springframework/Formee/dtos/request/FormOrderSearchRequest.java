package dtapcs.springframework.Formee.dtos.request;

import dtapcs.springframework.Formee.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class FormOrderSearchRequest {
    private UUID formId;
    private String startDate;
    private String endDate;
    private String keywords;
    private Integer pageNumber;
    private Integer pageSize;
    private List<OrderStatus> orderStatus;
}
