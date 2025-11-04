package projectpractice.tlearnapp.servicies;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectpractice.tlearnapp.dto.responses.GetUserResponse;
import projectpractice.tlearnapp.dto.responses.SetGetWordResponse;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.entities.Word;
import projectpractice.tlearnapp.exceptions.DataNotFoundException;
import projectpractice.tlearnapp.mappers.UserMapper;
import projectpractice.tlearnapp.repositories.UsersRepository;

import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    public GetUserResponse getUser(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow(DataNotFoundException::new);
        return userMapper.toGetUserResponse(user);
    }

    public SetGetWordResponse getLearntUserWords(Long userId) {
        Set<Word> userLearntWords = usersRepository.findLearntWordsByUserId(userId)
                .orElseThrow(DataNotFoundException::new);
        return userMapper.toSetGetWordResponse(userLearntWords);
    }
}
