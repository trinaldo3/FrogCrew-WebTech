package edu.tcu.cs.frogcrew.crewmember;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CrewMemberService {

    private final CrewMemberRepository repository;

    public CrewMemberService(CrewMemberRepository repository) {
        this.repository = repository;
    }

    public CrewMember create(CrewMemberDTO dto) {
        CrewMember member = new CrewMember();
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setEmail(dto.getEmail());
        member.setPhoneNumber(dto.getPhoneNumber());
        member.setPassword(dto.getPassword());
        member.setRole(dto.getRole());
        member.setPosition(dto.getPosition());
        return repository.save(member);
    }

    public Optional<CrewMember> findById(Long id) {
        return repository.findById(id);
    }

    public void inviteCrewMember(String token, String email) {
        // Simulate sending invite (expand later if needed)
        System.out.println("Inviting crew member with email: " + email + " using token: " + token);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }


}
