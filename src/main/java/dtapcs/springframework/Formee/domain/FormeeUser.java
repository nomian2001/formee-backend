package dtapcs.springframework.Formee.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class FormeeUser {
    @Id
    @Column( updatable = false, nullable = false)
    private String uuid;

    @OneToOne (fetch = FetchType.LAZY, mappedBy = "userId")
    private UserSettings userSettings;
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "userId")
    private Set<UserHistory> userHistories;
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "customerId")
    private Set<FormResponse> formResponses;
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "ownerId")
    private Set<Shop> shops;

    private String username;
    private String fullName;
    private String password;
    private int gender;
    private String email;
    private String profilePicture;
    private LocalDateTime createAt;

    public FormeeUser() {
    }

    public FormeeUser(String uuid) {
        this.uuid = uuid;
    }

    public FormeeUser(UserSettings userSettings, Set<UserHistory> userHistories, Set<FormResponse> formResponses, Set<Shop> shops, String username, String fullName, String password, int gender, String email, String profilePicture, LocalDateTime createAt) {
        this.userSettings = userSettings;
        this.userHistories = userHistories;
        this.formResponses = formResponses;
        this.shops = shops;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.profilePicture = profilePicture;
        this.createAt = createAt;
    }


    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", createAt=" + createAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormeeUser user = (FormeeUser) o;

        return uuid.equals(user.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
