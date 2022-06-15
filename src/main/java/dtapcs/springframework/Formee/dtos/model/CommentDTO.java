package dtapcs.springframework.Formee.dtos.model;

import dtapcs.springframework.Formee.entities.Auditable;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentDTO extends Auditable {
    private UUID uuid;
    private UUID orderId;
    private String message;
}
