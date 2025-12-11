package projectpractice.tlearnapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final AuthUserDetailsService userDetailsService;
    private final JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.info("Inside JWT filter");
            final String authorizationHeader = request.getHeader(AUTHORIZATION);
            String jwt = null;
            String email = null;
            if (Objects.nonNull(authorizationHeader) &&
                    authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7); // length of “Bearer “
                email = jwtHelper.extractEmail(jwt);
            }

            if (Objects.nonNull(email) &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails =
                        this.userDetailsService.loadUserByUsername(email);
                boolean isTokenValidated =
                        validateToken(jwt, userDetails);
                if (isTokenValidated) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(
                            usernamePasswordAuthenticationToken);
                }
            }
        } catch (ExpiredJwtException jwtException) {
            request.setAttribute("exception", jwtException);
        } catch (BadCredentialsException |
                 UnsupportedJwtException |
                 MalformedJwtException e) {
            log.error("Filter exception: {}", e.getMessage());
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = jwtHelper.extractEmail(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String bearerToken) {
        return extractExpiry(bearerToken).before(new Date());
    }

    public Date extractExpiry(String bearerToken) {
        return jwtHelper.extractClaimBody(bearerToken, Claims::getExpiration);
    }
}
