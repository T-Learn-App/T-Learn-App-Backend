package projectpractice.tlearnapp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final AuthUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            log.info("Inside JWT filter");

            String token = resolveToken(request);

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                Claims claims = jwtProvider.parseToken(token);

                if (claims == null || claims.getSubject() == null) {
                    log.warn("Invalid token: claims or subject is null");
                    filterChain.doFilter(request, response);
                    return;
                }

                String email = claims.getSubject();
                log.info("JWT subject(email) = [{}]", email);

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        } catch (ExpiredJwtException e) {
            log.warn("Token expired: {}", e.getMessage());
            request.setAttribute("exception", e);
        } catch (BadCredentialsException |
                 UnsupportedJwtException |
                 MalformedJwtException |
                 DecodingException |
                 IllegalArgumentException e) {
            log.error("Filter exception: {}", e.getMessage());
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null) return null;

        header = header.trim();

        if (header.startsWith("Bearer ")) {
            String token = header.substring(7).trim();
            return token.isEmpty() ? null : token;
        }

        return header.isEmpty() ? null : header;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = jwtProvider.parseToken(token).getSubject();
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiry(token).before(new Date());
    }

    public Date extractExpiry(String token) {
        return jwtProvider.parseToken(token).getExpiration();
    }
}