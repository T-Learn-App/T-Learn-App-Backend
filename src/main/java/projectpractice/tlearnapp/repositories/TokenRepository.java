package projectpractice.tlearnapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectpractice.tlearnapp.entities.RefreshToken;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query(value = "SELECT * FROM refresh_tokens WHERE token = :token", nativeQuery = true)
    Optional<RefreshToken> findByToken(@Param("token") String token);
}
