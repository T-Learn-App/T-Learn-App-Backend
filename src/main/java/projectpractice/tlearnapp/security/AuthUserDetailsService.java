package projectpractice.tlearnapp.security;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserProperties userProperties;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username) ||
                !username.equals(userProperties.getEmail())) {
            throw new UsernameNotFoundException(
                    String.format("User not found, or unauthorized %s", username));
        }

        return new User(userProperties.getEmail(),
                userProperties.getPassword(), new ArrayList<>());
    }
}
