package projectpractice.tlearnapp.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projectpractice.tlearnapp.dto.requests.AuthRequest;
import projectpractice.tlearnapp.dto.responses.AuthResponse;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.repositories.UsersRepository;
import projectpractice.tlearnapp.security.AuthUserDetailsService;
import projectpractice.tlearnapp.security.JwtHelper;

import java.util.Collections;
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

    public AuthResponse login(AuthRequest request) {
        String email = request.email();
        String password = request.password();
        String encodedPassword;

        Optional<User> user = usersRepository.findByEmail(email);

        if (user.isPresent()) {
            User excistsUser = user.get();
            if (!passwordEncoder.matches(password, excistsUser.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            encodedPassword = passwordEncoder.encode(password);
            usersRepository.save(new User(email, encodedPassword));
        }

        return generateToken(request);
    }

    public AuthResponse generateToken(AuthRequest request) {
        String email = request.email();
        String password = request.password();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtHelper.createToken(Collections.emptyMap(), userDetails.getUsername());
        return new AuthResponse(token);
    }
}
