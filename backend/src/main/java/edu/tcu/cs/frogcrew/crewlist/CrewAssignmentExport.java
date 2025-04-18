package edu.tcu.cs.frogcrew.crewlist;

import edu.tcu.cs.frogcrew.crewmember.CrewMember;
import edu.tcu.cs.frogcrew.crewmember.CrewMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewAssignmentExport {

    private final CrewMemberRepository crewMemberRepository;

    public CrewAssignmentExport(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public List<CrewMember> getCrewAssignedToGame(Long gameId) {
        return crewMemberRepository.findAll(); // return all for now
    }
}
