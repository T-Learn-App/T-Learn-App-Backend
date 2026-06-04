package projectpractice.tlearnapp.dto.responses;

import lombok.Builder;
import lombok.Data;

@Builder
public record AuthResponse(String accessToken, String refreshToken) {}
