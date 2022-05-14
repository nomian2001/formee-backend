package dtapcs.springframework.Formee.dtos.model;

import lombok.Data;

import java.util.UUID;
@Data
public class FormTemplateFullDTO {
    private UUID uuid;
    private String name;
    private String image;
    private String description;
}
