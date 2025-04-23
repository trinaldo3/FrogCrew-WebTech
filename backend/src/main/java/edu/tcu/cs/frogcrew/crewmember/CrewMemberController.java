package edu.tcu.cs.frogcrew.crewmember;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CrewMemberController {

    private final CrewMemberService service;

    public CrewMemberController(CrewMemberService service) {
        this.service = service;
    }

    @PostMapping("/crewmember")
    public ResponseEntity<Map<String, Object>> createCrewMember(
            @RequestParam("token") String token,
            @Valid @RequestBody CrewMemberDTO dto) {

        CrewMember saved = service.create(dto);

        Map<String, Object> data = new HashMap<>();
        data.put("id", saved.getId());
        data.put("firstName", saved.getFirstName());
        data.put("lastName", saved.getLastName());
        data.put("email", saved.getEmail());
        data.put("phoneNumber", saved.getPhoneNumber());
        data.put("role", saved.getRole());
        data.put("positions", saved.getPosition()); // rename for API compliance

        Map<String, Object> response = new HashMap<>();
        response.put("flag", true);
        response.put("code", 200);
        response.put("message", "Add Success");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/crewmember/{id}")
    public ResponseEntity<?> getCrewMember(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Optional: Better validation error responses
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("flag", false);
        response.put("code", 400);
        response.put("message", "Provided arguments are invalid, see data for details.");
        response.put("data", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/invite")
    public ResponseEntity<String> inviteCrewMember(@RequestParam String token, @RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        service.inviteCrewMember(token, email);
        return ResponseEntity.ok("Crew member invited successfully.");
    }

    @DeleteMapping("/crewmember/{id}")
    public ResponseEntity<Map<String, Object>> deleteCrewMember(@PathVariable Long id) {
        service.delete(id);

        Map<String, Object> response = new HashMap<>();
        response.put("flag", true);
        response.put("code", 200);
        response.put("message", "Delete Success");
        response.put("data", null);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/crewmembers")
    public ResponseEntity<Map<String, Object>> getAllCrewMembers() {
        List<CrewMember> crewList = service.findAll();

        List<Map<String, Object>> data = crewList.stream().map(crew -> {
            Map<String, Object> crewMap = new HashMap<>();
            crewMap.put("id", crew.getId());
            crewMap.put("firstName", crew.getFirstName());
            crewMap.put("lastName", crew.getLastName());
            crewMap.put("email", crew.getEmail());
            crewMap.put("phoneNumber", crew.getPhoneNumber());
            crewMap.put("role", crew.getRole());
            crewMap.put("positions", crew.getPosition());
            return crewMap;
        }).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("flag", true);
        response.put("code", 200);
        response.put("message", "Fetch Success");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }


}
