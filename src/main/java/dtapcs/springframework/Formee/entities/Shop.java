package dtapcs.springframework.Formee.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Shop {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

//    @ManyToOne (fetch = FetchType.LAZY)
//    @JoinColumn(name="ownerId")
//    private FormeeUser ownerId;
//
//    @OneToOne (fetch = FetchType.LAZY, mappedBy = "shopId")
//    private ShopSettings shopSettings;

//    @OneToOne (fetch = FetchType.LAZY, mappedBy = "shopId")
//    private Form form;

//    @OneToMany (fetch = FetchType.LAZY, mappedBy = "shopId")
//    Set<Product> products;

    private String name;

    private String description;

}
