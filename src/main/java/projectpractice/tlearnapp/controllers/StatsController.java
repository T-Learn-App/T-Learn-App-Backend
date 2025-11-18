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
import projectpractice.tlearnapp.dto.ListStatsDto;
import projectpractice.tlearnapp.dto.StatQueueDto;
import projectpractice.tlearnapp.servicies.StatsService;

@RestController
@RequestMapping("stats")
@AllArgsConstructor
@Validated
@Tag(name = "Stats Management", description = "Operations related to stats management")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(description = "stats got successfully", responseCode = "200"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    @Operation(summary = "Get all stats of user", description = "Get list of all user stats")
    public ListStatsDto getStat(@PathVariable Long id) {
        return statsService.getStats(id);
    }

    @PostMapping("/complete")
    @ApiResponses({
            @ApiResponse(description = "word marked as completed successfully", responseCode = "200"),
            @ApiResponse(description = "word or user wasn't found", responseCode = "404"),
            @ApiResponse(description = "word can't be send this moment", responseCode = "422"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    @Operation(summary = "Mark word as completed", description = "Send word to stats queue for create data in stats")
    public void completeWord(@RequestBody @Valid StatQueueDto completedWord) {
        statsService.markWordAsCompleted(completedWord);
    }
}
