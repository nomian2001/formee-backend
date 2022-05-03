package dtapcs.springframework.Formee.domain;

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

    public Product() {
    }

    public Product(Shop shopId, String name, String description, Long inventory, Long productPrice) {
        this.shopId = shopId;
        this.name = name;
        this.description = description;
        this.inventory = inventory;
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "uuid=" + uuid +
                ", shopId='" + shopId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", inventory=" + inventory +
                ", productPrice=" + productPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return uuid.equals(product.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
