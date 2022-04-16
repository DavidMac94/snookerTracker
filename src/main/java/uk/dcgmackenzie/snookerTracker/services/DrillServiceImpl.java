package uk.dcgmackenzie.snookerTracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.dcgmackenzie.snookerTracker.DTO.DrillDTO;
import uk.dcgmackenzie.snookerTracker.entities.Drill;
import uk.dcgmackenzie.snookerTracker.repositories.DrillRepository;

@Service @RequiredArgsConstructor
public class DrillServiceImpl implements DrillService{
    private final UserService userService;
    private final DrillRepository drillRepository;

    @Override
    public Drill saveDrill(DrillDTO drillDTO, String username) {
        Drill drill = new Drill();
        drill.setName(drillDTO.getName());
        drill.setDescription(drillDTO.getDescription());
        drill.setMaxScore(drillDTO.getMaxScore());
        drill.setUser(userService.getUser(username));
        return drillRepository.save(drill);
    }

    @Override
    public Drill getDrill(Long id, String username) {
        Drill drill = drillRepository.findById(id, username);
        if (drill == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drill id " + id + " not found");
        }
        return drill;
    }

    @Override
    public void deleteDrill(Long id, String username) {
        Drill drill = drillRepository.findById(id, username);
        if (drill == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drill id " + id + " not found");
        }
        drillRepository.deleteById(id);
    }
}
