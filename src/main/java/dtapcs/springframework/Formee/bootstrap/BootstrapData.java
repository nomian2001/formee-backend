package dtapcs.springframework.Formee.bootstrap;

import dtapcs.springframework.Formee.domain.FormeeUser;
import dtapcs.springframework.Formee.firebase.FirebaseService;
import dtapcs.springframework.Formee.repositories.UserHistoryRepository;
import dtapcs.springframework.Formee.repositories.UserRepository;
import dtapcs.springframework.Formee.repositories.UserSettingsRepository;
import org.springframework.boot.CommandLineRunner;

public class BootstrapData implements CommandLineRunner {
    private final UserHistoryRepository userHistoryRepository;
    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;

    public BootstrapData(UserHistoryRepository userHistoryRepository, UserRepository userRepository, UserSettingsRepository userSettingsRepository) {
        this.userHistoryRepository = userHistoryRepository;
        this.userRepository = userRepository;
        this.userSettingsRepository = userSettingsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        FormeeUser Dung = new FormeeUser();
        Dung.setUsername("pthdung");
        Dung.setFullName("Pham Tran Hien Dung");
        Dung.setPassword("Dung123456");
        Dung.setGender(1);
        Dung.setEmail("pthdung@gmail.com");
        Dung.setProfilePicture("XXXXXX");
        userRepository.save(Dung);

        System.out.println(Dung);

        FirebaseService.InitFirebase();

    }
}
