package projectpractice.tlearnapp.servicies;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectpractice.tlearnapp.dto.responses.UserResponse;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.exceptions.UserAlreadyExistsException;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.mappers.UserMapper;
import projectpractice.tlearnapp.repositories.UsersRepository;

@Service
@AllArgsConstructor
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    public UserResponse getUser(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow(DataNotFoundException::new);
        return userMapper.toUserResponse(user);
    }

    public UserResponse addUser(String email) {
        if (usersRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("the user already exists", email);
        }
        User user = User.builder().email(email).build();
        usersRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
