package projectpractice.tlearnapp.job;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectpractice.tlearnapp.entities.Stat;
import projectpractice.tlearnapp.entities.StatQueue;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.entities.Word;
import projectpractice.tlearnapp.enums.StatsStatus;
import projectpractice.tlearnapp.jobs.StatQueueJob;
import projectpractice.tlearnapp.repositories.StatQueueRepository;
import projectpractice.tlearnapp.repositories.StatsRepository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatQueueJobTest {

    @Mock
    private StatQueueRepository statQueueRepository;

    @Mock
    private StatsRepository statsRepository;

    @InjectMocks
    private StatQueueJob statQueueJob;

    @Test
    void run_ShouldCreateStat_WhenStatDoesNotExist() {
        User user = mock(User.class);
        Word word = mock(Word.class);
        StatQueue statQueue = mock(StatQueue.class);

        when(user.getId()).thenReturn(1L);
        when(word.getId()).thenReturn(10L);

        when(statQueue.getUser()).thenReturn(user);
        when(statQueue.getWord()).thenReturn(word);

        when(statQueueRepository.findByStatusLocked(
                anyInt(),
                eq(StatQueue.Status.ACCEPTED)))
                .thenReturn(List.of(statQueue));

        when(statsRepository.findByUserIdAndWordId(1L, 10L))
                .thenReturn(Optional.empty());

        statQueueJob.run(10, 1, Duration.ofMinutes(1));

        verify(statsRepository).save(any(Stat.class));
        verify(statsRepository, never())
                .updateAttemptsAndStatusByUserIdAndWordId(any(), any(), any());
    }

    @Test
    void run_ShouldUpdateStat_WhenStatExists() {
        User user = mock(User.class);
        Word word = mock(Word.class);
        StatQueue statQueue = mock(StatQueue.class);
        Stat stat = mock(Stat.class);

        when(user.getId()).thenReturn(1L);
        when(word.getId()).thenReturn(10L);

        when(statQueue.getUser()).thenReturn(user);
        when(statQueue.getWord()).thenReturn(word);

        when(stat.getAttempts()).thenReturn(3L);

        when(statQueueRepository.findByStatusLocked(
                anyInt(),
                eq(StatQueue.Status.ACCEPTED)))
                .thenReturn(List.of(statQueue));

        when(statsRepository.findByUserIdAndWordId(1L, 10L))
                .thenReturn(Optional.of(stat));

        statQueueJob.run(10, 1, Duration.ofMinutes(1));

        verify(statsRepository)
                .updateAttemptsAndStatusByUserIdAndWordId(
                        eq(1L),
                        eq(10L),
                        any(StatsStatus.class)
                );

        verify(statsRepository, never()).save(any(Stat.class));
    }
}
