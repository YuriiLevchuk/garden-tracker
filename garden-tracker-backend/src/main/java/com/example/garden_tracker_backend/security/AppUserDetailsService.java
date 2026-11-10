package com.example.garden_tracker_backend.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AppUserDetailsService extends UserDetailsService {

    UserDetails loadUserById(Long id) throws UsernameNotFoundException;
}
