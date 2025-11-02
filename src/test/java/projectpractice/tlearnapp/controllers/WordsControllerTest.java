package projectpractice.tlearnapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import projectpractice.tlearnapp.dto.responses.GetWordResponse;
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

    @Test
    public void getWordReturnOk() throws Exception {

        GetWordResponse response = new GetWordResponse(
                "testWord",
                "testTranscription",
                "testTranslation",
                "testPartOfSpeech");

        when(wordService.getRandomWord()).thenReturn(response);

        mockMvc.perform(get("/words"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("word").value("testWord"))
                .andExpect(jsonPath("transcription").value("testTranscription"))
                .andExpect(jsonPath("translation").value("testTranslation"))
                .andExpect(jsonPath("partOfSpeech").value("testPartOfSpeech"));
    }
}
