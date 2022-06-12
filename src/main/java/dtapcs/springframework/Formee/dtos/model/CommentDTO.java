package dtapcs.springframework.Formee.dtos.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentDTO {
    private UUID uuid;
    private UUID orderId;
    private String message;
}
