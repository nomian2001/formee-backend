package dtapcs.springframework.Formee.configuration;

import dtapcs.springframework.Formee.dtos.model.UserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class SpringAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (principal.equals("anonymousUser")) {
            return Optional.ofNullable(principal.toString());
        }
        else {
            UserDetails userDetails = (UserDetails) principal;
            return Optional.ofNullable(userDetails.getUsername());
        }
    }
}
