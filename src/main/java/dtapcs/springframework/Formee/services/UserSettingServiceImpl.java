package dtapcs.springframework.Formee.services;

import dtapcs.springframework.Formee.domain.UserSettings;
import dtapcs.springframework.Formee.repositories.UserSettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSettingServiceImpl implements UserSettingService {
    private final UserSettingsRepository userSettingsRepository;

    public UserSettingServiceImpl(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    @Override
    public String updateUserSetting(UserSettings settings) {
        userSettingsRepository.findById(settings.getUuid()).get().UpdateSettings(settings);
        return null;
    }
}
