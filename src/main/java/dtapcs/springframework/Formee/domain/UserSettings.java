package dtapcs.springframework.Formee.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class UserSettings {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", unique = true)

    private FormeeUser userId;

    private String settingsJson;

    public UserSettings(FormeeUser userId, String settingsJson) {
        this.userId = userId;
        this.settingsJson = settingsJson;
    }

    public UUID getUuid() {
        return uuid;
    }

    public FormeeUser getUserId() {
        return userId;
    }

    public void setUserId(FormeeUser userId) {
        this.userId = userId;
    }

    public String getSettingsJson() {
        return settingsJson;
    }

    public void setSettingsJson(String settingsJson) {
        this.settingsJson = settingsJson;
    }

    public UserSettings() {
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "uuid=" + uuid +
                ", userId=" + userId +
                ", settingsJson='" + settingsJson + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSettings that = (UserSettings) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
