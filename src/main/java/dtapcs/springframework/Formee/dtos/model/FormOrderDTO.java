package dtapcs.springframework.Formee.dtos.model;

import dtapcs.springframework.Formee.entities.Auditable;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class FormOrderDTO extends Auditable {
    private UUID uuid;
    private UUID formId;
    private String status;
    private String response;
    private String orderName;
    private Boolean confirmed;
    private Boolean requested;
    private Integer discount;
    List<CommentDTO> comments;
}
