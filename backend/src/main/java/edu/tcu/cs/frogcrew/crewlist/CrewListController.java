package edu.tcu.cs.frogcrew.crewlist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crewlist")
public class CrewListController {

    private final CrewListService crewListService;

    public CrewListController(CrewListService crewListService) {
        this.crewListService = crewListService;
    }

    @GetMapping("/export/{gameId}")
    public ResponseEntity<Map<String, Object>> exportCrewList(@PathVariable Long gameId) {
        List<CrewAssignmentExport> exportList = crewListService.getCrewAssignedToGame(gameId);

        Map<String, Object> response = new HashMap<>();
        response.put("flag", true);
        response.put("code", 200);
        response.put("message", "Find Success");
        response.put("data", exportList);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/schedule")
    public ResponseEntity<Map<String, Object>> scheduleCrew(@RequestBody CrewListDTO request) {
        crewListService.scheduleCrew(request.getGameId(), request.getCrewMemberIds());

        Map<String, Object> response = new HashMap<>();
        response.put("flag", true);
        response.put("code", 200);
        response.put("message", "Crew scheduled successfully.");
        response.put("data", null);

        return ResponseEntity.ok(response);
    }

}
