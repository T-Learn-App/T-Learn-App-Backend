package projectpractice.tlearnapp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectpractice.tlearnapp.dto.UserDto;
import projectpractice.tlearnapp.servicies.UsersService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;

    @Test
    void getUser_ShouldReturnUser_WhenTokenIsValid() {
        String accessToken = "Bearer valid-token";

        UserDto expectedUser = new UserDto(1L, "email", "password");

        when(usersService.getUser(accessToken))
                .thenReturn(expectedUser);

        UserDto actualUser = usersController.getUser(accessToken);

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);

        verify(usersService).getUser(accessToken);
    }

    @Test
    void getUser_ShouldThrowException_WhenUserNotFound() {
        String accessToken = "Bearer invalid-token";

        when(usersService.getUser(accessToken))
                .thenThrow(new RuntimeException("User not found"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usersController.getUser(accessToken)
        );

        assertEquals("User not found", exception.getMessage());

        verify(usersService).getUser(accessToken);
    }
}