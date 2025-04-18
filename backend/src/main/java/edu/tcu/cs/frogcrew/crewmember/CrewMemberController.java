package edu.tcu.cs.frogcrew.crewmember;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
}
