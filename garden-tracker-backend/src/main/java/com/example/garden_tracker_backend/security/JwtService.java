package com.example.garden_tracker_backend.security;

import io.jsonwebtoken.JwtException;

public interface JwtService {

    String createToken();
    Long getSubject(String token) throws JwtException, IllegalArgumentException;
}
