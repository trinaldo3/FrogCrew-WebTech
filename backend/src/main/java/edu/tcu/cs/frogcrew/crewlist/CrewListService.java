package edu.tcu.cs.frogcrew.crewlist;

import edu.tcu.cs.frogcrew.crewmember.CrewMember;
import edu.tcu.cs.frogcrew.crewmember.CrewMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrewListService {

    private final CrewMemberRepository crewMemberRepository;

    public CrewListService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public List<CrewAssignmentExport> getCrewAssignedToGame(Long gameId) {
        return crewMemberRepository.findAll().stream()
                .map(c -> new CrewAssignmentExport(
                        c.getId(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getPhoneNumber(),
                        c.getRole()))
                .collect(Collectors.toList());
    }

    public void scheduleCrew(Long gameId, List<Long> crewMemberIds) {

        System.out.println("Scheduling crew members " + crewMemberIds + " for game " + gameId);

    }

}
