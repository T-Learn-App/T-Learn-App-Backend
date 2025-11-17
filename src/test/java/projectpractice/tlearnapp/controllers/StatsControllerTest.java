package projectpractice.tlearnapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import projectpractice.tlearnapp.dto.ListStatsDto;
import projectpractice.tlearnapp.dto.StatsDto;
import projectpractice.tlearnapp.enums.StatsStatus;
import projectpractice.tlearnapp.servicies.StatsService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
public class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StatsService statsService;

    private StatsDto response;

    @BeforeEach
    void setUp() {
        response = new StatsDto(1L, 2L, 0L, StatsStatus.DRAFT);
    }

    @Test
    public void getStatReturnOk() throws Exception {
        ListStatsDto dto = new ListStatsDto(List.of(response));
        when(statsService.getStats(1L)).thenReturn(dto);

        mockMvc.perform(get("/stats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stats").value(dto));
    }
}
