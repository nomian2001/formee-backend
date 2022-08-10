package dtapcs.springframework.Formee.dtos.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ExportRequest {
    private UUID originalId;
    private String encodedId;
}
