package edu.tcu.cs.frogcrew.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authManager;

    public AuthController(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        try {
            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );

            Map<String, Object> response = new HashMap<>();
            response.put("flag", true);
            response.put("message", "Login successful");
            response.put("user", auth.getPrincipal());
            return response;

        } catch (AuthenticationException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("flag", false);
            response.put("message", "Invalid email or password");
            return response;
        }
    }
}
