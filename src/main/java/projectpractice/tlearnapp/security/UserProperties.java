package projectpractice.tlearnapp.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.user")
@Data
public class UserProperties {
    private String email;
    private String password;
}
