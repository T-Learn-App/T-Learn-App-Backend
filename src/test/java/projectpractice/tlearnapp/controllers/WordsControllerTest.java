package projectpractice.tlearnapp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectpractice.tlearnapp.dto.responses.ListWordResponse;
import projectpractice.tlearnapp.servicies.WordsService;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordsControllerTest {

    @Mock
    private WordsService wordsService;

    @InjectMocks
    private WordsController wordsController;

    @Test
    void getWords_ShouldReturnWords_WhenRequestIsValid() {
        String accessToken = "Bearer token";
        ListWordResponse expectedResponse = new ListWordResponse(1L, listOf());

        when(wordsService.getRandomWords(accessToken))
                .thenReturn(expectedResponse);

        ListWordResponse actualResponse =
                wordsController.getWords(accessToken);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(wordsService).getRandomWords(accessToken);
    }

    @Test
    void getWords_ShouldThrowException_WhenWordsNotFound() {
        String accessToken = "Bearer token";

        when(wordsService.getRandomWords(accessToken))
                .thenThrow(new RuntimeException("Words not found"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> wordsController.getWords(accessToken)
        );

        assertEquals("Words not found", exception.getMessage());

        verify(wordsService).getRandomWords(accessToken);
    }

    @Test
    void getWordsByCategoryId_ShouldReturnWords_WhenCategoryExists() {
        String accessToken = "Bearer token";
        Long categoryId = 1L;

        ListWordResponse expectedResponse = new ListWordResponse(1L, listOf());

        when(wordsService.getRandomWordsByCategory(accessToken, categoryId))
                .thenReturn(expectedResponse);

        ListWordResponse actualResponse =
                wordsController.getWordsByCategoryId(accessToken, categoryId);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(wordsService)
                .getRandomWordsByCategory(accessToken, categoryId);
    }

    @Test
    void getWordsByCategoryId_ShouldThrowException_WhenCategoryNotFound() {
        String accessToken = "Bearer token";
        Long categoryId = 999L;

        when(wordsService.getRandomWordsByCategory(accessToken, categoryId))
                .thenThrow(new RuntimeException("Category not found"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> wordsController.getWordsByCategoryId(accessToken, categoryId)
        );

        assertEquals("Category not found", exception.getMessage());

        verify(wordsService)
                .getRandomWordsByCategory(accessToken, categoryId);
    }
}