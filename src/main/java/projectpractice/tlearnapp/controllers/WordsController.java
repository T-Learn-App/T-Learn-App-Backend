package projectpractice.tlearnapp.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectpractice.tlearnapp.dto.responses.GetWordResponse;
import projectpractice.tlearnapp.servicies.WordsService;

@RestController
@RequestMapping("words")
@AllArgsConstructor
public class WordsController {

    private final WordsService wordsService;

    @GetMapping
    @ApiResponse(description = "gives an english word", responseCode = "200")
    @ApiResponse(description = "an error occurred", responseCode = "500")
    public GetWordResponse getWord() {
        return wordsService.getWord();
    }
}
