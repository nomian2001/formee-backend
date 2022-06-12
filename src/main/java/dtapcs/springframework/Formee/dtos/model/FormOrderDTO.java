package dtapcs.springframework.Formee.dtos.model;

import dtapcs.springframework.Formee.entities.Auditable;
import lombok.Data;

import java.util.UUID;

@Data
public class FormOrderDTO extends Auditable {
    private UUID uuid;
    private String orderName;
    private UUID formId;
    private String response;
    private Boolean confirmed;
}
