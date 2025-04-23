package edu.tcu.cs.frogcrew.availability;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvailabilityServiceTest {

    AvailabilityService availabilityService = new AvailabilityService();

    @Test
    void submitAvailability_shouldReturnSameDTO() {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setUserId(1L);
        dto.setGameId(2L);
        dto.setAvailability(1);
        dto.setComment("Test comment");

        AvailabilityDTO result = availabilityService.submitAvailability(dto);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals(2L, result.getGameId());
        assertEquals(1, result.getAvailability());
        assertEquals("Test comment", result.getComment());
    }

    @Test
    void submitAvailability_shouldHandleNullComment() {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setUserId(3L);
        dto.setGameId(4L);
        dto.setAvailability(2);
        dto.setComment(null);

        AvailabilityDTO result = availabilityService.submitAvailability(dto);

        assertNotNull(result);
        assertNull(result.getComment());
    }
}
