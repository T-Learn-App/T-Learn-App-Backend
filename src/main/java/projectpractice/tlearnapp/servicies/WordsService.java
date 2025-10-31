package projectpractice.tlearnapp.servicies;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import projectpractice.tlearnapp.dto.responses.GetWordResponse;
import projectpractice.tlearnapp.exceptions.GetWordException;
import projectpractice.tlearnapp.repositories.WordsRepository;
import projectpractice.tlearnapp.repositories.entities.Word;

@Service
@AllArgsConstructor
public class WordsService {

    private WordsRepository wordsRepository;

    public GetWordResponse getWord() {
        try {
            Word word = wordsRepository.findRandomWord();
            return new GetWordResponse(
                    word.getWord(),
                    word.getTranscription(),
                    word.getTranslation(),
                    word.getPartOfSpeech());
        } catch (Exception ex) {
            throw new GetWordException(ex.getMessage());
        }
    }
}
