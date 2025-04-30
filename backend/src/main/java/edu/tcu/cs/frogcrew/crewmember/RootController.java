package edu.tcu.cs.frogcrew.crewmember;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String home() {
        return "FrogCrew backend is running.";
    }
}
