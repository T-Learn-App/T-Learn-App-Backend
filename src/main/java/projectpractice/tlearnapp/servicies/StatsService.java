package projectpractice.tlearnapp.servicies;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.dto.ListStatsDto;
import projectpractice.tlearnapp.dto.StatQueueDto;
import projectpractice.tlearnapp.entities.Stat;
import projectpractice.tlearnapp.entities.StatQueue;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.entities.Word;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.mappers.StatsMapper;
import projectpractice.tlearnapp.repositories.StatQueueRepository;
import projectpractice.tlearnapp.repositories.StatsRepository;
import projectpractice.tlearnapp.repositories.UsersRepository;
import projectpractice.tlearnapp.repositories.WordsRepository;

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

    public ListStatsDto getStats(Long userId) {
        List<Stat> userStat = statsRepository.findAllByUserId(userId);
        ListStatsDto listStatsDto = new ListStatsDto(new ArrayList<>());
        for (Stat stat : userStat) {
            listStatsDto.stats().add(statsMapper.toStatsDto(stat));
            log.info("stat of user: {} for word: {} mapped successfully", userId, stat.getWord().getWord());
        }
        return listStatsDto;
    }

    @Transactional
    public void markWordAsCompleted(Long userId, StatQueueDto statQueueDto) {
        Word word = wordsRepository.findById(statQueueDto.wordId()).orElseThrow(DataNotFoundException::new);
        User user = usersRepository.findById(userId).orElseThrow(DataNotFoundException::new);
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
}
