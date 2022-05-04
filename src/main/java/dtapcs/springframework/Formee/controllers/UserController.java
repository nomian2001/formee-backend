package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.domain.FormeeUser;
import dtapcs.springframework.Formee.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/user/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PutMapping
    public ResponseEntity<String> updateProfile ( FormeeUser user){
        return new ResponseEntity<>(
                userService.updateProfile(user), HttpStatus.OK);
    }

}
