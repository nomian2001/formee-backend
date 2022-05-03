package dtapcs.springframework.Formee.domain;

import dtapcs.springframework.Formee.enums.HistoryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class UserHistory {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private FormeeUser userId;

    private HistoryType type;
    private LocalDateTime accessedAt;

    public UserHistory(FormeeUser userId, HistoryType type, LocalDateTime accessedAt) {
        this.userId = userId;
        this.type = type;
        this.accessedAt = accessedAt;
    }

    public UserHistory() {
    }

    @Override
    public String toString() {
        return "UserSetting{" +
                "uuid='" + uuid + '\'' +
                ", userId='" + userId + '\'' +
                ", type=" + type +
                ", accessedAt=" + accessedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHistory that = (UserHistory) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
