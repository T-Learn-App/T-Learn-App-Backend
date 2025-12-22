package projectpractice.tlearnapp.servicies;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectpractice.tlearnapp.dto.UserDto;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.exceptions.ConflictException;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.mappers.UserMapper;
import projectpractice.tlearnapp.repositories.UsersRepository;
import projectpractice.tlearnapp.security.JwtTokenProvider;

@Service
@AllArgsConstructor
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public UserDto getUser(String accessToken) {
        Long userId = jwtTokenProvider.getUserIdFromToken(accessToken);
        User user = usersRepository.findById(userId).orElseThrow(DataNotFoundException::new);
        return userMapper.toUserDto(user);
    }
}
