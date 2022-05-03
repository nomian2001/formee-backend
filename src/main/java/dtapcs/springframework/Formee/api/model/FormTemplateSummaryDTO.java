package dtapcs.springframework.Formee.api.model;

import lombok.Data;

import java.util.UUID;

@Data
public class FormTemplateSummaryDTO {
    private UUID uuid;
    private String name;
    private String image;
}
