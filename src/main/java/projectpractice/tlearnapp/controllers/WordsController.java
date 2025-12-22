package projectpractice.tlearnapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectpractice.tlearnapp.dto.responses.ListWordResponse;
import projectpractice.tlearnapp.security.AuthenticatedUserDetails;
import projectpractice.tlearnapp.security.JwtTokenProvider;
import projectpractice.tlearnapp.servicies.WordsService;

@RestController
@RequestMapping("words")
@Validated
@AllArgsConstructor
@Tag(name = "Words Management", description = "Operations related to words management")
public class WordsController {

    private final WordsService wordsService;

    @GetMapping
    @ApiResponses({
            @ApiResponse(description = "gives english words", responseCode = "200"),
            @ApiResponse(description = "words weren't found", responseCode = "404"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    @Operation(summary = "Get random words", description = "Retrieves several words from the data base")
    public ListWordResponse getWords(@RequestHeader(name = "Authorization") String accessToken) {
        return wordsService.getRandomWords(accessToken);
    }

    @GetMapping("/categories/{categoryId}")
    @ApiResponses({
            @ApiResponse(description = "gives english words", responseCode = "200"),
            @ApiResponse(description = "words weren't found", responseCode = "404"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    @Operation(summary = "Get random words by category", description = "Retrieves several words by category from the data base")
    public ListWordResponse getWordsByCategoryId(@RequestHeader(name = "Authorization") String accessToken,
                                                 @PathVariable @Valid Long categoryId) {
        return wordsService.getRandomWordsByCategory(accessToken, categoryId);
    }
}
