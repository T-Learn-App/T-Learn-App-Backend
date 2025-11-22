package projectpractice.tlearnapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import projectpractice.tlearnapp.dto.ListStatsDto;
import projectpractice.tlearnapp.dto.StatQueueDto;
import projectpractice.tlearnapp.dto.StatsDto;
import projectpractice.tlearnapp.enums.StatsStatus;
import projectpractice.tlearnapp.exceptions.InvalidRequestException;
import projectpractice.tlearnapp.servicies.StatsService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
public class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StatsService statsService;

    private StatsDto response;
    private StatQueueDto request;

    @BeforeEach
    void setUp() {
        response = new StatsDto(1L, 2L, 0L, StatsStatus.DRAFT);
        request = new StatQueueDto(1L, 2L);
    }

    @Test
    public void getStatReturnOk() throws Exception {
        ListStatsDto dto = new ListStatsDto(List.of(response));
        when(statsService.getStats(1L)).thenReturn(dto);

        mockMvc.perform(get("/stats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stats[0].userId").value(1))
                .andExpect(jsonPath("$.stats[0].wordId").value(2))
                .andExpect(jsonPath("$.stats[0].status").value(StatsStatus.DRAFT.toString()))
                .andExpect(jsonPath("$.stats[0].attempts").value(0));
    }

    @Test
    public void getStatReturnBadRequest() throws Exception {
        when(statsService.getStats(1L)).thenThrow(InvalidRequestException.class);

        mockMvc.perform(get("/stats/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").value("InvalidRequestException"))
                .andExpect(jsonPath("$.errorMessage").value("Invalid request"));
    }

    @Test
    public void completeWordReturnOk() throws Exception {
        mockMvc.perform(post("/stats/complete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
            {
                "user_id": 1,
                "word_id": 2
            }
        """))
                .andExpect(status().isCreated());

    }

    @Test
    public void completeWordReturnBadRequest() throws Exception {
        doThrow(InvalidRequestException.class)
                .when(statsService)
                .markWordAsCompleted(any(StatQueueDto.class));

        mockMvc.perform(post("/stats/complete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
                "user_id": 1
            }
        """))
                .andExpect(status().isBadRequest());
    }
}
