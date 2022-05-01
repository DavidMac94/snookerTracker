package uk.dcgmackenzie.snookerTracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uk.dcgmackenzie.snookerTracker.DTO.DrillDTO;
import uk.dcgmackenzie.snookerTracker.DTO.SessionDTO;
import uk.dcgmackenzie.snookerTracker.entities.*;
import uk.dcgmackenzie.snookerTracker.response.SessionResponse;
import uk.dcgmackenzie.snookerTracker.services.DrillService;
import uk.dcgmackenzie.snookerTracker.services.SessionService;
import uk.dcgmackenzie.snookerTracker.services.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/snooker")
public class MainController {
    private final UserService userService;
    private final DrillService drillService;
    private final SessionService sessionService;

//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getUsers() {
//        return ResponseEntity.ok().body(userService.getUsers());
//    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@Validated @RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/drills")
    public ResponseEntity<Drill> addDrill(Authentication authentication, @Valid @RequestBody DrillDTO drillDTO) {
        return new ResponseEntity<>(drillService.saveDrill(drillDTO, authentication.getName()),HttpStatus.CREATED);
    }

    @GetMapping("/drills")
    public ResponseEntity<Collection<Drill>> getDrills(Authentication authentication) {
        return new ResponseEntity<>(userService.getDrills(authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("/drills/{drillId}/sessions")
    public ResponseEntity<Collection<Session>> getSessionsByDrill(Authentication authentication, @PathVariable Long drillId) {
        return new ResponseEntity<>(sessionService.getSessionsByDrill(drillId, authentication.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/drills/{id}")
    public ResponseEntity<String> deleteDrill(Authentication authentication, @PathVariable Long id) {
        drillService.deleteDrill(id, authentication.getName());
        return new ResponseEntity<>("Drill " + id + " deleted", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/sessions")
    public ResponseEntity<SessionResponse> addSession(Authentication authentication, @Valid @RequestBody SessionDTO sessionDTO) {
        return new ResponseEntity<>(sessionService.saveSession(sessionDTO, authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Application healthy", HttpStatus.OK);
    }
}