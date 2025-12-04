package projectpractice.tlearnapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import projectpractice.tlearnapp.dto.UserDto;
import projectpractice.tlearnapp.servicies.UsersService;

@RestController
@RequestMapping("users")
@AllArgsConstructor
@Validated
@Tag(name = "Users Management", description = "Operations related to users management")
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(description = "gives user info", responseCode = "200"),
            @ApiResponse(description = "user not found", responseCode = "404"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    @Operation(summary = "Get user by id", description = "Retrieves a user by their unique identifier")
    public UserDto getUser(@PathVariable("id") @Valid Long userId) {
        return usersService.getUser(userId);
    }
}
