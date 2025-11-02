package projectpractice.tlearnapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import projectpractice.tlearnapp.common.api.AdviceController;
import projectpractice.tlearnapp.dto.responses.GetWordResponse;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.exceptions.InvalidRequestException;
import projectpractice.tlearnapp.servicies.WordsService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(controllers = WordsController.class)
public class WordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WordsService wordService;

    private GetWordResponse response;

    @BeforeEach
    void setUp() {
        response = new GetWordResponse(
                "testWord",
                "testTranscription",
                "testTranslation",
                "testPartOfSpeech");
    }

    @Test
    public void getWordReturnOk() throws Exception {

        when(wordService.getRandomWord()).thenReturn(response);

        mockMvc.perform(get("/words"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("word").value("testWord"))
                .andExpect(jsonPath("transcription").value("testTranscription"))
                .andExpect(jsonPath("translation").value("testTranslation"))
                .andExpect(jsonPath("partOfSpeech").value("testPartOfSpeech"));
    }

    @Test
    public void getWordReturnNotFound() throws Exception {

        when(wordService.getRandomWord()).thenThrow(DataNotFoundException.class);

        mockMvc.perform(get("/words"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value(404))
                .andExpect(jsonPath("exception").value("DataNotFoundException"))
                .andExpect(jsonPath("errorMessage").value("Data not found"));
    }

    @Test
    public void getWordReturnBadRequest() throws Exception {

        when(wordService.getRandomWord()).thenThrow(InvalidRequestException.class);

        mockMvc.perform(get("/words"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("exception").value("InvalidRequestException"))
                .andExpect(jsonPath("errorMessage").value("Invalid request"));
    }
}
