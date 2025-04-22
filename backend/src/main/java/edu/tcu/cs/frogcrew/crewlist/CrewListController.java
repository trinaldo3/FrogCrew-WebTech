package edu.tcu.cs.frogcrew.crewlist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
