package uk.dcgmackenzie.snookerTracker.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import uk.dcgmackenzie.snookerTracker.entities.Session;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {
    @Query("SELECT s FROM Session s WHERE s.drill.user.username = :username")
    List<Session> getSessions(String username);

    @Query("SELECT s FROM Session s WHERE s.id = :id AND s.drill.user.username = :username")
    Session getSession(Long id, String username);

    @Query("SELECT s FROM Session s WHERE s.drill.id = :drillId AND s.drill.user.username = :username")
    List<Session> getSessionsByDrill(Long drillId, String username);
}