package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements AppUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(u -> new UserDetailsImpl(u.getId(), u.getEmail(), u.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

    @Override
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .map(u -> new UserDetailsImpl(u.getId(), u.getEmail(), u.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("Id not found"));
    }
}
