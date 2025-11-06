package projectpractice.tlearnapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import projectpractice.tlearnapp.common.api.UsersAdviceController;
import projectpractice.tlearnapp.dto.UserDto;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.exceptions.InvalidRequestException;
import projectpractice.tlearnapp.exceptions.UserAlreadyExistsException;
import projectpractice.tlearnapp.servicies.UsersService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;

    private UserDto response;

    @BeforeEach
    void setUp() {
        response = new UserDto(1L, "pasha@mail.ru");
    }

    @Test
    public void getUserReturnOk() throws Exception {
        when(usersService.getUser(1L)).thenReturn(response);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("pasha@mail.ru"));
    }

    @Test
    public void getUserReturnNotFound() throws Exception {
        when(usersService.getUser(1L)).thenThrow(DataNotFoundException.class);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("DataNotFoundException"))
                .andExpect(jsonPath("$.errorMessage").value("Data not found"));
    }

    @Test
    public void getUserReturnBadRequest() throws Exception {
        when(usersService.getUser(1L)).thenThrow(InvalidRequestException.class);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").value("InvalidRequestException"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid request"));
    }

    @Test
    public void addUserReturnCreated() throws Exception {
        when(usersService.addUser(response.email())).thenReturn(response);

        mockMvc.perform(post("/users/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
                "email": "pasha@mail.ru"
            }
        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("pasha@mail.ru"));
    }

    @Test
    public void addUserReturnBadRequest() throws Exception {
        when(usersService.addUser("invalid email")).thenThrow(InvalidRequestException.class);

        mockMvc.perform(post("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
            {
                "email": "invalid email"
            }
        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").value("InvalidRequestException"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid request"));
    }

    @Test
    public void addUserReturnConflict() throws Exception {
        when(usersService.addUser(response.email())).thenThrow(UserAlreadyExistsException.class);

        mockMvc.perform(post("/users/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
                "email": "pasha@mail.ru"
            }
        """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.exception").value("UserAlreadyExistsException"))
                .andExpect(jsonPath("$.errorMessage").value(
                        "The user already exists"));
    }
}
