package edu.tcu.cs.frogcrew.game;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameService gameService;

    public GameServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGameDTOs_shouldReturnMappedList() {
        // Arrange
        Game g1 = new Game();
        g1.setGameId(1L);
        g1.setScheduleId(1L);
        g1.setGameDate(LocalDate.of(2024, 9, 7));
        g1.setVenue("Carter");
        g1.setOpponent("LIU");
        g1.setFinalized(false);

        Game g2 = new Game();
        g2.setGameId(2L);
        g2.setScheduleId(1L);
        g2.setGameDate(LocalDate.of(2024, 9, 14));
        g2.setVenue("Carter");
        g2.setOpponent("UCF");
        g2.setFinalized(true);

        when(gameRepository.findAll()).thenReturn(List.of(g1, g2));

        // Act
        List<GameDTO> result = gameService.getAllGameDTOs();

        // Assert
        assertEquals(2, result.size());

        GameDTO dto1 = result.get(0);
        assertEquals(1L, dto1.getGameId());
        assertEquals("LIU", dto1.getOpponent());
        assertFalse(dto1.isFinalized());

        GameDTO dto2 = result.get(1);
        assertEquals(2L, dto2.getGameId());
        assertEquals("UCF", dto2.getOpponent());
        assertTrue(dto2.isFinalized());

        verify(gameRepository, times(1)).findAll();
    }
}
