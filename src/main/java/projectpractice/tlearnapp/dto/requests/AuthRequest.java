package projectpractice.tlearnapp.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthRequest {

    @Email
    private String email;

    @Valid
    private String password;

    private String accessToken;

    private String refreshToken;
}
