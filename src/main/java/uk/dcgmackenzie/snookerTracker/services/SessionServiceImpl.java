package uk.dcgmackenzie.snookerTracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.dcgmackenzie.snookerTracker.DTO.SessionDTO;
import uk.dcgmackenzie.snookerTracker.entities.Drill;
import uk.dcgmackenzie.snookerTracker.entities.Session;
import uk.dcgmackenzie.snookerTracker.repositories.SessionRepository;
import uk.dcgmackenzie.snookerTracker.response.SessionResponse;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service @RequiredArgsConstructor
public class SessionServiceImpl implements SessionService{
    private final SessionRepository sessionRepository;
    private final DrillService drillService;

    @Override
    public SessionResponse saveSession(SessionDTO sessionDTO, String username) {
        Session session = new Session();
        Drill drill = drillService.getDrill(sessionDTO.getDrillId(), username);
        if (drill == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drill id " + sessionDTO.getDrillId() + " does not exist");
        }
        session.setDrill(drill);
        session.setResults(sessionDTO.getResults());
        session.setTimestamp(LocalDateTime.now());
        boolean isHighScore = true;
        Integer highestScoreInSession = Collections.max(sessionDTO.getResults());
        for (Session session1: sessionRepository.getSessionsByDrill(sessionDTO.getDrillId(), username)) {
            for (Integer result: session1.getResults()) {
                if (result >= highestScoreInSession) {
                    isHighScore = false;
                    break;
                }
            }
        }
        sessionRepository.save(session);
        return new SessionResponse(sessionDTO.getResults(), isHighScore);
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

    @Override
    public Collection<Session> getSessionsByDrill(Long drillId, String username) {
        Drill drill = drillService.getDrill(drillId, username);
        if (drill == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drill id " + drillId + " does not exist");
        }
        return sessionRepository.getSessionsByDrill(drillId, username);
    }
}
