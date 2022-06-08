package dtapcs.springframework.Formee.entities;

import dtapcs.springframework.Formee.enums.ResponsePermission;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Form extends Auditable {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formId")
    Set<FormOrder> formOrders;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    private Shop shopId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private FormeeUser userId;

    private String name;

    private String userId;

    private String templateId;
    private ResponsePermission responsePermission;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formId")
//    Set<Product> products;

    @Column(name = "layoutJson", columnDefinition = "TEXT")
    private String layoutJson;
}
