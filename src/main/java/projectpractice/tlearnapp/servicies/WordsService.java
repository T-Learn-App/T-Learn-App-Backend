package projectpractice.tlearnapp.servicies;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectpractice.tlearnapp.dto.responses.GetWordResponse;
import projectpractice.tlearnapp.repositories.WordsRepository;
import projectpractice.tlearnapp.repositories.entities.Word;

import java.util.Random;

@Service
@AllArgsConstructor
public class WordsService {

    private WordsRepository wordsRepository;

    public GetWordResponse getWord() {
        Word word = wordsRepository.findRandomWord();
        return new GetWordResponse(
                word.getWord(),
                word.getTranscription(),
                word.getTranslation(),
                word.getPartOfSpeech());
    }
}
