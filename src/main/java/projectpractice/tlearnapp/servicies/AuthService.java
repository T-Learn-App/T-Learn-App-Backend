package projectpractice.tlearnapp.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.dto.requests.AuthRequest;
import projectpractice.tlearnapp.dto.responses.AuthResponse;
import projectpractice.tlearnapp.entities.RefreshToken;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.exceptions.InvalidRequestException;
import projectpractice.tlearnapp.exceptions.RefreshTokenExpiredException;
import projectpractice.tlearnapp.repositories.TokenRepository;
import projectpractice.tlearnapp.repositories.UsersRepository;
import projectpractice.tlearnapp.security.JwtProperties;
import projectpractice.tlearnapp.security.JwtTokenProvider;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsersRepository userRepository;
    private final TokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperty;

    @Transactional
    public AuthResponse registerOrLogin(AuthRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User user = userRepository.findByEmail(email).get();
        if (user == null) {
            return createAndSaveTokens(createUser(email, password));
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        return createAndSaveTokens(user);
    }

    @Transactional
    public void register(String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new InvalidRequestException("user already exists: " + email);
        }

        createUser(email, password);
    }

    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();

        User user = userRepository.findByEmail(email).get();
        if (user == null) {
            throw new InvalidRequestException("user already exists: " + email);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidRequestException("invalid password");
        }

        return createAndSaveTokens(user);
    }

    @Transactional(noRollbackFor = RefreshTokenExpiredException.class)
    public AuthResponse refreshToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken).get();
        if (token == null) {
            throw new InvalidRequestException("invalid token");
        }

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiredException("refresh token expired");
        }

        refreshTokenRepository.delete(token);
        return createAndSaveTokens(token.getUser());
    }

    private User createUser(String email, String password) {
        User user = User.builder().email(email).password(password).build();

        return userRepository.save(user);
    }

    private AuthResponse createAndSaveTokens(User user) {
        String accessToken = jwtTokenProvider.createAccessToken(
                user.getEmail(),
                user.getId()
        );
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        refreshTokenRepository.deleteByUser(user);

        RefreshToken savedRefreshToken = new RefreshToken();
        LocalDateTime expiryDate = LocalDateTime.now().plus(jwtProperty.getRefreshTokenValidity(), ChronoUnit.MILLIS);
        savedRefreshToken.setAccessToken(accessToken);
        savedRefreshToken.setUser(user);
        savedRefreshToken.setToken(refreshToken);
        log.info("expiry time: {}", expiryDate);
        savedRefreshToken.setExpiryDate(expiryDate);

        savedRefreshToken = refreshTokenRepository.save(savedRefreshToken);

        return AuthResponse.builder().accessToken(accessToken).refreshToken(savedRefreshToken.getToken()).build();
    }
}
