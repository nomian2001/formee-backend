package dtapcs.springframework.Formee.entities;

import dtapcs.springframework.Formee.dtos.model.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class FormeeUser extends Auditable {
    @Id
    @Column(updatable = false, nullable = false)
    private String uuid;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "userId")
    private UserSettings userSettings;

    private String username;

    private String fullName;

    private String password;

    private int gender;

    private String email;

    private String profilePicture;

    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public FormeeUser() {

    }

    public FormeeUser(String uuid) {
        this.uuid = uuid;
    }

    public FormeeUser(UserSettings userSettings, String username, String fullName, String password, int gender, String email, String profilePicture) {
        this.userSettings = userSettings;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public void UpdateProfile(FormeeUser newUser) {
        this.userSettings = newUser.getUserSettings();
//        this.userHistories = newUser.getUserHistories();
        this.username = newUser.getUsername();
        this.fullName = newUser.getFullName();
        this.password = newUser.getPassword();
        this.gender = newUser.getGender();
        this.email = newUser.getEmail();
        this.profilePicture = newUser.getProfilePicture();
    }

    public void updateProfileFromDTO(UserDTO newUser) {
        this.username = newUser.getUsername();
        this.fullName = newUser.getFullName();
        this.email = newUser.getEmail();
        this.profilePicture = newUser.getProfilePicture();
        this.phone = newUser.getPhone();
    }
}
