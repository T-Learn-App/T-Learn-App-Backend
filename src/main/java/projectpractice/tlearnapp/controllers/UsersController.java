package projectpractice.tlearnapp.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projectpractice.tlearnapp.dto.responses.GetUserResponse;
import projectpractice.tlearnapp.dto.responses.SetGetWordResponse;
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
            @ApiResponse(description = "invalid request", responseCode = "500")
    })
    public GetUserResponse getUser(@PathVariable("id") Long userId) {
        return usersService.getUser(userId);
    }

    @GetMapping("/statistics/words")
    @ApiResponse(description = "gives learnt user words", responseCode = "200")
    @ApiResponse(description = "user doesn't have learnt words", responseCode = "404")
    public SetGetWordResponse getLearntUserWords(@RequestParam Long userId) {
        return usersService.getLearntUserWords(userId);
    }
}
