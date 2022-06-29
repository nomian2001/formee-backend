package dtapcs.springframework.Formee.helper;

import dtapcs.springframework.Formee.dtos.model.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonHelper {

    public static UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal.equals("anonymousUser")) {
            return new UserDetails("Ẩn danh");
        }
        return (UserDetails) principal;
    }
}
