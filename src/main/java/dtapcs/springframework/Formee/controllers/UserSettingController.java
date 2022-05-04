package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.domain.UserSettings;
import dtapcs.springframework.Formee.services.UserSettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/user-setting/")
public class UserSettingController {
    private final UserSettingService userSettingService;

    public UserSettingController(UserSettingService userSettingService) {
        this.userSettingService = userSettingService;
    }
    @PutMapping("notifications")
    public ResponseEntity<String> updateUserSetting(UserSettings settings)
    {
        return new ResponseEntity<>(userSettingService.updateUserSetting(settings), HttpStatus.OK);
    }
}
