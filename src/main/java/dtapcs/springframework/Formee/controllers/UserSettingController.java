package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.entities.UserSettings;
import dtapcs.springframework.Formee.services.inf.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-setting")
@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "#{'${formee.url}'}")
public class UserSettingController {
    @Autowired
    private UserSettingService userSettingService;

    @PutMapping("/notifications")
    public ResponseEntity<String> updateUserSetting(UserSettings settings) {
        return new ResponseEntity<>(userSettingService.updateUserSetting(settings), HttpStatus.OK);
    }
}
