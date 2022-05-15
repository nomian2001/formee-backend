package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.entities.FormeeUser;
import dtapcs.springframework.Formee.services.inf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<String> updateProfile(FormeeUser user) {
        return new ResponseEntity<>(
                userService.updateProfile(user), HttpStatus.OK);
    }

}
