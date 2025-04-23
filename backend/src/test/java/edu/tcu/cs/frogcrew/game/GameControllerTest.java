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
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        game1.setOpponent("LIU");
        game1.setLocation("Carter");
        game1.setGameDate(LocalDate.of(2024, 9, 7));
        game1.setCallTime(null);

        GameDTO game2 = new GameDTO();
        game2.setOpponent("UCF");
        game2.setLocation("Carter");
        game2.setGameDate(LocalDate.of(2024, 9, 14));
        game2.setCallTime(null);

        when(gameService.getAllGameDTOs()).thenReturn(List.of(game1, game2));

        // Act & Assert
        mockMvc.perform(get("/gameSchedule/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].opponent").value("LIU"))
                .andExpect(jsonPath("$.data[0].location").value("Carter"))
                .andExpect(jsonPath("$.data[1].opponent").value("UCF"));
    }

    @Test
    void addGamesToSchedule_shouldReturn200AndGameList() throws Exception {
        GameDTO dto1 = new GameDTO();
        dto1.setOpponent("Baylor");
        dto1.setLocation("Waco");
        dto1.setGameDate(LocalDate.of(2024, 11, 15));
        dto1.setCallTime(LocalTime.of(18, 0));

        Game g1 = new Game();
        g1.setGameId(2L);
        g1.setOpponent(dto1.getOpponent());
        g1.setLocation(dto1.getLocation());
        g1.setGameDate(dto1.getGameDate());
        g1.setCallTime(dto1.getCallTime());

        when(gameService.createGameSchedule(anyString(), anyList())).thenReturn(List.of(g1));

        mockMvc.perform(post("/gameSchedule/games")
                        .param("token", "test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(dto1))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Games added to schedule successfully."))
                .andExpect(jsonPath("$.data[0].opponent").value("Baylor"));
    }


}
