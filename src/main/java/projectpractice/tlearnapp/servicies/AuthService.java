package projectpractice.tlearnapp.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.dto.requests.AuthRequest;
import projectpractice.tlearnapp.dto.responses.AuthResponse;
import projectpractice.tlearnapp.entities.RefreshToken;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.repositories.TokenRepository;
import projectpractice.tlearnapp.repositories.UsersRepository;
import projectpractice.tlearnapp.security.AuthUserDetailsService;
import projectpractice.tlearnapp.security.JwtHelper;
import projectpractice.tlearnapp.security.JwtHelper.TokenType;
import projectpractice.tlearnapp.security.JwtProperties;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    public final BCryptPasswordEncoder passwordEncoder;
    private final AuthUserDetailsService userDetailsService;
    private final JwtHelper jwtHelper;
    private final UsersRepository usersRepository;
    private final TokenRepository tokenRepository;
    private final JwtProperties jwtProperties;

    @Transactional
    public AuthResponse login(AuthRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String encodedPassword;

        Optional<User> user = usersRepository.findByEmail(email);
        Optional<RefreshToken> refreshToken;

        if (user.isPresent()) {
            User excistsUser = user.get();
            refreshToken = tokenRepository.findByToken(request.getRefreshToken());
            if (!passwordEncoder.matches(password, excistsUser.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            encodedPassword = passwordEncoder.encode(password);
            User newUser = new User(email, encodedPassword);
            usersRepository.save(newUser);

            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", usersRepository.findByEmail(email).get().getId());
            refreshToken = Optional.of(new RefreshToken(
                    jwtHelper.createToken(claims, userDetails.getUsername(), TokenType.REFRESH),
                    newUser,
                    Date.from(Instant.ofEpochSecond(System.currentTimeMillis() + jwtProperties.getRefreshTokenValidity()))));
            tokenRepository.save(refreshToken.get());
        }

        return AuthResponse.builder()
                .accessToken(generateAccessToken(request).getAccessToken())
                .refreshToken(refreshToken.get().getToken())
                .build();
    }

    @Transactional
    public AuthResponse generateAccessToken(AuthRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        final UserDetails userDetails = loadUserByUsernameWithCache(email);

        if (request.getRefreshToken() == null) {
            throw new BadCredentialsException("Refresh token is null");
        }

        String refreshToken = tokenRepository.findByToken(request.getRefreshToken()).get().getToken();
        if (!refreshToken.equals(request.getRefreshToken())) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", usersRepository.findByEmail(email).get().getId());

        String token = jwtHelper.createToken(claims, userDetails.getUsername(), TokenType.ACCESS);
        return AuthResponse.builder().accessToken(token).build();
    }

    @Cacheable(value = "userDetails", key = "#email",
            unless = "#result == null")
    public UserDetails loadUserByUsernameWithCache(String username) {
        log.info("Loading user details: {}", username);
        return userDetailsService.loadUserByUsername(username);
    }
}
