package projectpractice.tlearnapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectpractice.tlearnapp.dto.WordResponse;
import projectpractice.tlearnapp.servicies.WordsService;

@RestController
@RequestMapping("words")
@AllArgsConstructor
@Tag(name = "Words Management", description = "Operations related to words management")
public class WordsController {

    private final WordsService wordsService;

    @GetMapping
    @ApiResponses({
            @ApiResponse(description = "gives an english word", responseCode = "200"),
            @ApiResponse(description = "word wasn't found", responseCode = "404"),
            @ApiResponse(description = "an error occurred", responseCode = "500")      
    })
    @Operation(summary = "Get random word", description = "Retrieves a word from the data base")
    public WordResponse getWord() {
        return wordsService.getRandomWord();
    }
}
