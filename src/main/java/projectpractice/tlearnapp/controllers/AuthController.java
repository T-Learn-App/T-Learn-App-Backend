package projectpractice.tlearnapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth Management", description = "Operations related to authentication management")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(description = "user login or sign up successfully", responseCode = "200"),
            @ApiResponse(description = "invalid password was sent", responseCode = "401"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    @Operation(summary = "Logins or signs up a user", description = "Adds a user to data base and gives him refresh and access jwt token")
    public AuthResponse login(@RequestBody @Valid AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/token/refresh")
    @ApiResponses({
            @ApiResponse(description = "user got token successfully", responseCode = "200"),
            @ApiResponse(description = "invalid refresh token", responseCode = "401"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    public AuthResponse refreshToken(@RequestBody @Valid AuthRequest request) {
        return authService.generateAccessToken(request);
    }
}
