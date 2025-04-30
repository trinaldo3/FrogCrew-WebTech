package edu.tcu.cs.frogcrew.security;

import edu.tcu.cs.frogcrew.crewmember.CrewMember;
import edu.tcu.cs.frogcrew.crewmember.CrewMemberRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CrewMemberUserDetailsService implements UserDetailsService {

    private final CrewMemberRepository repository;

    public CrewMemberUserDetailsService(CrewMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CrewMember member = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
            .username(member.getEmail())
            .password(member.getPassword())
            .roles(member.getRole())
            .build();
    }
}
