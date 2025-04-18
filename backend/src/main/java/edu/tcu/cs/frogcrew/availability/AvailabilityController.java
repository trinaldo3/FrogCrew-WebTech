package edu.tcu.cs.frogcrew.availability;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping("/availability")
    public ResponseEntity<Map<String, Object>> submitAvailability(@Valid @RequestBody AvailabilityDTO dto) {
        AvailabilityDTO saved = availabilityService.submitAvailability(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("flag", true);
        response.put("code", 200);
        response.put("message", "Add Success");
        response.put("data", saved);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Custom 400 validation response format
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
