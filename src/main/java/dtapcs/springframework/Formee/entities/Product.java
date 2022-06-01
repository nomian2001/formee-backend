package dtapcs.springframework.Formee.entities;

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

//    @ManyToOne (fetch = FetchType.LAZY)
//    @JoinColumn(name = "shopId")
//    private Shop shopId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "formId")
//    private Form formId;

    private String formId;

    private String userId;

    private String name;

    private String description;

    private Long inventory;

    private Long productPrice;
}
