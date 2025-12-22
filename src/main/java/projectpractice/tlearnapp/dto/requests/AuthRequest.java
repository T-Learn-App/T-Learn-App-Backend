package projectpractice.tlearnapp.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthRequest {

    @Email
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "minimum size is 8 symbols, owercase and uppercase letters, numbers, and special characters"
    )
    private String password;

    private String accessToken;

    private String refreshToken;
}
