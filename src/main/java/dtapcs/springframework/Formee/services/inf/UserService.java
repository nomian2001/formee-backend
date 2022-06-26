package dtapcs.springframework.Formee.services.inf;

import dtapcs.springframework.Formee.dtos.model.UserDTO;

public interface UserService {
    String updateProfile(UserDTO user);

    UserDTO getUserProfile(String userId);
}
