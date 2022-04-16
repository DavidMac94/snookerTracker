package uk.dcgmackenzie.snookerTracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
@AllArgsConstructor
@Getter
public class DrillDTO {
    @NotNull
    private final String name;
    @NotNull
    private final String description;
    private final Integer maxScore;
}
