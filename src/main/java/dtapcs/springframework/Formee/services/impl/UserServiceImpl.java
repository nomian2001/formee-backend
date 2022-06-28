package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.dtos.model.UserDTO;
import dtapcs.springframework.Formee.entities.FormeeUser;
import dtapcs.springframework.Formee.repositories.inf.UserRepo;
import dtapcs.springframework.Formee.services.inf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public String updateProfile(UserDTO dto) {
        if (StringUtils.hasText(dto.getPhone())) {
            FormeeUser checkPhone = userRepo.findByPhone(dto.getPhone());
            if (checkPhone != null && !checkPhone.getUuid().equals(dto.getUuid())) {
                return "message.user.duplicate.phone";
            }
        }
        FormeeUser user = userRepo.findById(dto.getUuid()).orElse(null);
        if (user == null) {
            return "message.common.not.found";
        }
        user.updateProfileFromDTO(dto);
        userRepo.save(user);
        return "";
    }

    @Override
    public UserDTO getUserProfile(String userId) {
        FormeeUser user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        return new UserDTO(user);
    }
}
