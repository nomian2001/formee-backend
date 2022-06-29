package dtapcs.springframework.Formee.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductSearchRequest {
    private String keywords;
    private Integer pageNumber;
    private Integer pageSize;
    private String typeId;
}
