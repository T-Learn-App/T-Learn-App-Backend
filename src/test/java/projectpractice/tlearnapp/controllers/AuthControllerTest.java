package projectpractice.tlearnapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectpractice.tlearnapp.dto.requests.AuthRequest;
import projectpractice.tlearnapp.dto.responses.AuthResponse;
import projectpractice.tlearnapp.servicies.AuthService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_ShouldReturnResponse() {
        AuthRequest request = AuthRequest.builder()
                .email("test@mail.com")
                .password("Password1!")
                .build();

        AuthResponse expected = new AuthResponse("access", "refresh");

        when(authService.registerOrLogin(request))
                .thenReturn(expected);

        AuthResponse actual = authController.login(request);

        assertEquals(expected, actual);
    }
}