package projectpractice.tlearnapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DecodingException;
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

        this.algorithm = determineAlgorithm(jwtProperty.getSecretKey());
        this.key = createSecretKey(jwtProperty.getSecretKey(), this.algorithm);

        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String createAccessToken(String email, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return buildToken(email, jwtProperty.getAccessTokenValidity(), claims);
    }

    public String createRefreshToken(String email) {
        return buildToken(email, jwtProperty.getRefreshTokenValidity(), new HashMap<>());
    }

    private String buildToken(String subject, long expirationMillis, Map<String, Object> claims) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expirationMillis);

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(key, algorithm);

        return builder.compact();
    }

    public Claims parseToken(String token) {
        try {
            token = normalizeToken(token);
            return jwtParser.parseClaimsJws(token).getBody();

        } catch (ExpiredJwtException ex) {
            throw new JwtAuthenticationException("JWT token expired", ex);
        } catch (UnsupportedJwtException ex) {
            throw new JwtAuthenticationException("Unsupported JWT token", ex);
        } catch (MalformedJwtException ex) {
            throw new JwtAuthenticationException("Malformed JWT token", ex);
        } catch (DecodingException ex) {
            // ваша ошибка: Illegal base64url character: ' '
            throw new JwtAuthenticationException("JWT token decoding error", ex);
        } catch (IllegalArgumentException ex) {
            throw new JwtAuthenticationException("Invalid JWT token", ex);
        }
    }

    public Long getUserIdFromToken(String token) {
        Object userId = parseToken(token).get("userId");

        if (userId == null) {
            throw new JwtAuthenticationException("userId claim is missing in token");
        }

        if (userId instanceof Number n) {
            return n.longValue();
        }

        return Long.parseLong(userId.toString());
    }

    /**
     * Приводит токен к "чистому" виду:
     * - trim()
     * - если пришло "Bearer xxx" -> берём только xxx
     * - проверяем на пустоту
     */
    private String normalizeToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Token is null");
        }

        token = token.trim();

        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        if (token.isEmpty()) {
            throw new IllegalArgumentException("Token is empty");
        }

        return token;
    }

    private SignatureAlgorithm determineAlgorithm(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return (keyBytes.length * 8 >= 512) ? SignatureAlgorithm.HS512 : SignatureAlgorithm.HS256;
    }

    private SecretKey createSecretKey(String secret, SignatureAlgorithm algorithm) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, algorithm.getJcaName());
    }
}
