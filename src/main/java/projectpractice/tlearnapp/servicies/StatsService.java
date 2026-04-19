package projectpractice.tlearnapp.servicies;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.dto.ListStatsDto;
import projectpractice.tlearnapp.dto.StatQueueDto;
import projectpractice.tlearnapp.dto.StatsDto;
import projectpractice.tlearnapp.entities.LeaderBoard;
import projectpractice.tlearnapp.entities.Stat;
import projectpractice.tlearnapp.entities.StatQueue;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.entities.Word;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.exceptions.InvalidRequestException;
import projectpractice.tlearnapp.mappers.StatsMapper;
import projectpractice.tlearnapp.repositories.LeaderBoardRepository;
import projectpractice.tlearnapp.repositories.StatQueueRepository;
import projectpractice.tlearnapp.repositories.StatsRepository;
import projectpractice.tlearnapp.repositories.UsersRepository;
import projectpractice.tlearnapp.repositories.WordsRepository;
import projectpractice.tlearnapp.security.JwtTokenProvider;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class StatsService {

    private final StatsRepository statsRepository;
    private final StatQueueRepository statQueueRepository;
    private final UsersRepository usersRepository;
    private final WordsRepository wordsRepository;
    private final StatsMapper statsMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final LeaderBoardRepository leaderBoardRepository;

    public ListStatsDto getStats(String accessToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(accessToken);
        List<Stat> userStat = statsRepository.findAllByUserId(userId);
        return makeListStatsDtoFromStat(userStat);
    }

    public ListStatsDto getStatsByLastDays(String accessToken, StatsDto statsDto) {
        Long userId = jwtTokenProvider.getUserIdFromToken(accessToken);
        if (statsDto.getLastDays() < 0) {
            throw new InvalidRequestException("Days cannot be negative");
        }
        List<Stat> userStat = statsRepository.findAllByUserIdAndLastDays(userId, statsDto.getLastDays());
        return makeListStatsDtoFromStat(userStat);
    }

    // add to current user his current total score + 10 if user exists or add new user if user is not exists
    @Transactional
    public void markWordAsCompleted(String accessToken, StatQueueDto statQueueDto) {
        Long userId = jwtTokenProvider.getUserIdFromToken(accessToken);
        Word word = wordsRepository.findById(statQueueDto.wordId()).orElseThrow(DataNotFoundException::new);
        User user = usersRepository.findById(userId).orElseThrow(DataNotFoundException::new);
        try {
            leaderBoardRepository.findByUserId(userId).orElseThrow(DataNotFoundException::new);
            leaderBoardRepository.updateLeaderBoardTotalScoreByUserId(userId, 10L);
        } catch (DataNotFoundException e) {
            leaderBoardRepository.save(LeaderBoard.builder().user(user).seasonId(1L).totalScore(10L).build());
        }

        try {
            statQueueRepository.save(StatQueue.builder().user(user).word(word).status(StatQueue.Status.ACCEPTED).build());
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            statQueueRepository.save(
                    StatQueue.builder().user(user).word(word).status(StatQueue.Status.ERROR).error(errorMessage).build());
            log.error("error during statQueue saving: {}", e.getClass().getSimpleName());
        }
        log.info("word: {} was sent successfully for user: {}", word.getWord(), user.getEmail());
    }

    private ListStatsDto makeListStatsDtoFromStat(List<Stat> stats) {
        ListStatsDto listStatsDto = new ListStatsDto(new ArrayList<>());
        for (Stat stat : stats) {
            listStatsDto.stats().add(statsMapper.toStatsDto(stat));
            log.info("stat for word: {} mapped successfully", stat.getWord().getWord());
        }
        return listStatsDto;
    }
}
