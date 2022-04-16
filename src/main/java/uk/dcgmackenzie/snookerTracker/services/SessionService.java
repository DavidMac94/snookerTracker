package uk.dcgmackenzie.snookerTracker.services;

import uk.dcgmackenzie.snookerTracker.DTO.SessionDTO;
import uk.dcgmackenzie.snookerTracker.entities.Session;

import java.util.List;

public interface SessionService {
    Session saveSession(Long drillId, String username);
    List<Session> getSessions(String username);
    Session getSession(Long id, String username);
}
