package projectpractice.tlearnapp.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import projectpractice.tlearnapp.TestConfig;
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
                null,
                "testWord",
                "testTranscription",
                "testTranslation",
                "testPartOfSpeech");
        wordsRepository.save(word);

        Optional<Word> wordOptional = wordsRepository.findRandomWord();

        assertThat(wordOptional).isPresent();
        assertThat(wordOptional.get().getWord()).isEqualTo(word.getWord());
    }
}


