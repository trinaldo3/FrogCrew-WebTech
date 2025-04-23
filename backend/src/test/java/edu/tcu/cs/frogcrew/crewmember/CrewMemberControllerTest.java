package edu.tcu.cs.frogcrew.crewmember;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CrewMemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class CrewMemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CrewMemberService crewMemberService;

    @Test
    void createCrewMember_shouldReturn200AndWrappedResponse() throws Exception {
        CrewMemberDTO dto = new CrewMemberDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john.doe@tcu.edu");
        dto.setPhoneNumber("1234567890");
        dto.setPassword("P@ssw0rd");
        dto.setRole("ADMIN");
        dto.setPosition(List.of("Director", "Producer"));

        CrewMember saved = new CrewMember();
        saved.setId(1L);
        saved.setFirstName(dto.getFirstName());
        saved.setLastName(dto.getLastName());
        saved.setEmail(dto.getEmail());
        saved.setPhoneNumber(dto.getPhoneNumber());
        saved.setPassword(dto.getPassword());
        saved.setRole(dto.getRole());
        saved.setPosition(dto.getPosition());

        when(crewMemberService.create(any(CrewMemberDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/crewmember?token=abc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.positions[0]").value("Director"));
    }

    @Test
    void createCrewMember_shouldReturn400WhenMissingFields() throws Exception {
        CrewMemberDTO dto = new CrewMemberDTO(); // Empty

        mockMvc.perform(post("/crewmember?token=abc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.firstName").value("must not be empty"));
    }

    @Test
    void getCrewMember_shouldReturn200WhenFound() throws Exception {
        CrewMember crew = new CrewMember();
        crew.setId(1L);
        crew.setFirstName("John");
        crew.setLastName("Doe");

        when(crewMemberService.findById(1L)).thenReturn(Optional.of(crew));

        mockMvc.perform(get("/crewmember/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void getCrewMember_shouldReturn404WhenNotFound() throws Exception {
        when(crewMemberService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/crewmember/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void inviteCrewMember_shouldReturn200AndSuccessMessage() throws Exception {
        String token = "abc123";
        String email = "someone@example.com";
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);

        mockMvc.perform(post("/invite")
                        .param("token", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(content().string("Crew member invited successfully."));
    }

    @Test
    void deleteCrewMember_shouldReturn200AndSuccessMessage() throws Exception {
        mockMvc.perform(delete("/crewmember/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void getAllCrewMembers_shouldReturn200AndListOfCrew() throws Exception {
        CrewMember crew1 = new CrewMember();
        crew1.setId(1L);
        crew1.setFirstName("John");
        crew1.setLastName("Doe");
        crew1.setEmail("john.doe@tcu.edu");
        crew1.setPhoneNumber("1234567890");
        crew1.setRole("ADMIN");
        crew1.setPosition(List.of("Producer"));

        CrewMember crew2 = new CrewMember();
        crew2.setId(2L);
        crew2.setFirstName("Jane");
        crew2.setLastName("Smith");
        crew2.setEmail("jane.smith@tcu.edu");
        crew2.setPhoneNumber("0987654321");
        crew2.setRole("CREW");
        crew2.setPosition(List.of("Director"));

        when(crewMemberService.findAll()).thenReturn(List.of(crew1, crew2));

        mockMvc.perform(get("/crewmembers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Fetch Success"))
                .andExpect(jsonPath("$.data[0].firstName").value("John"))
                .andExpect(jsonPath("$.data[1].firstName").value("Jane"));
    }

}