package dtapcs.springframework.Formee.configuration;

import dtapcs.springframework.Formee.entities.Role;
import dtapcs.springframework.Formee.enums.ERole;
import dtapcs.springframework.Formee.repositories.inf.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupEvent implements ApplicationRunner {
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role role = roleRepo.findByName(ERole.ROLE_USER).orElse(null);
        if (role == null) {
            Role userRole = new Role(ERole.ROLE_USER);
            roleRepo.save(userRole);
        }
    }
}
