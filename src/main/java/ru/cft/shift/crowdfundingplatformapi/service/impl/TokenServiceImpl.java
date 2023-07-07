package ru.cft.shift.crowdfundingplatformapi.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.cft.shift.crowdfundingplatformapi.entity.Person;
import ru.cft.shift.crowdfundingplatformapi.enumeration.PersonRole;
import ru.cft.shift.crowdfundingplatformapi.exception.UnauthorizedException;
import ru.cft.shift.crowdfundingplatformapi.security.TokenData;
import ru.cft.shift.crowdfundingplatformapi.service.TokenService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    private static final String ISSUER = "crowdfunding-platform-api";
    private static final String CLAIM_ID = "id";
    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_ROLE = "role";

    @Value("${tokens.access-secret-key}")
    private String accessSecret;

    @Value("${tokens.refresh-secret-key}")
    private String refreshSecret;

    @Value("${tokens.access-lifetime-min}")
    private int accessTokenLifetime;

    @Value("${tokens.refresh-lifetime-days}")
    private int refreshTokenLifetime;

    @Override
    public String generateAccessToken(Person person) {
        Key key = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        Date expiresAt = Date.from(
                Instant.now()
                        .plus(
                                accessTokenLifetime,
                                ChronoUnit.MINUTES
                        )
        );

        return generateToken(person.getId(), person.getEmail(), person.getRole(), expiresAt, key);
    }

    @Override
    public Pair<String, Date> generateRefreshTokenAndExpiresDate(Person person) {
        Key key = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
        Date expiresAt = Date.from(
                Instant.now()
                        .plus(
                                refreshTokenLifetime,
                                ChronoUnit.DAYS
                        )
        );

        return Pair.of(generateToken(person.getId(), person.getEmail(), person.getRole(), expiresAt, key), expiresAt);
    }

    @Override
    public TokenData decodeAccessToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
            Jws<Claims> data = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = data.getBody();

            return new TokenData(
                    UUID.fromString(claims.getSubject()),
                    claims.get(CLAIM_EMAIL, String.class),
                    PersonRole.valueOf(claims.get(CLAIM_ROLE, String.class))
            );
        } catch (Exception exception) {
            throw new UnauthorizedException("Не авторизован", exception);
        }
    }

    private String generateToken(UUID id,
                                 String email,
                                 PersonRole role,
                                 Date expiresAt,
                                 Key key) {
        Date issuedAt = Date.from(Instant.now());

        return Jwts
                .builder()
                .setIssuer(ISSUER)
                .setSubject(id.toString())
                .claim(CLAIM_ID, id.toString())
                .claim(CLAIM_ROLE, role)
                .claim(CLAIM_EMAIL, email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
