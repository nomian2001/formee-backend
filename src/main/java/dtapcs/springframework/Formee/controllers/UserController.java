package dtapcs.springframework.Formee.controllers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import dtapcs.springframework.Formee.domain.FormeeUser;
import dtapcs.springframework.Formee.repositories.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @PostMapping ("/login")
    FormeeUser VerifyLogin(@RequestHeader(value = "token") String idToken) throws Exception
    {
        System.out.println(idToken);
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get();
        final String uid = decodedToken.getUid();
        Optional<FormeeUser> user = userRepository.findById(UUID.fromString(uid));
        if(user.isPresent())
        {
            return user.get();
        }
        else{
            FormeeUser newUser = new FormeeUser();
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
            newUser.setEmail(userRecord.getEmail());
            newUser.setFullName(userRecord.getDisplayName());
            return userRepository.save(newUser);
        }
    }

}
