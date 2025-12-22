package projectpractice.tlearnapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectpractice.tlearnapp.dto.ListStatsDto;
import projectpractice.tlearnapp.dto.StatQueueDto;
import projectpractice.tlearnapp.dto.StatsDto;
import projectpractice.tlearnapp.security.AuthenticatedUserDetails;
import projectpractice.tlearnapp.servicies.StatsService;

@RestController
@RequestMapping("stats")
@AllArgsConstructor
@Validated
@Tag(name = "Stats Management", description = "Operations related to stats management")
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    @ApiResponses({
            @ApiResponse(description = "stats got successfully", responseCode = "200"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    @Operation(summary = "Get all stats of user", description = "Get list of all user stats")
    public ListStatsDto getStat(@RequestHeader(name = "Authorization") String accessToken) {
        return statsService.getStats(accessToken);
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(description = "stats got successfully", responseCode = "200"),
            @ApiResponse(description = "days cannot be negative", responseCode = "400"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    @Operation(summary = "Get all stats of user by filters", description = "Get list of all user stats in the last days")
    public ListStatsDto getStatsByFilters(@RequestHeader(name = "Authorization") String accessToken,
                                          @RequestBody StatsDto statsDto) {
        return statsService.getStatsByLastDays(accessToken, statsDto);
    }

    @PutMapping("/complete")
    @ApiResponses({
            @ApiResponse(description = "word marked as completed successfully", responseCode = "200"),
            @ApiResponse(description = "word or user wasn't found", responseCode = "404"),
            @ApiResponse(description = "word can't be send this moment", responseCode = "422"),
            @ApiResponse(description = "an error occurred", responseCode = "500")
    })
    @Operation(summary = "Mark word as completed", description = "Send word to stats queue for create data in stats")
    public void completeWord(@RequestHeader(name = "Authorization") String accessToken,
                             @RequestBody @Valid StatQueueDto completedWord) {
        statsService.markWordAsCompleted(accessToken, completedWord);
    }
}
