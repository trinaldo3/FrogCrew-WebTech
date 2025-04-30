package edu.tcu.cs.frogcrew.security;

import edu.tcu.cs.frogcrew.crewmember.CrewMember;
import edu.tcu.cs.frogcrew.crewmember.CrewMemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CrewMemberRepository repository;

    public CustomUserDetailsService(CrewMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CrewMember member = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        String role = member.getRole() != null ? member.getRole() : "CREW";

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(role)
                .build();
    }
}
