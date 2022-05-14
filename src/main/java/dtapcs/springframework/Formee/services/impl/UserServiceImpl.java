package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.FormeeUser;
import dtapcs.springframework.Formee.repositories.inf.UserRepo;
import dtapcs.springframework.Formee.services.inf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public String updateProfile(FormeeUser user) {
        userRepo.findById(user.getUuid()).get().UpdateProfile(user);
        return null;
    }
}
