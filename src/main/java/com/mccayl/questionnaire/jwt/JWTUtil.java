package com.mccayl.questionnaire.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mccayl.questionnaire.model.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class JWTUtil {
    private static final String issuer = "McCayl Questionnaire";

    private final Algorithm accessTokenAlgorithm;
    private final Algorithm refreshTokenAlgorithm;

    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;

    private final JWTVerifier accessTokenVerifier;
    private final JWTVerifier refreshTokenVerifier;

    public JWTUtil(@Value("${security.jwt.access.secret}") String accessTokenSecret,
                   @Value("${security.jwt.refresh.secret}") String refreshTokenSecret,
                   @Value("${security.jwt.access.ExpirationMinutes}") int accessTokenExpirationMinutes,
                   @Value("${security.jwt.refresh.ExpirationDays}") int refreshTokenExpirationDays)
    {
        accessTokenAlgorithm = Algorithm.HMAC256(accessTokenSecret);
        refreshTokenAlgorithm = Algorithm.HMAC256(refreshTokenSecret);
        accessTokenExpirationMs = (long) accessTokenExpirationMinutes * 60 * 1000;
        refreshTokenExpirationMs = (long) refreshTokenExpirationDays * 24 * 60 * 60 * 1000;
        accessTokenVerifier = JWT.require(accessTokenAlgorithm)
                .withIssuer(issuer)
                .build();
        refreshTokenVerifier = JWT.require(refreshTokenAlgorithm)
                .withIssuer(issuer)
                .build();
    }

    public String generateAccessToken(User user) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + accessTokenExpirationMs))
                .sign(accessTokenAlgorithm);
    }

    public String generateRefreshToken(User user) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + refreshTokenExpirationMs))
                .sign(refreshTokenAlgorithm);
    }

    private Optional<DecodedJWT> decodeAccessToken(String token) {
        try {
            return Optional.of(accessTokenVerifier.verify(token));
        } catch (JWTVerificationException e) {
            log.error("invalid access token", e);
        }
        return Optional.empty();
    }

    private Optional<DecodedJWT> decodeRefreshToken(String token) {
        try {
            return Optional.of(refreshTokenVerifier.verify(token));
        } catch (JWTVerificationException e) {
            log.error("invalid refresh token", e);
        }
        return Optional.empty();
    }

    public boolean validateAccessToken(String token) {
        return decodeAccessToken(token).isPresent();
    }

    public boolean validateRefreshToken(String token) {
        return decodeRefreshToken(token).isPresent();
    }

    public String getUsernameFromAccessToken(String token) {
        return decodeAccessToken(token).get().getSubject();
    }

    public String getUsernameFromRefreshToken(String token) {
        return decodeRefreshToken(token).get().getSubject();
    }
}
