package dtapcs.springframework.Formee.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class FormeeUser {
    @Id
    @Column(updatable = false, nullable = false)
    private String uuid;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userId")
    private UserSettings userSettings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
    private Set<UserHistory> userHistories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customerId")
    private Set<FormResponse> formResponses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerId")
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

    public void UpdateProfile(FormeeUser newUser) {
        this.userSettings = newUser.getUserSettings();
        this.userHistories = newUser.getUserHistories();
        this.formResponses = newUser.getFormResponses();
        this.shops = newUser.getShops();
        this.username = newUser.getUsername();
        this.fullName = newUser.getFullName();
        this.password = newUser.getPassword();
        this.gender = newUser.getGender();
        this.email = newUser.getEmail();
        this.profilePicture = newUser.getProfilePicture();
        this.createAt = createAt;
    }
}
