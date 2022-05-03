package dtapcs.springframework.Formee.domain;

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

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="ownerId")
    private FormeeUser ownerId;
    @OneToOne (fetch = FetchType.LAZY, mappedBy = "shopId")
    private ShopSettings shopSettings;
    @OneToOne (fetch = FetchType.LAZY, mappedBy = "shopId")
    private Form form;
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "shopId")
    Set<Product> products;
    private String name;
    private String description;

    public Shop() {
    }

    public Shop(FormeeUser ownerId, ShopSettings shopSettings, Form form, Set<Product> products, String name, String description) {
        this.ownerId = ownerId;
        this.shopSettings = shopSettings;
        this.form = form;
        this.products = products;
        this.name = name;
        this.description = description;
    }


    @Override
    public String toString() {
        return "Shop{" +
                "uuid=" + uuid +
                ", ownerId=" + ownerId +
                ", shopSettings=" + shopSettings +
                ", form=" + form +
                ", products=" + products +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        return uuid.equals(shop.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
