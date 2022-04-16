package uk.dcgmackenzie.snookerTracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.dcgmackenzie.snookerTracker.entities.Drill;
import uk.dcgmackenzie.snookerTracker.entities.Session;
import uk.dcgmackenzie.snookerTracker.repositories.SessionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class SessionServiceImpl implements SessionService{
    private final SessionRepository sessionRepository;
    private final DrillService drillService;

    @Override
    public Session saveSession(Long drillId, String username) {
        Session session = new Session();
        Drill drill = drillService.getDrill(drillId, username);
        if (drill == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drill id " + drillId + " does not exist");
        }
        session.setDrill(drill);
        session.setTimestamp(LocalDateTime.now());
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> getSessions(String username) {
        return sessionRepository.getSessions(username);
    }

    @Override
    public Session getSession(Long id, String username) {
        Session session = sessionRepository.getSession(id, username);
        if (session == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session id " + id + " not found");
        }
        return session;
    }
}
