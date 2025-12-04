package projectpractice.tlearnapp.dto.requests;

import jakarta.validation.Valid;

public record AuthRequest(String email, @Valid String password) {
}
