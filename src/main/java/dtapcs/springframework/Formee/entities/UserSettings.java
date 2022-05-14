package dtapcs.springframework.Formee.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", unique = true)
    private FormeeUser userId;

    private String settingsJson;

    public void UpdateSettings(UserSettings newSettings) {
        settingsJson = newSettings.getSettingsJson();
    }
}
