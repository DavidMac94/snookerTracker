package uk.dcgmackenzie.snookerTracker.services;

import uk.dcgmackenzie.snookerTracker.DTO.UserConfirmDTO;
import uk.dcgmackenzie.snookerTracker.DTO.UserRegisterDTO;
import uk.dcgmackenzie.snookerTracker.entities.Drill;
import uk.dcgmackenzie.snookerTracker.entities.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    String registerUser(UserRegisterDTO userRegisterDTO);
    String confirmUser(UserConfirmDTO userConfirmDTO);
    User getUser(String username);
    Collection<Drill> getDrills(String username);
    List<User> getUsers();
}
