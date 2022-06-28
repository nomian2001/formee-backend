package dtapcs.springframework.Formee.controllers;

import dtapcs.springframework.Formee.dtos.model.DataResponse;
import dtapcs.springframework.Formee.dtos.model.FormTemplateFullDTO;
import dtapcs.springframework.Formee.dtos.model.UserDTO;
import dtapcs.springframework.Formee.entities.FormeeUser;
import dtapcs.springframework.Formee.services.inf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
@CrossOrigin(origins = "#{'${formee.url}'}")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @PutMapping("/profile")
    public ResponseEntity updateProfile(@RequestBody UserDTO user) {
        DataResponse response;
        String result = userService.updateProfile(user);
        if (StringUtils.hasText(result)) {
            response = DataResponse.withCode(HttpStatus.ALREADY_REPORTED.value())
                    .withMessage(super.getMessage(result))
                    .withResult(result)
                    .build();
        }
        else {
            response = DataResponse.ok()
                    .withMessage(super.getMessage("message.common.success"))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity getUserProfile(@PathVariable String userId) {
        UserDTO result = userService.getUserProfile(userId);
        DataResponse response = DataResponse.ok()
                .withMessage(super.getMessage("message.common.success"))
                .withResult(result)
                .build();
        return ResponseEntity.ok(response);
    }
}
