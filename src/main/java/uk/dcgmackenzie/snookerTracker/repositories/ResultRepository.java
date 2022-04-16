package uk.dcgmackenzie.snookerTracker.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.dcgmackenzie.snookerTracker.entities.Result;

import java.util.List;

@Repository
public interface ResultRepository extends CrudRepository<Result, Long> {
    @Query("SELECT r FROM Result r WHERE r.session.drill.user.username = :username")
    List<Result> getResults(String username);

    @Query("SELECT r FROM Result r WHERE r.id = :id AND r.session.drill.user.username = :username")
    Result getResult(Long id, String username);

    @Query("SELECT max(score) FROM Result r WHERE r.session.drill.id = :drillId AND r.session.drill.user.username = :username")
    Integer getHighScore(Long drillId, String username);
}
