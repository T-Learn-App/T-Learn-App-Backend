package projectpractice.tlearnapp.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import projectpractice.tlearnapp.TestConfig;
import projectpractice.tlearnapp.entities.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import({TestConfig.class})
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("pasha@mail.ru");
    }

    @Test
    @Transactional
    public void getUserSuccessfully() {
        usersRepository.save(user);

        Optional<User> gotUser = usersRepository.findByEmail(user.getEmail());

        assertThat(gotUser.isPresent()).isTrue();
        assertThat(gotUser.get().getId()).isEqualTo(user.getId());
        assertThat(gotUser.get().getEmail()).isEqualTo(user.getEmail());
    }
}
