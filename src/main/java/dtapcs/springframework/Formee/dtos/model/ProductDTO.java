package dtapcs.springframework.Formee.dtos.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductDTO {
    private UUID uuid;

    private String imageName;

    private String imageList;

    private String name;

    private String description;

    private Long inventory;

    private Long productPrice;

    private Long costPrice;

    private String typeId;
}
