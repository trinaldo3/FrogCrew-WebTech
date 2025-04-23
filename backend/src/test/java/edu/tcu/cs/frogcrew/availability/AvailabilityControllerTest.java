package edu.tcu.cs.frogcrew.availability;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AvailabilityController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AvailabilityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AvailabilityService availabilityService;

    @Test
    void submitAvailability_shouldReturn200_whenValid() throws Exception {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setUserId(1L);
        dto.setGameId(2L);
        dto.setAvailability(1);
        dto.setComment("Available after 5pm");

        when(availabilityService.submitAvailability(any(AvailabilityDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.userId").value(1));
    }

    @Test
    void submitAvailability_shouldReturn400_whenMissingUserId() throws Exception {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setGameId(2L);
        dto.setAvailability(1);
        dto.setComment("Missing userId");

        mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.data.userId").value("User id is required"));
    }

    @Test
    void submitAvailability_shouldReturn400_whenMissingGameId() throws Exception {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setUserId(1L);
        dto.setAvailability(1);
        dto.setComment("Missing gameId");

        mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.gameId").value("Game id is required"));
    }

    @Test
    void submitAvailability_shouldReturn400_whenMissingAvailability() throws Exception {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setUserId(1L);
        dto.setGameId(2L);
        dto.setComment("Missing availability");

        mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.availability").value("Availability is required"));
    }

    @Test
    void submitAvailability_shouldReturn400_whenCommentTooLong() throws Exception {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setUserId(1L);
        dto.setGameId(2L);
        dto.setAvailability(1);
        dto.setComment("a".repeat(300));  // Over 255 characters

        mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.comment").exists());
    }

    @Test
    void submitAvailability_shouldReturn200_whenCommentIsEmpty() throws Exception {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setUserId(1L);
        dto.setGameId(2L);
        dto.setAvailability(1);
        dto.setComment("");

        when(availabilityService.submitAvailability(any(AvailabilityDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true));
    }
}
