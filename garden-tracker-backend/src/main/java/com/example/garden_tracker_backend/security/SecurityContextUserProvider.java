package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.model.User;
import com.example.garden_tracker_backend.service.UserProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextUserProvider implements UserProvider {

    @Override
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            return userDetails.getUser();
        }
        throw new IllegalStateException();
    }
}
