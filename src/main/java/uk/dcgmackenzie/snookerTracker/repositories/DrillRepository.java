package uk.dcgmackenzie.snookerTracker.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.dcgmackenzie.snookerTracker.entities.Drill;

public interface DrillRepository extends CrudRepository<Drill, Long> {
    @Query("SELECT d FROM Drill d WHERE d.id = :id AND d.user.username = :username")
    Drill findById(Long id, String username);
}
