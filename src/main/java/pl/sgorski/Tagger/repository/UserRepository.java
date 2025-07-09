package pl.sgorski.Tagger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sgorski.Tagger.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
