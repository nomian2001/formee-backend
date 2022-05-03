package dtapcs.springframework.Formee.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
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
