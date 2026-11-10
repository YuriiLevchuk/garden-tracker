package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.service.UserProvider;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.example.garden_tracker_backend.TestConstants.*;

public class SecurityContextUserProviderTest {

    private final UserProvider userProvider = new SecurityContextUserProvider();

    @Test
    public void givenNullAuthentication_whenGetCurrentUser_thenThrow() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertThrows(IllegalStateException.class, userProvider::getCurrentUser);
    }

    @Test
    public void givenInvalidPrincipalTypeAuthentication_whenGetCurrentUser_thenThrow() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("StringPrincipal", null, List.of()));
        assertThrows(IllegalStateException.class, userProvider::getCurrentUser);
    }

    @Test
    public void givenValidAuthentication_whenGetCurrentUser_thenReturnCurrentUser() {
        var userDetails = new UserDetailsImpl(
                1L,
                TEST_EMAIL,
                null
        );
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );
        var result = userProvider.getCurrentUser();
        assertEquals(userDetails.getId(), result);
    }
}
