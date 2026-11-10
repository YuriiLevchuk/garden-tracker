package com.example.garden_tracker_backend.security;

import com.example.garden_tracker_backend.service.UserProvider;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final UserProvider userProvider;
    private final SecretKey key;
    private final Duration expiration;

    public JwtServiceImpl(UserProvider userProvider,
                          @Value("${app.jwt.secret}") String secret,
                          @Value("${app.jwt.expiration}") Duration expiration) {
        this.userProvider = userProvider;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    @Override
    public String createToken() {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userProvider.getCurrentUser().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expiration)))
                .signWith(key)
                .compact();
    }

    @Override
    public Long getSubject(String token) throws JwtException, IllegalArgumentException {
        String subject = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return Long.parseLong(subject);
    }
}
