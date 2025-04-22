package edu.tcu.cs.frogcrew.crewlist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrewAssignmentExportTest {

    @Test
    void constructor_setsAllFieldsCorrectly() {
        CrewAssignmentExport export = new CrewAssignmentExport(
                1L, "John", "Doe", "555-1234", "ADMIN");

        assertEquals(1L, export.getId());
        assertEquals("John", export.getFirstName());
        assertEquals("Doe", export.getLastName());
        assertEquals("555-1234", export.getPhoneNumber());
        assertEquals("ADMIN", export.getRole());
    }

    @Test
    void setters_modifyFieldsCorrectly() {
        CrewAssignmentExport export = new CrewAssignmentExport();
        export.setId(2L);
        export.setFirstName("Jane");
        export.setLastName("Smith");
        export.setPhoneNumber("555-5678");
        export.setRole("PRODUCER");

        assertEquals(2L, export.getId());
        assertEquals("Jane", export.getFirstName());
        assertEquals("Smith", export.getLastName());
        assertEquals("555-5678", export.getPhoneNumber());
        assertEquals("PRODUCER", export.getRole());
    }
}
