package dtapcs.springframework.Formee.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import dtapcs.springframework.Formee.entities.FormeeUser;
import dtapcs.springframework.Formee.repositories.inf.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/authentication")
public class LoginController {
    @Autowired
    private UserRepo userRepo;

    @CrossOrigin
    @PostMapping("/login")
    FormeeUser VerifyLogin(@RequestHeader(value = "token") String idToken) throws Exception {
        System.out.println(idToken);
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get();
        final String uid = decodedToken.getUid();
        Optional<FormeeUser> user = userRepo.findById(uid);
        if (user.isPresent()) {
            return user.get();
        } else {
            FormeeUser newUser = new FormeeUser(uid);
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
            newUser.setEmail(userRecord.getEmail());
            newUser.setFullName(userRecord.getDisplayName());
            newUser.setProfilePicture(userRecord.getPhotoUrl());
            newUser.setUsername("Formee user");
            return userRepo.save(newUser);
        }
    }

}
