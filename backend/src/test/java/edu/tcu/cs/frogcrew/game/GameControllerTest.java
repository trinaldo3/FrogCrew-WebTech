package edu.tcu.cs.frogcrew.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
@AutoConfigureMockMvc(addFilters = false)
class GameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GameService gameService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetAllGamesReturnsCorrectResponse() throws Exception {
        // Arrange
        GameDTO game1 = new GameDTO();
        game1.setGameId(1L);
        game1.setScheduleId(1L);
        game1.setGameDate(LocalDate.of(2024, 9, 7));
        game1.setVenue("Carter");
        game1.setOpponent("LIU");
        game1.setIsFinalized(false);

        GameDTO game2 = new GameDTO();
        game2.setGameId(2L);
        game2.setScheduleId(1L);
        game2.setGameDate(LocalDate.of(2024, 9, 14));
        game2.setVenue("Carter");
        game2.setOpponent("UCF");
        game2.setIsFinalized(false);

        when(gameService.getAllGameDTOs()).thenReturn(List.of(game1, game2));

        // Act & Assert
        mockMvc.perform(get("/gameSchedule/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].gameId").value(1))
                .andExpect(jsonPath("$.data[0].opponent").value("LIU"))
                .andExpect(jsonPath("$.data[1].opponent").value("UCF"));
    }
}
