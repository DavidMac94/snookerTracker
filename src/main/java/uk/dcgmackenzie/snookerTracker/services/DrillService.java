package uk.dcgmackenzie.snookerTracker.services;

import uk.dcgmackenzie.snookerTracker.DTO.DrillDTO;
import uk.dcgmackenzie.snookerTracker.entities.Drill;

public interface DrillService {
    Drill saveDrill(DrillDTO drillDTO, String username);
    Drill getDrill(Long id, String username);
    void deleteDrill(Long id, String username);
}
