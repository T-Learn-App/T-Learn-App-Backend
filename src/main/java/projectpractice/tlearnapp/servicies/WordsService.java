package projectpractice.tlearnapp.servicies;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectpractice.tlearnapp.dto.responses.GetWordResponse;
import projectpractice.tlearnapp.entities.Word;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.mappers.WordMapper;
import projectpractice.tlearnapp.repositories.WordsRepository;

@Service
@AllArgsConstructor
@Slf4j
public class WordsService {

    private final WordsRepository wordsRepository;
    private final WordMapper wordMapper;

    public GetWordResponse getRandomWord() {
        Word word = wordsRepository.findRandomWord().orElseThrow(DataNotFoundException::new);
        log.info("word: {} was taken successfully", word.getWord());
        return wordMapper.toGetWordResponse(word);
    }
}
