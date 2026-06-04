package projectpractice.tlearnapp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectpractice.tlearnapp.dto.ListStatsDto;
import projectpractice.tlearnapp.dto.StatQueueDto;
import projectpractice.tlearnapp.dto.StatsDto;
import projectpractice.tlearnapp.enums.StatsStatus;
import projectpractice.tlearnapp.servicies.StatsService;

import static java.util.Collections.emptyList;
import static org.hibernate.internal.util.collections.CollectionHelper.listOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsControllerTest {

    @Mock
    private StatsService statsService;

    @InjectMocks
    private StatsController statsController;

    @Test
    void getStat_ShouldReturnStats_WhenTokenIsValid() {
        String token = "Bearer token";
        ListStatsDto expected = new ListStatsDto(emptyList());

        when(statsService.getStats(token)).thenReturn(expected);

        ListStatsDto actual = statsController.getStat(token);

        assertEquals(expected, actual);
        verify(statsService).getStats(token);
    }

    @Test
    void getStat_ShouldThrowException_WhenServiceFails() {
        String token = "Bearer token";

        when(statsService.getStats(token))
                .thenThrow(new RuntimeException("Error"));

        assertThrows(
                RuntimeException.class,
                () -> statsController.getStat(token)
        );

        verify(statsService).getStats(token);
    }

    @Test
    void getStatsByFilters_ShouldReturnFilteredStats() {
        String token = "Bearer token";
        StatsDto statsDto = new StatsDto(1L, 1L, 1L, StatsStatus.COMPLETED, 1L);
        ListStatsDto expected = new ListStatsDto(listOf(statsDto));

        when(statsService.getStatsByLastDays(token, statsDto))
                .thenReturn(expected);

        ListStatsDto actual =
                statsController.getStatsByFilters(token, statsDto);

        assertEquals(expected, actual);
        verify(statsService).getStatsByLastDays(token, statsDto);
    }

    @Test
    void getStatsByFilters_ShouldThrowException_WhenServiceFails() {
        String token = "Bearer token";
        StatsDto statsDto = new StatsDto(1L, 1L, 1L, StatsStatus.COMPLETED, 1L);

        when(statsService.getStatsByLastDays(token, statsDto))
                .thenThrow(new RuntimeException("Invalid days"));

        assertThrows(
                RuntimeException.class,
                () -> statsController.getStatsByFilters(token, statsDto)
        );

        verify(statsService).getStatsByLastDays(token, statsDto);
    }

    @Test
    void completeWord_ShouldCallService_WhenDataIsValid() {
        String token = "Bearer token";
        StatQueueDto dto = new StatQueueDto(1L);

        doNothing().when(statsService)
                .markWordAsCompleted(token, dto);

        assertDoesNotThrow(() ->
                statsController.completeWord(token, dto));

        verify(statsService).markWordAsCompleted(token, dto);
    }

    @Test
    void completeWord_ShouldThrowException_WhenServiceFails() {
        String token = "Bearer token";
        StatQueueDto dto = new StatQueueDto(1L);

        doThrow(new RuntimeException("Word not found"))
                .when(statsService)
                .markWordAsCompleted(token, dto);

        assertThrows(
                RuntimeException.class,
                () -> statsController.completeWord(token, dto)
        );

        verify(statsService).markWordAsCompleted(token, dto);
    }
}