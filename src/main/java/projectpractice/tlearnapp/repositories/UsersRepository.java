package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectpractice.tlearnapp.entities.User;
import projectpractice.tlearnapp.entities.Word;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.learntUserWords FROM User u WHERE u.id = :userId")
    Optional<Set<Word>> findLearntWordsByUserId(@Param("userId") Long userId);
}
