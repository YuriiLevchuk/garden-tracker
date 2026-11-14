package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.service.UserProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

public class JwtServiceTest {

    private static final String TEST_SECRET =
            "test-secret-key-that-is-at-least-32-bytes-long-for-hs256";
    private final UserProvider userProvider = mock(UserProvider.class);
    private final Duration expiration = Duration.ofMinutes(5);
    private final JwtService jwtService = new JwtServiceImpl(userProvider, TEST_SECRET, expiration);

    @Test
    public void whenCreateToken_thenReturnValidToken() {
        final Long userId = 1L;
        when(userProvider.getCurrentUser()).thenReturn(userId);
        var token = jwtService.createToken();

        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(TEST_SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertThat(claims.getSubject()).isEqualTo(String.valueOf(userId));
        assertThat(claims.getIssuedAt()).isInSameMinuteWindowAs(new Date());
        assertThat(claims.getExpiration()).isInSameMinuteWindowAs(new Date(System.currentTimeMillis() + expiration.toMillis()));
    }

    @Test
    void whenGetSubject_thenReturnSubjectFromGeneratedToken() {
        final Long userId = 1L;
        when(userProvider.getCurrentUser()).thenReturn(userId);
        String token = jwtService.createToken();

        assertThat(jwtService.getSubject(token)).isEqualTo(userId);
    }
}
