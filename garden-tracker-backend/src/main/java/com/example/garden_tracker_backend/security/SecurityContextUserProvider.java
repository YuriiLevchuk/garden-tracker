package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.service.UserProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextUserProvider implements UserProvider {

    @Override
    public Long getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl user) {
            return user.getId();
        }
        throw new IllegalStateException();
    }
}
