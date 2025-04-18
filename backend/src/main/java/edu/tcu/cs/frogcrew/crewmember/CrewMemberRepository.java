package edu.tcu.cs.frogcrew.crewmember;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
    // more methods
}
