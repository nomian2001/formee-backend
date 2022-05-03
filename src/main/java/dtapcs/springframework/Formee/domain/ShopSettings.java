package dtapcs.springframework.Formee.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class ShopSettings {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column( updatable = false, nullable = false)
    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopId")
    private Shop shopId;
    private String customizationJson;

    public ShopSettings() {
    }

    public ShopSettings(Shop shopId, String customizationJson) {
        this.shopId = shopId;
        this.customizationJson = customizationJson;
    }

    @Override
    public String toString() {
        return "ShopSetting{" +
                "uuid=" + uuid +
                ", shopId='" + shopId + '\'' +
                ", customizationJson='" + customizationJson + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopSettings that = (ShopSettings) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
