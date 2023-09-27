package com.mccayl.questionnaire.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {
    String extractUserName(String token);
    Date extractExpiration(String token);

    String generateToken(UserDetails userDetails);

    String generateToken(UserDetails userDetails, int expInMin);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenValid(String token);
}
