package dtapcs.springframework.Formee.dtos.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductTypeDTO {
    private UUID uuid;
    private String name;
    private String color;
    private String backgroundColor;
}
