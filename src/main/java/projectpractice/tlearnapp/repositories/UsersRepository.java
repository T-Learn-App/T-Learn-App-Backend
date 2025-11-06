package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectpractice.tlearnapp.entities.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
