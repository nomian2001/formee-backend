package dtapcs.springframework.Formee.services;

import dtapcs.springframework.Formee.domain.FormeeUser;
import dtapcs.springframework.Formee.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String updateProfile(FormeeUser user) {
        return null;
    }
}
