package dtapcs.springframework.Formee.bootstrap;

import dtapcs.springframework.Formee.repositories.inf.UserHistoryRepo;
import dtapcs.springframework.Formee.repositories.inf.UserRepo;
import dtapcs.springframework.Formee.repositories.inf.UserSettingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {
    @Autowired
    private UserHistoryRepo userHistoryRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserSettingsRepo userSettingsRepo;

    @Override
    public void run(String... args) throws Exception {
//        FormeeUser Dung = new FormeeUser();
//        Dung.setUsername("pthdung");
//        Dung.setFullName("Pham Tran Hien Dung");
//        Dung.setPassword("Dung123456");
//        Dung.setGender(1);
//        Dung.setEmail("pthdung@gmail.com");
//        Dung.setProfilePicture("XXXXXX");
//        userRepository.save(Dung);

        //System.out.println(Dung);
    }
}
