package dtapcs.springframework.Formee.dtos.request;

import lombok.Data;

@Data
public class CustomerSearchRequest {
    private String keywords;
    private Integer pageNumber;
    private Integer pageSize;
}
