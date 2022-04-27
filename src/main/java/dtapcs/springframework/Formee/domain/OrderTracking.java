package dtapcs.springframework.Formee.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
@Entity
public class OrderTracking {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;
    private String key;
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="orderId")
    private FormResponse orderId;
    private String permissionJson;

    public OrderTracking() {
    }

    public OrderTracking(String key, FormResponse orderId, String permissionJson) {
        this.key = key;
        this.orderId = orderId;
        this.permissionJson = permissionJson;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FormResponse getOrderId() {
        return orderId;
    }

    public void setOrderId(FormResponse orderId) {
        this.orderId = orderId;
    }

    public String getPermissionJson() {
        return permissionJson;
    }

    public void setPermissionJson(String permissionJson) {
        this.permissionJson = permissionJson;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "OrderTracking{" +
                "uuid=" + uuid +
                ", key='" + key + '\'' +
                ", orderId='" + orderId + '\'' +
                ", permissionJson='" + permissionJson + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderTracking that = (OrderTracking) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
