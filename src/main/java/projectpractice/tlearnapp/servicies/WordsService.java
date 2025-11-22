package projectpractice.tlearnapp.servicies;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectpractice.tlearnapp.dto.responses.ListWordResponse;
import projectpractice.tlearnapp.entities.Word;
import projectpractice.tlearnapp.mappers.WordMapper;
import projectpractice.tlearnapp.repositories.WordsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class WordsService {

    private final WordsRepository wordsRepository;
    private final WordMapper wordMapper;

    public ListWordResponse getRandomWords(Long userId) {
        List<Word> words = wordsRepository.findRandomWords();
        log.info("words were taken successfully");
        ListWordResponse response = new ListWordResponse(userId, new ArrayList<>());
        for (Word word : words) {
            response.words().add(wordMapper.toWordResponse(word));
        }
        return response;
    }
}
