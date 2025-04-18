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
}
