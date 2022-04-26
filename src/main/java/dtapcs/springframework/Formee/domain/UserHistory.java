package dtapcs.springframework.Formee.domain;

import dtapcs.springframework.Formee.enums.HistoryType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class UserHistory {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    private String userId;

    private HistoryType type;
    private LocalDateTime accessedAt;

    public UserHistory(String userId, HistoryType type, LocalDateTime accessedAt) {
        this.userId = userId;
        this.type = type;
        this.accessedAt = accessedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HistoryType getType() {
        return type;
    }

    public void setType(HistoryType type) {
        this.type = type;
    }

    public LocalDateTime getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(LocalDateTime accessedAt) {
        this.accessedAt = accessedAt;
    }

    public UUID getUuid() {
        return uuid;
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
