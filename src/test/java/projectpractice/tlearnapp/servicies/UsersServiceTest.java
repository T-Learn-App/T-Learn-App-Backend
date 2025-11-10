package projectpractice.tlearnapp.servicies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectpractice.tlearnapp.dto.UserDto;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.exceptions.ConflictException;
import projectpractice.tlearnapp.mappers.UserMapper;
import projectpractice.tlearnapp.repositories.UsersRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test class for UsersService")
public class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UsersService usersService;

    private Optional<User> user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = Optional.of(new User("pasha@mail.ru"));
        userDto = new UserDto(1L, "pasha@mail.ru");
    }

    @Test
    public void shouldGetUserSuccessfully() {
        when(usersRepository.findById(1L)).thenReturn(user);
        when(userMapper.toUserDto(user.get())).thenReturn(userDto);

        assertThat(usersService.getUser(1L)).isEqualTo(userDto);
    }

    @Test
    public void shouldGetUserUnsuccessfullyWhenUserNotFound() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(DataNotFoundException.class)
                .isThrownBy(() -> usersService.getUser(1L));
    }

    @Test
    public void shouldAddUserSuccessfully() {
        String email = "pasha@mail.ru";
        User savedUser = User.builder().email(email).build();
        UserDto expectedDto = new UserDto(1L, email);

        when(usersRepository.existsByEmail(email)).thenReturn(false);
        when(usersRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toUserDto(any(User.class))).thenReturn(expectedDto);

        UserDto result = usersService.addUser(email);

        assertThat(result).isEqualTo(expectedDto);
    }


    @Test
    public void shouldAddUserUnsuccessfullyWhenUserAlreadyExists() {
        when(usersRepository.existsByEmail(user.get().getEmail())).thenReturn(true);

        assertThatExceptionOfType(ConflictException.class)
                .isThrownBy(() -> usersService.addUser(user.get().getEmail()));
    }
}
