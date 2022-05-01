package uk.dcgmackenzie.snookerTracker.services;

import org.springframework.http.ResponseEntity;
import uk.dcgmackenzie.snookerTracker.DTO.SessionDTO;
import uk.dcgmackenzie.snookerTracker.entities.Session;
import uk.dcgmackenzie.snookerTracker.response.SessionResponse;

import java.util.Collection;
import java.util.List;

public interface SessionService {
    SessionResponse saveSession(SessionDTO sessionDTO, String username);
    Collection<Session> getSessions(String username);
    Session getSession(Long id, String username);
    Collection<Session> getSessionsByDrill(Long drillId, String username);
}
