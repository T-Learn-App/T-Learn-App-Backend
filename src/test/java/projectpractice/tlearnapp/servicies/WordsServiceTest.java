package projectpractice.tlearnapp.servicies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectpractice.tlearnapp.dto.responses.GetWordResponse;
import projectpractice.tlearnapp.entities.Word;
import projectpractice.tlearnapp.mappers.WordMapper;
import projectpractice.tlearnapp.repositories.WordsRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for WordService")
public class WordsServiceTest {

    @Mock
    private WordsRepository wordsRepository;
    @Mock
    private WordMapper wordMapper;
    @InjectMocks
    private WordsService wordsService;

    @Test
    public void shouldGetWordSuccessfully() {
        Optional<Word> word = Optional.of(new Word(
                1L,
                "testWord",
                "testTranscription",
                "testTranslation",
                "testPartOfSpeech"));
        GetWordResponse getWordResponse = new GetWordResponse(
                "testWord",
                "testTranscription",
                "testTranslation",
                "testPartOfSpeech");
        when(wordsRepository.findRandomWord()).thenReturn(word);
        when(wordMapper.toGetWordResponse(word.get())).thenReturn(getWordResponse);

        GetWordResponse response = wordsService.getRandomWord();

        assertThat(response.word()).isEqualTo("testWord");
        assertThat(response.transcription()).isEqualTo("testTranscription");
        assertThat(response.translation()).isEqualTo("testTranslation");
        assertThat(response.partOfSpeech()).isEqualTo("testPartOfSpeech");
        verify(wordsRepository, times(1)).findRandomWord();
    }
}
