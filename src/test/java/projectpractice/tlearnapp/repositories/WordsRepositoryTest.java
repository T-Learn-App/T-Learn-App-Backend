package projectpractice.tlearnapp.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.TestConfig;
import projectpractice.tlearnapp.entities.Category;
import projectpractice.tlearnapp.entities.Word;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
public class WordsRepositoryTest {

    @Autowired
    private WordsRepository wordsRepository;

    @Test
    @Transactional
    public void getWordSuccessfully() {
        Word word = new Word(
                "testWord",
                "testTranscription",
                "testTranslation",
                "testPartOfSpeech",
                new Category());
        wordsRepository.save(word);

        Optional<Word> wordOptional = wordsRepository.findRandomWords();

        assertThat(wordOptional).isPresent();
        assertThat(wordOptional.get().getWord()).isEqualTo(word.getWord());
    }
}


