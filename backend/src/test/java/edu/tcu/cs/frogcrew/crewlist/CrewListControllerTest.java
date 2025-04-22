package edu.tcu.cs.frogcrew.crewlist;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CrewListController.class)
@AutoConfigureMockMvc(addFilters = false)
class CrewListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CrewListService crewListService;

    @Test
    void exportCrewList_shouldReturnWrappedResponse() throws Exception {
        CrewAssignmentExport c1 = new CrewAssignmentExport(1L, "John", "Doe", "555-1234", "ADMIN");
        CrewAssignmentExport c2 = new CrewAssignmentExport(2L, "Jane", "Smith", "555-5678", "DIRECTOR");

        when(crewListService.getCrewAssignedToGame(1L)).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/crewlist/export/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].firstName").value("John"))
                .andExpect(jsonPath("$.data[1].firstName").value("Jane"));
    }
}
