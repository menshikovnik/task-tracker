package com.nickmenshikov.flux.auth.service;

import com.nickmenshikov.flux.auth.repository.UserRepository;
import com.nickmenshikov.flux.core.model.FluxUserDetails;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FluxUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(login)
                .or(() -> userRepository.findUserByEmail(login))
                .map(
                user -> new FluxUserDetails(
                        user.getId(),
                        user.getUsername(),
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                )
        ).orElseThrow(
                () -> new UsernameNotFoundException("User not found: " + login)
        );
    }
}
