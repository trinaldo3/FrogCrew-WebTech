package edu.tcu.cs.frogcrew.crewmember;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrewMemberServiceTest {

    @Mock
    CrewMemberRepository crewMemberRepository;

    @InjectMocks
    CrewMemberService crewMemberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldReturnSavedCrewMember() {
        // Arrange
        CrewMemberDTO dto = new CrewMemberDTO();
        dto.setFirstName("Jane");
        dto.setLastName("Doe");
        dto.setEmail("jane.doe@tcu.edu");
        dto.setPhoneNumber("555-1234");
        dto.setPassword("secret");
        dto.setRole("ADMIN");
        dto.setPosition(List.of("Producer"));

        CrewMember saved = new CrewMember();
        saved.setId(1L);
        saved.setFirstName(dto.getFirstName());
        saved.setLastName(dto.getLastName());
        saved.setEmail(dto.getEmail());
        saved.setPhoneNumber(dto.getPhoneNumber());
        saved.setPassword(dto.getPassword());
        saved.setRole(dto.getRole());
        // saved.setPosition(dto.getPosition());

        when(crewMemberRepository.save(any(CrewMember.class))).thenReturn(saved);

        // Act
        CrewMember result = crewMemberService.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jane", result.getFirstName());
        // assertEquals("Producer", result.getPosition().get(0));
        verify(crewMemberRepository).save(any(CrewMember.class));
    }

    @Test
    void findById_shouldReturnCrewMemberWhenExists() {
        // Arrange
        CrewMember mock = new CrewMember();
        mock.setId(42L);
        mock.setFirstName("Test");
        when(crewMemberRepository.findById(42L)).thenReturn(Optional.of(mock));

        // Act
        Optional<CrewMember> result = crewMemberService.findById(42L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getFirstName());
        verify(crewMemberRepository).findById(42L);
    }

    @Test
    void findById_shouldReturnEmptyWhenNotFound() {
        when(crewMemberRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<CrewMember> result = crewMemberService.findById(99L);

        assertFalse(result.isPresent());
        verify(crewMemberRepository).findById(99L);
    }
}
