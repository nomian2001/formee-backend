package dtapcs.springframework.Formee.services.impl;

import dtapcs.springframework.Formee.entities.UserSettings;
import dtapcs.springframework.Formee.repositories.inf.UserSettingsRepo;
import dtapcs.springframework.Formee.services.inf.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingServiceImpl implements UserSettingService {
    @Autowired
    private UserSettingsRepo userSettingsRepo;

    @Override
    public String updateUserSetting(UserSettings settings) {
        userSettingsRepo.findById(settings.getUuid()).get().UpdateSettings(settings);
        return null;
    }
}
