//package projectpractice.tlearnapp.controllers;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import projectpractice.tlearnapp.dto.responses.WordResponse;
//import projectpractice.tlearnapp.exceptions.DataNotFoundException;
//import projectpractice.tlearnapp.exceptions.InvalidRequestException;
//import projectpractice.tlearnapp.servicies.WordsService;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//
//@WebMvcTest(controllers = WordsController.class)
//public class WordsControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private WordsService wordService;
//
//    private WordResponse response;
//
//    @BeforeEach
//    void setUp() {
//        response = new WordResponse(
//                "testWord",
//                "testTranscription",
//                "testTranslation",
//                "testPartOfSpeech",
//                1L);
//    }
//
//    @Test
//    public void getWordReturnOk() throws Exception {
//
//        when(wordService.getRandomWords(1L)).thenReturn(response);
//
//        mockMvc.perform(get("/words"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("word").value("testWord"))
//                .andExpect(jsonPath("transcription").value("testTranscription"))
//                .andExpect(jsonPath("translation").value("testTranslation"))
//                .andExpect(jsonPath("partOfSpeech").value("testPartOfSpeech"));
//    }
//
//    @Test
//    public void getWordReturnNotFound() throws Exception {
//
//        when(wordService.getRandomWords()).thenThrow(DataNotFoundException.class);
//
//        mockMvc.perform(get("/words"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("statusCode").value(404))
//                .andExpect(jsonPath("exception").value("DataNotFoundException"))
//                .andExpect(jsonPath("errorMessage").value("Data not found"));
//    }
//
//    @Test
//    public void getWordReturnBadRequest() throws Exception {
//
//        when(wordService.getRandomWords()).thenThrow(InvalidRequestException.class);
//
//        mockMvc.perform(get("/words"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("statusCode").value(400))
//                .andExpect(jsonPath("exception").value("InvalidRequestException"))
//                .andExpect(jsonPath("errorMessage").value("Invalid request"));
//    }
//}
