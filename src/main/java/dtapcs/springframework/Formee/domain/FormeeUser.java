package dtapcs.springframework.Formee.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
public class FormeeUser {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column( updatable = false, nullable = false)
    private UUID uuid;

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

    public Set<UserHistory> getUserHistories() {
        return userHistories;
    }

    public void setUserHistories(Set<UserHistory> userHistories) {
        this.userHistories = userHistories;
    }

    public Set<FormResponse> getFormResponses() {
        return formResponses;
    }

    public void setFormResponses(Set<FormResponse> formResponses) {
        this.formResponses = formResponses;
    }

    public Set<Shop> getShops() {
        return shops;
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
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
