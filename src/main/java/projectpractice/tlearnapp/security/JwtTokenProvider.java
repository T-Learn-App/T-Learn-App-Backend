package projectpractice.tlearnapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projectpractice.tlearnapp.exceptions.JwtAuthenticationException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class JwtTokenProvider {

    private final JwtProperties jwtProperty;
    private final SecretKey key;
    private final JwtParser jwtParser;
    private final SignatureAlgorithm algorithm;

    @Autowired
    public JwtTokenProvider(JwtProperties jwtProperty) {
        this.jwtProperty = jwtProperty;
        this.key = createSecretKey();
        this.algorithm = determineAlgorithm();
        this.jwtParser = createJwtParser();
    }

    private SecretKey createSecretKey() {
        byte[] keyBytes = jwtProperty.getSecretKey().getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length * 8 >= 512) {
            return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
        } else {
            return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        }
    }

    private SignatureAlgorithm determineAlgorithm() {
        byte[] keyBytes = jwtProperty.getSecretKey().getBytes(StandardCharsets.UTF_8);
        return (keyBytes.length * 8 >= 512) ? SignatureAlgorithm.HS512 : SignatureAlgorithm.HS256;
    }

    private JwtParser createJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String createAccessToken(String email, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", String.valueOf(userId));
        return buildToken(email, jwtProperty.getAccessTokenValidity(), claims);
    }

    public String createRefreshToken(String email) {
        return buildToken(email, jwtProperty.getRefreshTokenValidity(), new HashMap<>());
    }

    private String buildToken(String subject, long expirationMillis, Map<String, Object> claims) {
        return buildToken(subject, expirationMillis, new Date(), claims);
    }

    private String buildToken(String subject, long expirationMillis, Date issuedAt, Map<String, Object> claims) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setClaims(claims)
                .setExpiration(new Date(issuedAt.getTime() + expirationMillis))
                .signWith(key, algorithm);

        return builder.compact();
    }

    public Claims parseToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw new JwtAuthenticationException("JWT token expired", ex);
        } catch (UnsupportedJwtException ex) {
            throw new JwtAuthenticationException("Unsupported JWT token", ex);
        } catch (MalformedJwtException ex) {
            throw new JwtAuthenticationException("Malformed JWT token", ex);
        } catch (IllegalArgumentException ex) {
            throw new JwtAuthenticationException("Invalid JWT token", ex);
        }
    }

    public Long getUserIdFromToken(String token) {
        return  Long.parseLong(parseToken(token).get("userId").toString());
    }
}