package dtapcs.springframework.Formee.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ShopSetting {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    private String shopId;
    private String customizationJson;

    public ShopSetting() {
    }

    public ShopSetting(String shopId, String customizationJson) {
        this.shopId = shopId;
        this.customizationJson = customizationJson;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCustomizationJson() {
        return customizationJson;
    }

    public void setCustomizationJson(String customizationJson) {
        this.customizationJson = customizationJson;
    }

    public UUID getUuid() {
        return uuid;
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

        ShopSetting that = (ShopSetting) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
