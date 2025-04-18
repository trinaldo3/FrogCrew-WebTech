package edu.tcu.cs.frogcrew.availability;

import org.springframework.stereotype.Service;

@Service
public class AvailabilityService {

    public AvailabilityDTO submitAvailability(AvailabilityDTO dto) {
        // for now, just return the DTO
        return dto;
    }
}
