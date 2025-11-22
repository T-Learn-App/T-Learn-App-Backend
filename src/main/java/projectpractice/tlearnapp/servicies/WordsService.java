package projectpractice.tlearnapp.servicies;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.dto.responses.ListWordResponse;
import projectpractice.tlearnapp.entities.Stat;
import projectpractice.tlearnapp.entities.Word;
import projectpractice.tlearnapp.mappers.WordMapper;
import projectpractice.tlearnapp.repositories.StatsRepository;
import projectpractice.tlearnapp.repositories.WordsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class WordsService {

    private final WordsRepository wordsRepository;
    private final StatsRepository statsRepository;
    private final WordMapper wordMapper;

    @Transactional
    public ListWordResponse getRandomWords(Long userId) {
        List<Word> words = wordsRepository.findRandomWords();
        log.info("words were taken successfully");
        ListWordResponse response = new ListWordResponse(userId, new ArrayList<>());

        // make the rotation
        List<Stat> rotation = statsRepository.findRotationStatsByUserId(userId);

        for (Word word : words) {
            response.words().add(wordMapper.toWordResponse(word));
        }
        for (Stat stat : rotation) {
            Word word = wordsRepository.findById(stat.getWord().getId()).orElse(null);
            response.words().add(wordMapper.toWordResponse(word));
        }
        return response;
    }
}
