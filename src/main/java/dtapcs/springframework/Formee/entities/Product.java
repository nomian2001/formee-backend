package dtapcs.springframework.Formee.entities;

import dtapcs.springframework.Formee.enums.ProductType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Product extends Auditable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @Column(columnDefinition = "TEXT")
    private String imageBase64;

    private String imageName;

    private String imageList;

    private String formId;

    private String userId;

    private String name;

    private String description;

    private Long inventory;

    private Long productPrice;

    private Long costPrice;

    private ProductType type;
}
