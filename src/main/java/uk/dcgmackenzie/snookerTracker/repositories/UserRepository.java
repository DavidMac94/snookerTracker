package uk.dcgmackenzie.snookerTracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.dcgmackenzie.snookerTracker.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
