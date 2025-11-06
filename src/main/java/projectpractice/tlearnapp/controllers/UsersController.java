package projectpractice.tlearnapp.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectpractice.tlearnapp.dto.responses.UserResponse;
import projectpractice.tlearnapp.servicies.UsersService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(description = "gives user info", responseCode = "200"),
            @ApiResponse(description = "user not found", responseCode = "404"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    public UserResponse getUser(@PathVariable("id") @Valid Long userId) {
        return usersService.getUser(userId);
    }

    @PostMapping("/registration")
    @ApiResponses(value = {
            @ApiResponse(description = "user was added successfully", responseCode = "201"),
            @ApiResponse(description = "invalid request", responseCode = "400"),
            @ApiResponse(description = "user already exists", responseCode = "409"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    public UserResponse addUser(@RequestBody @Valid @Email String email) {
        return usersService.addUser(email);
    }
}
