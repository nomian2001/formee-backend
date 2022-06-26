package dtapcs.springframework.Formee.dtos.model;

import dtapcs.springframework.Formee.entities.FormeeUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDTO {
    private String uuid;
    private String email;
    private String phone;
    private String username;
    private String fullName;
    private String profilePicture;

    public UserDTO(FormeeUser user) {
        uuid = user.getUuid();
        email = user.getEmail();
        phone = user.getPhone();
        username = user.getUsername();
        fullName = user.getFullName();
        profilePicture = user.getProfilePicture();
    }
}
