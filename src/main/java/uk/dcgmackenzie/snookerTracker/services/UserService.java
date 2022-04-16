package uk.dcgmackenzie.snookerTracker.services;

import uk.dcgmackenzie.snookerTracker.entities.Drill;
import uk.dcgmackenzie.snookerTracker.entities.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    Collection<Drill> getDrills(String username);
    List<User> getUsers();
}
