package dtapcs.springframework.Formee.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column( updatable = false, nullable = false)
    private UUID uuid;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    private Shop shopId;

    private String name;

    private String description;

    private Long inventory;

    private Long productPrice;

}
