package edu.tcu.cs.frogcrew.crewlist;

import edu.tcu.cs.frogcrew.crewmember.CrewMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crewlist")
public class CrewListController {

    private final CrewAssignmentExport crewAssignmentExport;

    public CrewListController(CrewAssignmentExport crewAssignmentExport) {
        this.crewAssignmentExport = crewAssignmentExport;
    }

    @GetMapping("/export/{gameId}")
    public ResponseEntity<List<CrewMember>> exportCrewList(@PathVariable Long gameId) {
        List<CrewMember> crew = crewAssignmentExport.getCrewAssignedToGame(gameId);
        return ResponseEntity.ok(crew);
    }
}
