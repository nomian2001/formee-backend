package dtapcs.springframework.Formee.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

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


    public Shop getShopId() {
        return shopId;
    }

    public void setShopId(Shop shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInventory() {
        return inventory;
    }

    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public UUID getUuid() {
        return uuid;
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
