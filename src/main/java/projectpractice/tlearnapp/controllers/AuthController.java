package projectpractice.tlearnapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projectpractice.tlearnapp.dto.requests.AuthRequest;
import projectpractice.tlearnapp.dto.responses.AuthResponse;
import projectpractice.tlearnapp.servicies.AuthService;

@RestController("auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/token/create")
    public AuthResponse createToken(@RequestBody @Valid AuthRequest request) {
        return authService.generateToken(request);
    }
}
