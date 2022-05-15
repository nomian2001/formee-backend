package dtapcs.springframework.Formee.dtos.model;

import dtapcs.springframework.Formee.entities.FormeeUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDetails {
    private String id;
    private Collection<? extends GrantedAuthority> authorities;
    public static UserDetails build(FormeeUser user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetails(
                user.getUuid(),
                authorities);
    }
}
