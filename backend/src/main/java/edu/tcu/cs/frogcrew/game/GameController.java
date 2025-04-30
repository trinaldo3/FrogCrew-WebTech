package edu.tcu.cs.frogcrew.game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gameSchedule")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping("/games")
    public ResponseEntity<Map<String, Object>> getAllGames() {
        List<GameDTO> games = service.getAllGameDTOs();

        Map<String, Object> response = new HashMap<>();
        response.put("flag", true);
        response.put("code", 200);
        response.put("message", "Find Success");
        response.put("data", games);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/games")
    public ResponseEntity<Map<String, Object>> addGamesToSchedule(
            @RequestParam String token,
            @RequestBody List<GameDTO> gameDTOList) {

        List<Game> addedGames = service.createGameSchedule(token, gameDTOList);

        List<Map<String, Object>> data = addedGames.stream().map(game -> {
            Map<String, Object> g = new HashMap<>();
            g.put("id", game.getGameId());
            g.put("sport", game.getSport());
            g.put("opponent", game.getOpponent());
            g.put("location", game.getLocation());
            g.put("gameDate", game.getGameDate());
            g.put("callTime", game.getCallTime());
            return g;
        }).toList();
        

        Map<String, Object> response = new HashMap<>();
        response.put("flag", true);
        response.put("code", 200);
        response.put("message", "Games added to schedule successfully.");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/games/{gameId}/assign")
    public ResponseEntity<Map<String, Object>> assignCrewToGame(
            @PathVariable Long gameId,
            @RequestBody List<Long> crewIds) {

        service.assignCrewMembers(gameId, crewIds);

        Map<String, Object> response = new HashMap<>();
        response.put("flag", true);
        response.put("code", 200);
        response.put("message", "Crew assigned successfully.");
        return ResponseEntity.ok(response);
    }

}
