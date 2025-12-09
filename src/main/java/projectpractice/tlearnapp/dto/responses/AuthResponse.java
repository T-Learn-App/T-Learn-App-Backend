package projectpractice.tlearnapp.dto.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    private final String accessToken;
    private final String refreshToken;
}
